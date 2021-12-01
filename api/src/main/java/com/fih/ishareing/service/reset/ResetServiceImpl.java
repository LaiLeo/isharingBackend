package com.fih.ishareing.service.reset;

import com.fih.ishareing.configurations.security.Pbkdf2Sha256PasswordEncoder;
import com.fih.ishareing.controller.base.error.ApiErrorResponse;
import com.fih.ishareing.errorHandling.exceptions.BadPasswordException;
import com.fih.ishareing.errorHandling.exceptions.InternalErrorException;
import com.fih.ishareing.errorHandling.exceptions.ParameterException;
import com.fih.ishareing.errorHandling.exceptions.UserNotFoundException;
import com.fih.ishareing.repository.AuthUserRepository;
import com.fih.ishareing.repository.CoreUserAccountRepository;
import com.fih.ishareing.repository.UserRepository;
import com.fih.ishareing.repository.vwUserRepository;
import com.fih.ishareing.repository.entity.tbAuthUser;
import com.fih.ishareing.repository.entity.vwUsers;
import com.fih.ishareing.service.AbstractAuthenticationService;
import com.fih.ishareing.service.AbstractService;
import com.fih.ishareing.service.reset.model.ResetPasswordReqVO;
import com.fih.ishareing.service.reset.model.ResetPasswordRespVO;
import com.fih.ishareing.service.reset.model.ResetPermissionReqVO;
import com.fih.ishareing.service.reset.model.ResetPermissionRespVO;
import com.fih.ishareing.service.token.TokenService;
import com.fih.ishareing.service.user.UserService;
import com.fih.ishareing.utils.MailUtils;
import com.fih.ishareing.validators.EmailValidator;
import com.fih.ishareing.validators.PasswordValidator;
import com.fih.ishareing.validators.ValidatorUtils;
import com.google.common.cache.LoadingCache;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import com.fih.ishareing.utils.RandomCharUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class ResetServiceImpl extends AbstractService implements ResetService {
    private static Logger logger = LoggerFactory.getLogger(ResetServiceImpl.class);

    public final static String REDIS_KEY_ACCESSCODE_TO_USER = "reset:accesscode_to_user";

    private final long ACCESS_EXPIRES_MIN = 10;
    private final long ACCESS_TOKEN_EXPIRES = TimeUnit.MINUTES.toMillis(ACCESS_EXPIRES_MIN); // 10 min

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private AuthUserRepository AuthUserRepo;

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private RandomCharUtils RandomCharUtils;

	@Autowired
	private MailUtils mailUtils;

	@Autowired
	private PasswordValidator passwordValidator;

	@Override
	public ResetPermissionRespVO getResetCode(ResetPermissionReqVO model) {
		// TODO Auto-generated method stub
		// Validate Input Parameters
		ResetPermissionReqVO newVO = modelMapper.map(model, ResetPermissionReqVO.class);
		Errors errors = ValidatorUtils.validate(newVO);
		List<FieldError> listFieldError = (errors != null && errors.getFieldErrorCount() > 0)?errors.getFieldErrors():null;
		
		if(listFieldError != null && listFieldError.size() > 0)
			throw new ParameterException(String.format("Parameter: %s.", listFieldError.get(0).getField()));
		
		// Find user info
		vwUsers vwUsersObj = userService.findUserByUserName(model.getEmail());
		if(vwUsersObj == null)
			throw new UserNotFoundException();
		
		// Send reset password mail
		String strAccessCode = RandomCharUtils.randomString(32);
		
		String content = model.getAccessPage() + "?accessCode=%s";
		content = String.format(content, strAccessCode);
		mailUtils.sendMail(model.getEmail(), "[微樂志工] 會員密碼重新設定", mailUtils.getResetEmailTemplate(content));

		// Store access code to redis
		JSONObject JOUserInfo = new JSONObject() {{
			put("id", vwUsersObj.getId());
			put("username", vwUsersObj.getUsername());
		}};
		storeAccessCodeToUser(strAccessCode, JOUserInfo.toJSONString());

		// Prepare return result
		ResetPermissionRespVO result = new ResetPermissionRespVO();
		result.setAccessCode(strAccessCode);
		
		return result;
	}

	@Override
	public ResetPasswordRespVO updatePassword(ResetPasswordReqVO model) {
		// TODO Auto-generated method stub
		Pbkdf2Sha256PasswordEncoder Pbkdf2Sha256PasswordEncoderObj = new Pbkdf2Sha256PasswordEncoder();

		// Validate Input Parameters
		ResetPasswordReqVO newVO = modelMapper.map(model, ResetPasswordReqVO.class);
		Errors errors = ValidatorUtils.validate(newVO);
		List<FieldError> listFieldError = (errors != null && errors.getFieldErrorCount() > 0)?errors.getFieldErrors():null;
		
		if(listFieldError != null && listFieldError.size() > 0) {
			throw new ParameterException(String.format("Parameter: %s.", listFieldError.get(0).getField()));
		}

		if (model.getPassword() != null && !passwordValidator.validate(model.getPassword())) {
			throw new BadPasswordException(String.format("Password: %s.", model.getPassword()));
		}
		
		//
		String strAccessCode = model.getAccessCode();
		Integer nUserID = 0;
		try {
			String strUserInfo = loadAccessCodeInfo(strAccessCode);
			JSONObject JOUserInfo = null;
			if(strUserInfo != null && !strUserInfo.isEmpty())
				JOUserInfo = (JSONObject) (new JSONParser()).parse(strUserInfo);
			nUserID = (JOUserInfo != null && JOUserInfo.containsKey("id"))?
					Integer.parseInt(JOUserInfo.get("id").toString()):0;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(nUserID == 0)
			throw new UserNotFoundException();
			
		// Update password
		String strNewPassword = model.getPassword();
		String strPassword = Pbkdf2Sha256PasswordEncoderObj.encode(strNewPassword);
		
		tbAuthUser tbAuthUserObj = AuthUserRepo.findByid(nUserID);
		if(tbAuthUserObj == null)
			throw new UserNotFoundException();
		
		tbAuthUserObj.setPassword(strPassword);
		
		try {
			AuthUserRepo.save(tbAuthUserObj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InternalErrorException(String.format("Update password failed. [Exception: %s]", e.getMessage()));
		}
		
		return null;
	}


    private void storeAccessCodeToUser(String accesscode, String userinfo) {
        String accessCodeToUser = String.format("%s:%s", REDIS_KEY_ACCESSCODE_TO_USER, accesscode);
        redisTemplate.opsForValue().set(accessCodeToUser, userinfo, ACCESS_EXPIRES_MIN, TimeUnit.MINUTES);
    }

    private String loadAccessCodeInfo(String accesscode) {
        String accessCodeToUser = String.format("%s:%s", REDIS_KEY_ACCESSCODE_TO_USER, accesscode);
        
        return redisTemplate.opsForValue().get(accessCodeToUser);
    }

}