package com.fih.ishareing.controller.v1.user;

import com.fih.ishareing.controller.base.BaseController;
import com.fih.ishareing.errorHandling.exceptions.BadEmailException;
import com.fih.ishareing.service.validation.ResetPasswordValidateService;
import com.fih.ishareing.service.validation.model.EmailValidateRequestVO;
import com.fih.ishareing.service.validation.model.ResetPasswordVO;
import com.fih.ishareing.validators.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reset")
public class ResetPasswordController extends BaseController {

    @Autowired
    private ResetPasswordValidateService resetPasswordService;

    @Autowired
    private EmailValidator emailValidator;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "/password")
    public void requestResetPassword(@RequestBody EmailValidateRequestVO request) {
        boolean validateMail = emailValidator.validate(request.getEmail());
        if (!validateMail) {
            throw new BadEmailException();
        }

        resetPasswordService.request(request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/password")
    public void resetPassword(@RequestBody ResetPasswordVO resetPassword) {
        resetPasswordService.resetPassword(resetPassword);
    }
}
