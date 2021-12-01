package com.fih.ishareing.controller.v1.reset;

import com.fih.ishareing.controller.base.BaseController;
import com.fih.ishareing.service.auth.AuthService;
import com.fih.ishareing.service.reset.ResetService;
import com.fih.ishareing.service.reset.model.ResetPasswordReqVO;
import com.fih.ishareing.service.reset.model.ResetPasswordRespVO;
import com.fih.ishareing.service.reset.model.ResetPermissionReqVO;
import com.fih.ishareing.service.reset.model.ResetPermissionRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/reset")
public class ResetController extends BaseController {

    @Autowired
    private ResetService resetService;

    @PostMapping(value = "/password")
    public ResetPermissionRespVO getResetPermission(@RequestBody @Valid ResetPermissionReqVO model, BindingResult bindingResult) {
        return resetService.getResetCode(model);
    }

    @PutMapping(value = "/password")
    public ResponseEntity<?> updateResetPassword(@RequestBody @Valid ResetPasswordReqVO model, BindingResult bindingResult) {
        ResetPasswordRespVO result = resetService.updatePassword(model);

        return new ResponseEntity<ResetPasswordRespVO>(result, HttpStatus.OK);
    }
}
