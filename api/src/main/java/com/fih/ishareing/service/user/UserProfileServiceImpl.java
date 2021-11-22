package com.fih.ishareing.service.user;

import java.util.Optional;

import com.fih.ishareing.errorHandling.ErrorHandlingConstants.ERROR_BUNDLE;
import com.fih.ishareing.errorHandling.base.ResourceNotFoundException;
import com.fih.ishareing.errorHandling.exceptions.PasswordNotMatchException;
import com.fih.ishareing.repository.UserRepository;
import com.fih.ishareing.repository.pocservice.entity.User;
import com.fih.ishareing.service.AbstractAuthenticationService;
import com.fih.ishareing.service.user.model.UserPasswordUpdateVO;
import com.fih.ishareing.service.user.model.UserProfileVO;
import com.google.common.base.Preconditions;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl extends AbstractAuthenticationService implements UserProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserProfileVO getProfile() {
        Optional<User> userValue = null; //userRepository.findByAccountAndActiveTrueAndEnableTrue(getUserAccount());
        if (userValue.isPresent()) {
            User user = userValue.get();
            UserProfileVO profile = new UserProfileVO();
            profile.setId(user.getId());
            profile.setCode(user.getCode().toString());
            profile.setAccount(user.getAccount());
            profile.setName(user.getName());
            return profile;
        } else {
            throw new ResourceNotFoundException(ERROR_BUNDLE.userNotFound);
        }
    }

    @Override
    public void modifyPassword(UserPasswordUpdateVO userPassword) {
        Preconditions.checkArgument(StringUtils.isNotBlank(userPassword.getOldPassword()), "oldPassword");
        Preconditions.checkArgument(StringUtils.isNotBlank(userPassword.getNewPassword()), "newPassword");

        Optional<User> userValue = null; //userRepository.findByAccountAndActiveTrueAndEnableTrue(getUserAccount());
        if (userValue.isPresent()) {
            User user = userValue.get();
            boolean matchPass = passwordEncoder.matches(userPassword.getOldPassword(), user.getPassword());
            if (matchPass) {
                String newPass = passwordEncoder.encode(userPassword.getNewPassword());
                userRepository.modifyPassword(user.getId(), newPass);
            } else {
                throw new PasswordNotMatchException();
            }
        } else {
            throw new ResourceNotFoundException(ERROR_BUNDLE.userNotFound);
        }
    }

}
