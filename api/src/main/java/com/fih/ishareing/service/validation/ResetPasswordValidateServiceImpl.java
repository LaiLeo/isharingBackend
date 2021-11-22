package com.fih.ishareing.service.validation;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

import com.fih.ishareing.service.email.model.MailRequestVO;
import com.fih.ishareing.service.validation.model.ResetPasswordVO;
import com.fih.ishareing.errorHandling.ErrorHandlingConstants.ERROR_BUNDLE;
import com.fih.ishareing.errorHandling.base.ResourceNotFoundException;
import com.fih.ishareing.repository.pocservice.entity.User;
import com.fih.ishareing.service.validation.model.EmailValidateRequestVO;
import com.google.common.base.Preconditions;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordValidateServiceImpl extends AbstraceEmailValidateService
        implements ResetPasswordValidateService {

    private final static String REDIS_KEY_RESET_PASSWORD = "auth:reset.password:";
    private final static String REDIS_KEY_RESET_PASSWORD_MAIL_QUOTA = "quota:email:reset.password:";

    @Override
    public void request(EmailValidateRequestVO request) {
        Optional<User> userValue = null; //getUserRepository().findByEmailAndActiveTrueAndEnableTrue(request.getEmail());
        if (userValue.isPresent()) {
            sendValidationEmail(userValue.get(), request);
        } else {
            throw new ResourceNotFoundException(ERROR_BUNDLE.userNotFound);
        }
    }

    @Override
    public void resetPassword(ResetPasswordVO resetPassword) {
        Preconditions.checkArgument(StringUtils.isNotBlank(resetPassword.getAccessCode()),
                "Access code is null or empty");

        String taskId = new String(BaseEncoding.base64Url().decode(resetPassword.getAccessCode().trim()));
        Map<Object, Object> values = validateTask(taskId);
        User user = getTaskUser(values);

        getUserRepository().modifyPassword(user.getId(), getPasswordEncoder().encode(resetPassword.getPassword()));

        deleteTask(getTaskKey(taskId));
    }

    protected String getTaskKey(String taskId) {
        String hashTaskId = Hashing.sha256().hashString(taskId, StandardCharsets.UTF_8).toString();
        return String.format("%s%s", REDIS_KEY_RESET_PASSWORD, hashTaskId);
    }

    @Override
    protected String getEmailQuotaKey(String email) {
        String hash = Hashing.sha256().hashString(email, StandardCharsets.UTF_8).toString();
        return String.format("%s%s", REDIS_KEY_RESET_PASSWORD_MAIL_QUOTA, hash);
    }

    @Override
    protected MailRequestVO createMailRequest(String to, String link) {
        MailRequestVO request = new MailRequestVO();
        request.setFrom("fusionnet@fih-foxconn.com");
        request.setTo(to);
        request.setSubject("IEQ帳號重設密碼驗證信");
        request.setContent(createMailContent(link));
        return request;
    }

    private String createMailContent(String link) {
        StringBuilder sb = new StringBuilder();
        sb.append("您好, <p>");
        sb.append("請輸入點選下方連結即可重設您的IEQ帳號密碼，有效期限<b>15</b>分鐘。<p>");
        sb.append(String.format("連結 : <b>%s</b>", link));
        return sb.toString();
    }

    @Override
    protected String createValidateLink(String accessPage, String accessCode) {
        return String.format("%s?accessCode=%s", accessPage, accessCode);
    }

}
