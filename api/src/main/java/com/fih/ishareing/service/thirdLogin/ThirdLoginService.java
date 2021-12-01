package com.fih.ishareing.service.thirdLogin;

import com.alibaba.fastjson.JSONObject;
import com.fih.ishareing.service.thirdLogin.model.FubonUserValidVO;
import com.fih.ishareing.service.thirdLogin.model.TwmUserValidVO;

public interface ThirdLoginService {
    JSONObject validFubonUsers(FubonUserValidVO user);

    JSONObject validTwmUsers(TwmUserValidVO user);
}