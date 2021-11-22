package com.fih.ishareing.service.validation;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.fih.ishareing.repository.UserRepository;
import com.fih.ishareing.service.email.EmailService;
import com.fih.ishareing.service.email.model.MailRequestVO;
import com.fih.ishareing.errorHandling.ErrorHandlingConstants.ERROR_BUNDLE;
import com.fih.ishareing.errorHandling.base.ResourceNotFoundException;
import com.fih.ishareing.errorHandling.exceptions.AccessTaskInValidException;
import com.fih.ishareing.errorHandling.exceptions.QuotaLimitationException;
import com.fih.ishareing.repository.pocservice.entity.User;
import com.fih.ishareing.service.validation.model.EmailValidateRequestVO;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;

import org.apache.commons.codec.Charsets;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class AbstraceEmailValidateService {
    private static Logger logger = LoggerFactory.getLogger(AbstraceEmailValidateService.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    protected abstract String getTaskKey(String code);

    protected abstract String getEmailQuotaKey(String email);

    protected abstract MailRequestVO createMailRequest(String to, String link);

    protected abstract String createValidateLink(String accessPage, String accessCode);

    protected void sendValidationEmail(User user, EmailValidateRequestVO request) {

        Preconditions.checkArgument(StringUtils.isNotBlank(request.getEmail()));

        boolean flag = redisTemplate.opsForValue().setIfAbsent(getEmailQuotaKey(request.getEmail()), "1", 1,
                TimeUnit.MINUTES);
        if (!flag) {
            throw new QuotaLimitationException("Email already sent, please try again in one minute");
        }

        String taskId = UUID.randomUUID().toString().replace("-", "");
        String key = getTaskKey(taskId);

        String hashEmail = Hashing.sha256().hashString(request.getEmail(), StandardCharsets.UTF_8).toString();
        Map<String, Object> values = ImmutableMap.of("userId", user.getId(), "email", hashEmail, "code",
                passwordEncoder.encode(taskId));

        // send mail
        String accessCode = BaseEncoding.base64Url().encode(taskId.getBytes(Charsets.US_ASCII));
        String link = createValidateLink(request.getAccessPage(), accessCode);
        emailService.sendEmail(createMailRequest(request.getEmail(), link));

        redisTemplate.opsForHash().putAll(key, values);
        redisTemplate.expire(key, 15, TimeUnit.MINUTES);
    }

    protected Map<Object, Object> validateTask(String taskId) {
        String key = getTaskKey(taskId);

        Map<Object, Object> values = getRedisTemplate().opsForHash().entries(key);
        if (values.isEmpty()) {
            logger.error("Access task is invalid, taskId:{}", taskId);
            throw new AccessTaskInValidException("Access task is invalid");
        }

        String code = MapUtils.getString(values, "code");
        if (StringUtils.isNotBlank(code)) {
            if (getPasswordEncoder().matches(taskId, code)) {
                return values;
            } else {
                logger.error("Access code is invalid");
                throw new AccessTaskInValidException("Access code is invalid");
            }
        } else {
            logger.error("Access code not found");
            throw new AccessTaskInValidException();
        }
    }

    protected User getTaskUser(final Map<Object, Object> values) {
        Integer userId = MapUtils.getInteger(values, "userId");
        if (userId == null) {
            logger.error("User not found");
            throw new AccessTaskInValidException();
        } else {
            Optional<User> userValue = getUserRepository().findById(userId);
            if (userValue.isPresent() && validateUser(userValue.get())) {
                User user = userValue.get();
                String email = MapUtils.getString(values, "email");
                if (validateEmail(user.getEmail(), email)) {
                    return user;
                } else {
                    throw new AccessTaskInValidException("Email not found or invalid");
                }
            } else {
                throw new ResourceNotFoundException(ERROR_BUNDLE.userNotFound);
            }
        }
    }

    protected void deleteTask(String key) {
        boolean deleted = getRedisTemplate().delete(key);
        if (!deleted) {
            logger.warn("Delete redis key return false, key:{}", key);
        }
    }

    private boolean validateUser(User user) {

        if (user.getActive() && user.getEnable())
            return true;

        return false;
    }

    private boolean validateEmail(String userEmail, String email) {

        if (StringUtils.isBlank(userEmail))
            return false;

        if (StringUtils.isBlank(email))
            return false;

        String hashEmail = Hashing.sha256().hashString(userEmail, StandardCharsets.UTF_8).toString();
        return hashEmail.equalsIgnoreCase(email);
    }

    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

}
