package com.fih.ishareing.controller.v1.user;

import com.fih.ishareing.controller.base.BaseController;
import com.fih.ishareing.service.auth.AuthService;
import com.fih.ishareing.service.user.UserProfileService;
import com.fih.ishareing.service.user.model.UserPasswordUpdateVO;
import com.fih.ishareing.service.user.model.UserProfileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserProfileController extends BaseController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private AuthService authService;

    @GetMapping(value = "/profile")
    public UserProfileVO getProfile() {
        return userProfileService.getProfile();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/password")
    public void modifyPassowrd(@RequestBody UserPasswordUpdateVO userPasswordUpdate) {
        userProfileService.modifyPassword(userPasswordUpdate);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/token")
    public void logout() {
        authService.logout();
    }
}
