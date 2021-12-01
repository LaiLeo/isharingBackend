package com.fih.ishareing.service.user;

import com.fih.ishareing.repository.entity.*;
import com.fih.ishareing.repository.pocservice.entity.User;
import com.fih.ishareing.service.thirdLogin.model.FubonUserValidVO;
import com.fih.ishareing.service.user.model.AuthUserAddVO;
import com.fih.ishareing.service.user.model.AuthUserUpdateVO;
import com.fih.ishareing.service.user.model.TwmEnterpriseUserValidVO;
import com.fih.ishareing.service.user.model.UserLicenseImagesVO;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface UserService {
    long count(Specification<User> spec);

    boolean exist(String account);

    boolean exist(Integer id);

    boolean existLicenseImage(Integer id);

    vwUsers findUserByUserName(String username);

    vwUsers findUserByUserId(Integer userId);

    JSONObject listUsers(Specification<vwUsers> spec, Pageable pageable);

    List<?> addUsers(List<AuthUserAddVO> users);

    List<?> updateUsers(List<AuthUserUpdateVO> users);

    List<?> deleteUsers(List<String> accounts);
    
    void updateUserPhoto(Integer userid, String photo);
    
    void updateUserIcon(Integer userid, String icon);
    
    void updateUserLicenseImage(Integer userid, String image, String thumbpath);
    
    void removeUserPhoto(Integer userid);
    
    void removeUserIcon(Integer userid);
    
    void removeUserLicense(Integer licenseId);
    
	void updatePassword(Integer userid, String newpassword);
	
	Boolean checkPasswordCorrect(Integer userid, String oldpassword);
	
    JSONObject listUserRegisteredEvent(Specification<vwUserRegisteredEvent> spec, Pageable pageable);

    List<?> getUsersMenu();

    com.alibaba.fastjson.JSONObject validFubonUser(FubonUserValidVO user);

    com.alibaba.fastjson.JSONObject validTwmUser(TwmEnterpriseUserValidVO user);

    long countLicenseImages(Specification<tbCoreUserLicenseImage> spec);

    List<UserLicenseImagesVO> findLicenseImages(Specification<tbCoreUserLicenseImage> spec, Pageable pageable);

    JSONObject listUserFocusedEvent(Specification<vwUserFocusedEvent> spec, Pageable pageable);

    JSONObject listUserVisitEvent(Specification<vwEventVisitHistory> spec, Pageable pageable);

    JSONObject listUserSubscribeNpos(Specification<vwUserSubscribedNpos> spec, Pageable pageable);

    com.alibaba.fastjson.JSONObject unbindFubon();

    com.alibaba.fastjson.JSONObject unbindTwmEnterprise();

    com.alibaba.fastjson.JSONObject getVolunteerTime(String startTime, String endTime);
}