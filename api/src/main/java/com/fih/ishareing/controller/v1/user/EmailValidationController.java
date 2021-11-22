package com.fih.ishareing.controller.v1.user;

import com.fih.ishareing.controller.base.BaseController;
import com.fih.ishareing.errorHandling.exceptions.BadEmailException;
import com.fih.ishareing.service.validation.UserEmailValidateService;
import com.fih.ishareing.service.validation.model.EmailValidateRequestVO;
import com.fih.ishareing.validators.EmailValidator;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/validation")
public class EmailValidationController extends BaseController {

    @Autowired
    private UserEmailValidateService userEmailValidateService;

    @Autowired
    private EmailValidator emailValidator;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "/email")
    public void requestValidateEmail(@RequestBody EmailValidateRequestVO request) {
        boolean validateMail = emailValidator.validate(request.getEmail());
        if (!validateMail) {
            throw new BadEmailException();
        }

        userEmailValidateService.request(request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/email")
    public void validateEmail(@RequestBody Map<String, Object> request) {
        String accessCode = MapUtils.getString(request, "accessCode");
        userEmailValidateService.validateEmail(accessCode);
    }

}
