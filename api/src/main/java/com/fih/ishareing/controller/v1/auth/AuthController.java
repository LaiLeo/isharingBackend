package com.fih.ishareing.controller.v1.auth;

import com.fih.ishareing.controller.base.BaseController;
import com.fih.ishareing.service.auth.AuthService;
import com.fih.ishareing.service.auth.model.AuthTokenReqVO;
import com.fih.ishareing.service.auth.model.AuthTokenRespVO;
import com.fih.ishareing.service.auth.model.IeqAccessTokenVO;
import com.fih.ishareing.service.auth.model.LoginVO;
import com.fih.ishareing.service.auth.model.RefreshTokenReqVO;
import com.fih.ishareing.service.auth.model.RefreshTokenRespVO;
import com.fih.ishareing.service.auth.model.RefreshTokenVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController extends BaseController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/token")
    public AuthTokenRespVO login(@RequestBody @Valid AuthTokenReqVO login, BindingResult bindingResult) {
        return authService.login(login);
    }

    @PutMapping(value = "/token")
    public RefreshTokenRespVO refreshToken(@RequestBody @Valid RefreshTokenReqVO refreshToken, BindingResult bindingResult) {
        return authService.refreshToken(refreshToken);
    }
}
