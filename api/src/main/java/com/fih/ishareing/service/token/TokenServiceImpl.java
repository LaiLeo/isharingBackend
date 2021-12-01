package com.fih.ishareing.service.token;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import com.fih.ishareing.errorHandling.exceptions.InValidRefreshTokenException;
import com.fih.ishareing.errorHandling.exceptions.UserNotFoundException;
import com.fih.ishareing.repository.AuthRepository;
import com.fih.ishareing.repository.AuthUserRepository;
import com.fih.ishareing.repository.entity.AuthToken;
import com.fih.ishareing.repository.entity.tbAuthUser;
import com.fih.ishareing.service.AbstractAuthenticationService;
import com.fih.ishareing.service.AbstractService;
import com.fih.ishareing.service.auth.model.AuthTokenRespVO;
import com.fih.ishareing.service.auth.model.RefreshTokenRespVO;
import com.fih.ishareing.service.user.model.AuthUser;
import com.fih.ishareing.utils.Hasher;
import com.fih.ishareing.utils.IsharingApiUtils;
import com.fih.ishareing.utils.MD5Utils;
import com.fih.ishareing.utils.date.DateUtils;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.common.hash.Hashing;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class TokenServiceImpl extends AbstractService implements TokenService {
    private static Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    public final static String REDIS_KEY_ACCESS = "auth:access";
    public final static String REDIS_KEY_ACCESS_TO_REFRESH = "auth:access_to_refresh";
    public final static String REDIS_KEY_ACCESS_TO_METADATA = "auth:access_to_metadata";
    public final static String REDIS_KEY_REFRESH_TO_ACCESS = "auth:refresh_to_access";
    public final static String REDIS_KEY_REFRASH = "auth:refresh";
    public final static String REDIS_KEY_UNAME_TO_ACCESS = "auth:uanme_to_access";

    private final long ACCESS_EXPIRES_MIN = 60;
    private final long ACCESS_TOKEN_EXPIRES = TimeUnit.MINUTES.toMillis(ACCESS_EXPIRES_MIN); // 1 hour

    private final long REFRESH_EXPIRES_DAY = 1;
    private final long REFRESH_TOKEN_EXPIRES = TimeUnit.DAYS.toMillis(REFRESH_EXPIRES_DAY); // 30 days

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private AuthUserRepository AuthUserRepo;

    @Autowired
    private IsharingApiUtils isharingApiUtils;

    @Autowired
    private AuthRepository authRep;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MD5Utils md5Utils;
    
    private final Key key;

    public TokenServiceImpl() {
        this.key = Keys.hmacShaKeyFor("09434918-d617-45af-856d-3f5650b61e1c".getBytes());

    }

    @PostConstruct
    public void init() {
        // Set<String> keys = redisTemplate.keys("auth:*");
        // redisTemplate.delete(keys);
    }

    @Override
    public AuthTokenRespVO generateToken(Authentication authentication) {
        Preconditions.checkArgument(authentication != null);

        AuthUser user = (AuthUser) authentication.getPrincipal();
        String username = user.getUsername();
        
        // Query user record.
        tbAuthUser TBAuthUserObj = AuthUserRepo.findByUsername(username);
        if(TBAuthUserObj == null) {
            throw new UserNotFoundException(String.format("User not exists. [User Name: %s]", username));
        }
        
        TBAuthUserObj.setLastLogin(DateUtils.GetNowTimestamp());
        AuthUserRepo.save(TBAuthUserObj);
        
        user.setUserId(TBAuthUserObj.getId());
        user.setSu((TBAuthUserObj.getIsSu().intValue() == 1));

        Map<String, Object> claims = ClaimsUtils.toClaims(user);

        // store refresh token
        String refreshToken = genUniqueId();
        storeRefreshToken(refreshToken, username, claims);

        // store access token
//        String accessToken = genUniqueId();
        String accessToken = isharingApiUtils.getAccessToken(user.getUserId());
        if (accessToken == null) {
            throw new UserNotFoundException(String.format("User not exists. [User Name: %s]", username));
        }
	    storeAccessToken(accessToken, username, claims);
        
	    // store access to refresh
	    storeAccessToRefresh(accessToken, refreshToken);

	    // store refresh to access
	    storeRefreshToAccess(refreshToken, accessToken);

	    // store uname to access
	    storeUnameToAccess(username, accessToken);
        
        // prepare response content
        AuthTokenRespVO Resp = new AuthTokenRespVO();
        Resp.setAccessToken(accessToken);
        Resp.setExpiresIn(ACCESS_TOKEN_EXPIRES / 1000);
        Resp.setRefreshToken(refreshToken);
        Resp.setResetPassword(false);

        return Resp;
    }

    @Override
    public RefreshTokenRespVO refreshToken(Authentication authentication) {
        Preconditions.checkArgument(authentication != null);
        Preconditions.checkArgument(
                authentication.getPrincipal() != null && authentication.getPrincipal() instanceof AuthUser);
        Preconditions.checkArgument(authentication.getCredentials() != null
                && StringUtils.isNotBlank(authentication.getCredentials().toString()));

        AuthUser user = (AuthUser) authentication.getPrincipal();

        String refreshToken = authentication.getCredentials().toString();

        // remove access by refresh
        removeAccessTokenUseRefreshToken(refreshToken, user.getUsername());

        // store access token
//        String accessToken = genUniqueId();
        String accessToken = isharingApiUtils.getAccessToken(user.getUserId());
        if (accessToken == null) {
            throw new InValidRefreshTokenException("Token is out-of-date or invalid");
        }
        storeAccessToken(accessToken, user.getUsername(), ClaimsUtils.toClaims(user));

        // store access to refresh
        storeAccessToRefresh(accessToken, refreshToken);

        // store refresh to access
        storeRefreshToAccess(refreshToken, accessToken);

        // store uname to access
        storeUnameToAccess(user.getUsername(), accessToken);

        // prepare response content
        RefreshTokenRespVO Resp = new RefreshTokenRespVO();
        Resp.setAccessToken(accessToken);
        Resp.setExpiresIn(ACCESS_TOKEN_EXPIRES / 1000);
        Resp.setRefreshToken(refreshToken);

        return Resp;
    }


    @Override
    public Authentication readAuthentication(String accessToken) {
        String access = keyAccessToken(accessToken);
        String jwtValue = redisTemplate.opsForValue().get(access);
        if (StringUtils.isBlank(jwtValue)) {
            AuthToken authToken = authRep.findByKey(accessToken);
            if (authToken == null) {
                logger.error("Token not found, token:{}", accessToken);
                return null;
            } else {
                tbAuthUser authUser = AuthUserRepo.findByid(authToken.getUserId());
                Authentication authentication = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(authUser.getUsername() ,md5Utils.getMd5("FihIsharing")));
                AuthUser user = (AuthUser) authentication.getPrincipal();
                Map<String, Object> claims = ClaimsUtils.toClaims(user);
                jwtValue = storeAccessTokenReturnJwt(accessToken, authUser.getUsername(), claims);
            }
        }

        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(jwtValue).getBody();
            int uid = ClaimsUtils.getUID(claims);
            String app = ClaimsUtils.getApplicationCode(claims);
            boolean isSu = ClaimsUtils.isSu(claims);
            boolean isAdmin = ClaimsUtils.isAdmin(claims);
            UserDetails userDetails = new AuthUser(uid, app, isSu, isAdmin, false, claims.getSubject(), "",
                    ImmutableList.of());
            return new UsernamePasswordAuthenticationToken(userDetails, accessToken, userDetails.getAuthorities());
        } catch (ExpiredJwtException | SignatureException | IllegalArgumentException ex) {
            logger.error("Token expired, error:{}", ex.getMessage());
            return null;
        }
    }

    @Override
    public Authentication readAuthenticationForRefreshToken(String token) {
        Preconditions.checkArgument(StringUtils.isNotBlank(token));

        String refresh = keyRefreshToken(token);
        String jwtValue = redisTemplate.opsForValue().get(refresh);
        if (StringUtils.isBlank(jwtValue)) {
            logger.error("Refresh token not found, token:{}", token);
            return null;
        }

        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(jwtValue).getBody();
            int uid = ClaimsUtils.getUID(claims);
            String app = ClaimsUtils.getApplicationCode(claims);
            boolean isSu = ClaimsUtils.isSu(claims);
            boolean isAdmin = ClaimsUtils.isAdmin(claims);
            UserDetails userDetails = new AuthUser(uid, app, isSu, isAdmin, false, claims.getSubject(), "",
                    ImmutableList.of());
            return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
        } catch (ExpiredJwtException | SignatureException | IllegalArgumentException ex) {
            logger.error("Token expired, error:{}", ex.getMessage());
            return null;
        }
    }
    
    @Override
    public void removeToken(String account, String accessToken) {

        Builder<String> builder = new ImmutableSet.Builder<String>();

        String access = keyAccessToken(accessToken);
        builder.add(access);

        String accessToRefresh = String.format("%s:%s", REDIS_KEY_ACCESS_TO_REFRESH, accessToken);
        builder.add(accessToRefresh);

        String refreshToken = redisTemplate.opsForValue().get(accessToRefresh);
        if (StringUtils.isNotBlank(refreshToken)) {
            String refreshToAccess = String.format("%s:%s", REDIS_KEY_REFRESH_TO_ACCESS, refreshToken);
            builder.add(refreshToAccess);
            String refresh = keyRefreshToken(refreshToken);
            builder.add(refresh);
        }

        redisTemplate.delete(builder.build());

        String unameToAccess = keyUnameToAccess(account);
        redisTemplate.opsForSet().remove(unameToAccess, accessToken);
    }

    @Override
    public void removeTokenUseAccount(final String account) {
        Set<String> tokens = redisTemplate.opsForSet().members(keyUnameToAccess(account));
        tokens.forEach(f -> {
            removeToken(account, f);
        });
    }

    @Override
    public void removeAccessTokenUseRefreshToken(String refreshToken, String username) {

        Builder<String> builder = new ImmutableSet.Builder<String>();

        String refreshToAccess = String.format("%s:%s", REDIS_KEY_REFRESH_TO_ACCESS, refreshToken);
        builder.add(refreshToAccess);

        String accessToken = redisTemplate.opsForValue().get(refreshToAccess);
        if (StringUtils.isNotBlank(accessToken)) {
            String unameToAccess = keyUnameToAccess(username);
            redisTemplate.opsForSet().remove(unameToAccess, accessToken);

            builder.add(keyAccessToken(accessToken));

            String accessToRefresh = String.format("%s:%s", REDIS_KEY_ACCESS_TO_REFRESH, accessToken);
            builder.add(accessToRefresh);
        }

        redisTemplate.delete(builder.build());

    }

    private String genUniqueId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private void storeAccessToken(final String accessToken, final String username, final Map<String, Object> claims) {
        long exp = System.currentTimeMillis() + ACCESS_TOKEN_EXPIRES;
        String accessTokenValue = Jwts.builder().setClaims(claims).setId(accessToken).setSubject(username)
                .setIssuedAt(new Date()).setExpiration(new Date(exp)).signWith(this.key).compact();
        String accessKey = keyAccessToken(accessToken);
        redisTemplate.opsForValue().set(accessKey, accessTokenValue, ACCESS_EXPIRES_MIN, TimeUnit.MINUTES);
    }

    private void storeRefreshToken(final String refreshToken, final String username, final Map<String, Object> claims) {
        long exp = System.currentTimeMillis() + REFRESH_TOKEN_EXPIRES;
        String refreshTokenValue = Jwts.builder().setClaims(claims).setId(refreshToken).setSubject(username)
                .setIssuedAt(new Date()).setExpiration(new Date(exp)).signWith(this.key).compact();
        String refreshKey = keyRefreshToken(refreshToken);
        redisTemplate.opsForValue().set(refreshKey, refreshTokenValue, REFRESH_EXPIRES_DAY, TimeUnit.DAYS);
    }

    private void storeAccessToRefresh(String accessToken, String refreshToken) {
        String accessToRefresh = String.format("%s:%s", REDIS_KEY_ACCESS_TO_REFRESH, accessToken);
        redisTemplate.opsForValue().set(accessToRefresh, refreshToken, ACCESS_EXPIRES_MIN, TimeUnit.MINUTES);
    }

    private void storeAccessToMetaData(String accessToken, String metadata) {
        String accessToRefresh = String.format("%s:%s", REDIS_KEY_ACCESS_TO_METADATA, accessToken);
        redisTemplate.opsForValue().set(accessToRefresh, metadata, ACCESS_EXPIRES_MIN, TimeUnit.MINUTES);
    }

    private void storeRefreshToAccess(String refreshToken, String accessToken) {
        String refreshToAccess = String.format("%s:%s", REDIS_KEY_REFRESH_TO_ACCESS, refreshToken);
        redisTemplate.opsForValue().set(refreshToAccess, accessToken, ACCESS_EXPIRES_MIN, TimeUnit.MINUTES);
    }

    private void storeUnameToAccess(String username, String accessToken) {
        String unameToAccess = keyUnameToAccess(username);
        redisTemplate.opsForSet().add(unameToAccess, accessToken);
        redisTemplate.expire(unameToAccess, ACCESS_EXPIRES_MIN, TimeUnit.MINUTES);
    }

    private static String keyAccessToken(String token) {
        return String.format("%s:%s", REDIS_KEY_ACCESS, token);
    }

    private static String keyRefreshToken(String token) {
        return String.format("%s:%s", REDIS_KEY_REFRASH, token);
    }

    private static String keyUnameToAccess(String username) {
        String hashUname = Hashing.sha256().hashString(username, StandardCharsets.UTF_8).toString();
        return String.format("%s:%s", REDIS_KEY_UNAME_TO_ACCESS, hashUname);
    }

    private String storeAccessTokenReturnJwt(final String accessToken, final String username, final Map<String, Object> claims) {
        long exp = System.currentTimeMillis() + ACCESS_TOKEN_EXPIRES;
        String accessTokenValue = Jwts.builder().setClaims(claims).setId(accessToken).setSubject(username)
                .setIssuedAt(new Date()).setExpiration(new Date(exp)).signWith(this.key).compact();
        String accessKey = keyAccessToken(accessToken);
        redisTemplate.opsForValue().set(accessKey, accessTokenValue, ACCESS_EXPIRES_MIN, TimeUnit.MINUTES);

        return accessTokenValue;
    }
}