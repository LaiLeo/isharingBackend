package com.fih.ishareing.service.user;

import com.fih.ishareing.service.user.model.UserPasswordUpdateVO;
import com.fih.ishareing.service.user.model.UserProfileVO;

public interface UserProfileService {
    
    UserProfileVO getProfile();

    void modifyPassword(UserPasswordUpdateVO userPasswordUpdate);
}
