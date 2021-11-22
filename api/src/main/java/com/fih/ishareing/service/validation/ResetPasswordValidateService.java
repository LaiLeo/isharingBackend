package com.fih.ishareing.service.validation;

import com.fih.ishareing.service.validation.model.ResetPasswordVO;
import com.fih.ishareing.service.validation.model.EmailValidateRequestVO;

public interface ResetPasswordValidateService {
    void request(EmailValidateRequestVO request);

    void resetPassword(ResetPasswordVO resetPassword);
}
