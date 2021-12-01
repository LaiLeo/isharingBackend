package com.fih.ishareing.service.thirdLogin;

import com.alibaba.fastjson.JSONObject;
import com.fih.ishareing.constant.ApiErrorConstant;
import com.fih.ishareing.controller.base.error.ApiErrorResponse;
import com.fih.ishareing.errorHandling.ErrorHandlingConstants;
import com.fih.ishareing.errorHandling.exceptions.InvalidUserPasswordException;
import com.fih.ishareing.repository.CoreUserThirdRepository;
import com.fih.ishareing.repository.entity.tbCoreUserThird;
import com.fih.ishareing.repository.entity.vwUsers;
import com.fih.ishareing.repository.vwUserRepository;
import com.fih.ishareing.service.auth.model.AuthTokenRespVO;
import com.fih.ishareing.service.thirdLogin.model.FubonUserValidVO;
import com.fih.ishareing.service.thirdLogin.model.TwmUserValidVO;
import com.fih.ishareing.service.token.TokenService;
import com.fih.ishareing.utils.MD5Utils;
import com.fih.ishareing.utils.ThirdPartyApiUtils;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class ThirdLoginServiceImpl implements ThirdLoginService {
    private static Logger logger = LoggerFactory.getLogger(ThirdLoginServiceImpl.class);
    private final long ACCESS_EXPIRES_MIN = 5;
    private final long ACCESS_TOKEN_EXPIRES = TimeUnit.MINUTES.toMillis(ACCESS_EXPIRES_MIN); // 5 mins

    @Autowired
    private ThirdPartyApiUtils thirdPartyApiUtils;

    @Autowired
    private MD5Utils md5Utils;

    @Autowired
    private vwUserRepository vwUserRep;

    @Autowired
    private CoreUserThirdRepository userThirdRep;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Transactional
    @Override
    public JSONObject validFubonUsers(FubonUserValidVO user) {
        JSONObject result = new JSONObject();
        ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
        List<Object> errors = new ArrayList<Object>();
        vwUsers vwUsers = null;
        result.put("username", user.getUsername());

        Boolean isMember = thirdPartyApiUtils.validFubonUser(user.getUsername(), md5Utils.getMd5(user.getPassword()));
        if (!isMember) {
            errors.add(new ApiErrorResponse(ErrorHandlingConstants.ERROR_BUNDLE.userNotFound));
            builder.put(ApiErrorConstant.ERRORS, errors);
        } else {
            tbCoreUserThird tbCoreUserThird = userThirdRep.findByEnterpriseSerialCodeAndEnterpriseSerialEmail("fubon", user.getUsername());
            if (tbCoreUserThird != null) {
                vwUsers = vwUserRep.findByIdAndIsActive(tbCoreUserThird.getUserId(), 1);
            } else {
                vwUsers = vwUserRep.findByUsernameAndIsActive(user.getUsername(), 1);
                if (vwUsers == null) {
                    errors.add(new ApiErrorResponse(ErrorHandlingConstants.ERROR_BUNDLE.userNotRegister));
                    builder.put(ApiErrorConstant.ERRORS, errors);
                    //redis存fubon資訊並返回
                    String thirdPartyInfo = setThirdPartyInfo("fubon", "", user.getUsername());
                    String accessCode = storeAccessCode(thirdPartyInfo);
                    result.put("accessCode", accessCode);
                }
            }
        }

        if (errors.size() == 0) {
            // find account and grant token
            JSONObject tokenObject = new JSONObject();
            if (userThirdRep.countByUserIdAndEnterpriseSerialCodeAndEnterpriseSerialEmail(vwUsers.getId(), "fubon", user.getUsername()) == 0) {
                userThirdRep.save(new tbCoreUserThird(vwUsers.getId(), "fubon", "", user.getUsername()));
                tokenObject.put("bind", true);
            }

            try {
                Authentication authentication = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername() ,md5Utils.getMd5("FihIsharing")));
                AuthTokenRespVO authTokenRespVO =  tokenService.generateToken(authentication);
                tokenObject.put("accessToken", authTokenRespVO.getAccessToken());
                tokenObject.put("expiresIn", authTokenRespVO.getExpiresIn());
                tokenObject.put("refreshToken", authTokenRespVO.getRefreshToken());
                tokenObject.put("resetPassword", authTokenRespVO.getResetPassword());
            } catch (BadCredentialsException ex) {
                throw new InvalidUserPasswordException("Account not found or password is invaild");
            }

            result.put("token", tokenObject);

        } else {
            result.put("errors", errors);
        }

        return result;
    }

    @Override
    public JSONObject validTwmUsers(TwmUserValidVO user) {
        JSONObject result = new JSONObject();
        ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
        List<Object> errors = new ArrayList<Object>();
        vwUsers vwUsers = null;
        result.put("code", user.getCode());

        JSONObject userProfile = thirdPartyApiUtils.getTwmUserProfile(user.getCode());
        String userEmail = null;
        String userId = null;
//         {"uid":"653895","linkopenid":[],"signup_date":"20200616","login_type":"MC","loginuid":"653895","need_link":"D","caller_id":"fihcloud","name":"用戶177","mobilenbr":"0988535122","email":"vddianama@twm.com"}

        if (userProfile.size() == 0) {
            errors.add(new ApiErrorResponse(ErrorHandlingConstants.ERROR_BUNDLE.userNotFound));
            builder.put(ApiErrorConstant.ERRORS, errors);
        } else {
            userEmail = userProfile.getString("email");
            userId = userProfile.getString("uid");
            tbCoreUserThird tbCoreUserThird = userThirdRep.findByEnterpriseSerialCodeAndEnterpriseSerialNumber("twmMember", userId);
            if (tbCoreUserThird != null) {
                vwUsers = vwUserRep.findByIdAndIsActive(tbCoreUserThird.getUserId(), 1);
            } else {
                if (userEmail != null) {
                    vwUsers = vwUserRep.findByUsernameAndIsActive(userEmail, 1);
                    result.put("username", userEmail);
                }
                if (vwUsers == null) {
                    errors.add(new ApiErrorResponse(ErrorHandlingConstants.ERROR_BUNDLE.userNotRegister));
                    builder.put(ApiErrorConstant.ERRORS, errors);
                    //redis存twmEnterprise資訊並返回
                    String thirdPartyInfo = setThirdPartyInfo("twmMember", userProfile.getString("uid") , userEmail);
                    String accessCode = storeAccessCode(thirdPartyInfo);
                    result.put("accessCode", accessCode);
                }
            }
        }

        if (errors.size() == 0) {
            // find account and grant token
            JSONObject tokenObject = new JSONObject();
            userEmail = vwUsers.getUsername();

            try {
                Authentication authentication = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(userEmail ,md5Utils.getMd5("FihIsharing")));
                AuthTokenRespVO authTokenRespVO =  tokenService.generateToken(authentication);
                tokenObject.put("accessToken", authTokenRespVO.getAccessToken());
                tokenObject.put("expiresIn", authTokenRespVO.getExpiresIn());
                tokenObject.put("refreshToken", authTokenRespVO.getRefreshToken());
                tokenObject.put("resetPassword", authTokenRespVO.getResetPassword());
            } catch (BadCredentialsException ex) {
                throw new InvalidUserPasswordException("Account not found or password is invaild");
            }

            result.put("token", tokenObject);

        } else {
            result.put("errors", errors);
        }

        return result;
    }

    private String setThirdPartyInfo(String enterpriseSerialCode, String enterpriseSerialNumber, String enterpriseSerialEmail) {
        JSONObject thirdPartyInfo = new JSONObject();
        thirdPartyInfo.put("enterpriseSerialCode", enterpriseSerialCode);
        thirdPartyInfo.put("enterpriseSerialNumber", enterpriseSerialNumber);
        thirdPartyInfo.put("enterpriseSerialEmail", enterpriseSerialEmail);
        return thirdPartyInfo.toJSONString();
    }
    private String storeAccessCode(String thirdPartyInfo) {
        String accessCode = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(accessCode, thirdPartyInfo, ACCESS_EXPIRES_MIN, TimeUnit.MINUTES);

        return accessCode;
    }

    private String loadAccessCodeInfo(String accesscode) {
        return redisTemplate.opsForValue().get(accesscode);
    }
}