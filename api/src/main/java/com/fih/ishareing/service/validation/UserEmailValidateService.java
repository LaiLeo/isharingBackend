package com.fih.ishareing.service.validation;

import com.fih.ishareing.service.validation.model.EmailValidateRequestVO;

public interface UserEmailValidateService {
    void request(EmailValidateRequestVO request);

    void validateEmail(String accessCode);
}
