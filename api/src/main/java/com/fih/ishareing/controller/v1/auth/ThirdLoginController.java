package com.fih.ishareing.controller.v1.auth;

import com.alibaba.fastjson.JSONObject;
import com.fih.ishareing.controller.base.BaseController;
import com.fih.ishareing.service.thirdLogin.ThirdLoginService;
import com.fih.ishareing.service.thirdLogin.model.FubonUserValidVO;
import com.fih.ishareing.service.thirdLogin.model.TwmUserValidVO;
import com.fih.ishareing.utils.signature.ApiSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
public class ThirdLoginController extends BaseController {

    @Autowired
    private ThirdLoginService thirdLoginService;
    @Autowired
    private ApiSignature apiSignature;

    @PostMapping(value = "/fubon")
    public JSONObject validFubonUsers(@Valid @RequestBody FubonUserValidVO user) {
        return thirdLoginService.validFubonUsers(user);
    }

    @PostMapping(value = "/twm")
    public JSONObject validTwmUsers(@Valid @RequestBody TwmUserValidVO code) {
        return thirdLoginService.validTwmUsers(code);
    }

    @GetMapping(value = "/sign")
    public JSONObject genSign() {
        String SIGNATURE_VERSION = "v1";
        String SIGNATURE_ACCESS_KEY_ID = "testid";
        String SIGNATURE_TIMESTAMP = String.valueOf(System.currentTimeMillis());
        String SIGNATURE_METHOD = "HMAC-SHA1";
        String SIGNATURE_SIGNATURE_VERSION = "1.0";
        String SIGNATURE_SIGNATURE_NONCE = UUID.randomUUID().toString();
        String signature = apiSignature.encode(SIGNATURE_VERSION, SIGNATURE_ACCESS_KEY_ID, SIGNATURE_METHOD, SIGNATURE_TIMESTAMP, SIGNATURE_SIGNATURE_VERSION, SIGNATURE_SIGNATURE_NONCE);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("signature", signature);
        jsonObject.put("nonce", SIGNATURE_SIGNATURE_NONCE);
        jsonObject.put("timestamp", SIGNATURE_TIMESTAMP);
        return jsonObject;
    }
}