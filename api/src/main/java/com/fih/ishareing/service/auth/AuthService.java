package com.fih.ishareing.service.auth;

import com.fih.ishareing.errorHandling.exceptions.InValidRefreshTokenException;
import com.fih.ishareing.errorHandling.exceptions.InvalidUserPasswordException;
import com.fih.ishareing.errorHandling.exceptions.TokenInValidException;
import com.fih.ishareing.service.token.TokenService;
import com.fih.ishareing.service.user.UserService;
import com.fih.ishareing.service.auth.model.AuthTokenReqVO;
import com.fih.ishareing.service.auth.model.AuthTokenRespVO;
import com.fih.ishareing.service.auth.model.IeqAccessTokenVO;
import com.fih.ishareing.service.auth.model.LoginVO;
import com.fih.ishareing.service.auth.model.RefreshTokenReqVO;
import com.fih.ishareing.service.auth.model.RefreshTokenRespVO;
import com.fih.ishareing.service.auth.model.RefreshTokenVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    public AuthTokenRespVO login(final AuthTokenReqVO login) {
    	
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(login.getUsername() ,login.getPassword()));
            return tokenService.generateToken(authentication);
        } catch (BadCredentialsException ex) {
            throw new InvalidUserPasswordException("Account not found or password is invaild");
        }
    }

//    public IeqAccessTokenVO login(final LoginVO login) {
//
//        try {
//            Authentication authentication = authenticationManager
//                    .authenticate(new UsernamePasswordAuthenticationToken(login.getAccount(), login.getPassword()));
//            return tokenService.generateToken(authentication);
//        } catch (BadCredentialsException ex) {
//            throw new InvalidUserPasswordException("Account not found or password is invaild");
//        }
//    }

    public RefreshTokenRespVO refreshToken(final RefreshTokenReqVO refreshToken) {
        Authentication authentication = tokenService.readAuthenticationForRefreshToken(refreshToken.getRefreshToken());
        if (authentication == null)
            throw new InValidRefreshTokenException("Token is out-of-date or invalid");

        boolean exist = userService.exist(AuthenticationUtils.getUserAccount(authentication));
        if (!exist) {
            throw new InValidRefreshTokenException("Token is out-of-date or invalid");
        }

        try {
            return tokenService.refreshToken(authentication);
        } catch (BadCredentialsException ex) {
            throw new InValidRefreshTokenException("Token is out-of-date or invalid");
        }
    }

//    public IeqAccessTokenVO refreshToken(final RefreshTokenVO refreshToken) {
//        Authentication authentication = tokenService.readAuthenticationForRefreshToken(refreshToken.getRefreshToken());
//        if (authentication == null)
//            throw new InValidRefreshTokenException("Token is out-of-date or invalid");
//
//        boolean exist = userService.exist(AuthenticationUtils.getUserAccount(authentication));
//        if (!exist) {
//            throw new InValidRefreshTokenException("Token is out-of-date or invalid");
//        }
//
//        try {
//            return tokenService.refreshToken(authentication);
//        } catch (BadCredentialsException ex) {
//            throw new InValidRefreshTokenException("Token is out-of-date or invalid");
//        }
//    }

    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String account = AuthenticationUtils.getUserAccount();
            tokenService.removeToken(account, authentication.getCredentials().toString());
        } else {
            throw new TokenInValidException();
        }
    }

}