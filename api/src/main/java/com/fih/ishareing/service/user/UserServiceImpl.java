package com.fih.ishareing.service.user;

import com.fih.ishareing.constant.ApiErrorConstant;
import com.fih.ishareing.controller.base.error.ApiErrorResponse;
import com.fih.ishareing.controller.base.vo.result.ApiBatchResult;
import com.fih.ishareing.errorHandling.ErrorHandlingConstants.ERROR_BUNDLE;
import com.fih.ishareing.repository.AuthUserRepository;
import com.fih.ishareing.repository.UserRepository;
import com.fih.ishareing.repository.entity.tbAuthUser;
import com.fih.ishareing.repository.pocservice.entity.User;
import com.fih.ishareing.service.AbstractAuthenticationService;
import com.fih.ishareing.service.token.TokenService;
import com.fih.ishareing.service.user.model.AuthUser;
import com.fih.ishareing.service.user.model.IeqUser;
import com.fih.ishareing.service.user.model.UserAddVO;
import com.fih.ishareing.service.user.model.UserUpdateVO;
import com.fih.ishareing.service.user.model.UserVO;
import com.fih.ishareing.validators.EmailValidator;
import com.google.common.base.Preconditions;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends AbstractAuthenticationService implements UserService, UserDetailsService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthUserRepository AuthUserRepo;
    
    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private LoadingCache<String, Optional<String>> applicatonNames;

//    public UserServiceImpl() {
//        CacheLoader<String, Optional<String>> cacheLoader = new CacheLoader<String, Optional<String>>() {
//            @Override
//            public Optional<String> load(String key) {
//                return applicationService.findApplicationName(key);
//            }
//        };
//
//        this.applicatonNames = CacheBuilder.newBuilder().expireAfterWrite(3, TimeUnit.MINUTES).build(cacheLoader);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        tbAuthUser userValue = AuthUserRepo.findByUsernameAndIsActive(username, 1); //userRepository.findByAccountAndActiveTrueAndEnableTrue(username);
        if (userValue != null) {
        	tbAuthUser user = userValue;

            Set<GrantedAuthority> authoritys = new HashSet<>();
            boolean forceResetPassword = false;
            return new AuthUser(user.getId(), "twm", (user.getIsSu() == 1)?true:false, (user.getIsSu() == 1)?true:false,
                    forceResetPassword, user.getUsername(), user.getPassword(), authoritys);
        }

        throw new UsernameNotFoundException("account not found");
    }

    @Override
    public long count(Specification<User> spec) {
        return userRepository.count(spec);
    }

    @Override
    public boolean exist(String account) {
        Preconditions.checkArgument(StringUtils.isNotBlank(account));
        return AuthUserRepo.existsByUsernameAndIsActive(account, 1);
    }

    @Override
    public List<UserVO> findUsers(Specification<User> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable).stream().map(this::transfer).collect(Collectors.toList());
    }

    @Override
    public List<?> addUsers(List<UserAddVO> users) {
        ApiBatchResult result = new ApiBatchResult();
        for (int i = 0; i < users.size(); i++) {
            UserAddVO user = users.get(i);
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            builder.put(ApiErrorConstant.INDEX, i);

            if (StringUtils.isBlank(user.getAccount())) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.invalidRequest.name(), "Account must not be null or emtpy"));
            } else {
                builder.put(ApiErrorConstant.ACCOUNT, user.getAccount());
            }

            if (StringUtils.isBlank(user.getPassword())) {
                errors.add(new ApiErrorResponse(ERROR_BUNDLE.invalidPassword));
            }

            if (StringUtils.isBlank(user.getEmail()) || !emailValidator.validate(user.getEmail())) {
                errors.add(new ApiErrorResponse(ERROR_BUNDLE.badEmail));
            }

            // check create site admin user only one and appcode exist
            if (isSu()) {
//                boolean exist = applicationService.existAppCode(user.getAppCode());
//                if (exist) {
//                    exist = userRepository.existsByApplicationCodeAndActiveTrueAndAdminUserTrue(user.getAppCode());
//                    if (exist) {
//                        errors.add(new ApiErrorResponse(ERROR_BUNDLE.userAccountExists, "Admin user already exist"));
//                    }
//                } else {
//                    errors.add(new ApiErrorResponse(ERROR_BUNDLE.applicationNotFound));
//                }
            }

            if (CollectionUtils.isEmpty(errors)) {
                try {
                    boolean exist = userRepository.existsByAccountAndActiveTrueAndEnableTrue(user.getAccount());
                    if (exist) {
                        errors.add(new ApiErrorResponse(ERROR_BUNDLE.userAccountExists));
                        builder.put(ApiErrorConstant.ERRORS, errors);
                    } else {
                        if (StringUtils.isBlank(user.getRoleCode())) {
                            userRepository.save(createUserInstance(user));
                        } else {
//                            Optional<Role> roleValue = roleRepository.findByCodeAndApplicationCodeAndActiveTrue(
//                                    user.getRoleCode(), getApplicationCode());
//                            if (roleValue.isPresent()) {
//                                userRepository.save(createUserInstance(user, roleValue.get()));
//                            } else {
//                                errors.add(new ApiErrorResponse(ERROR_BUNDLE.userRoleNotFound));
//                                builder.put(ApiErrorConstant.ERRORS, errors);
//                            }
                        }
                    }
                } catch (DataIntegrityViolationException ex) {
                    logger.error("Add new user fail, account:{}, error:{}", user.getAccount(), ex.getMessage());
                    errors.add(new ApiErrorResponse(ERROR_BUNDLE.userAccountExists));
                    builder.put(ApiErrorConstant.ERRORS, errors);
                } catch (Exception ex) {
                    logger.error("An error occurred while add new user, account:{}, error:{}", user.getAccount(),
                            ex.getMessage());
                    errors.add(new ApiErrorResponse(ERROR_BUNDLE.internalError));
                    builder.put(ApiErrorConstant.ERRORS, errors);
                }
            } else {
                builder.put(ApiErrorConstant.ERRORS, errors);
            }

            result.add(builder.build());
        }

        return result.build();
    }

    @Override
    public List<?> updateUsers(List<UserUpdateVO> users) {
        Date now = new Date();
        int createdUserId = getUserId();
        ApiBatchResult result = new ApiBatchResult();
        for (int i = 0; i < users.size(); i++) {
            UserUpdateVO user = users.get(i);
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            builder.put(ApiErrorConstant.INDEX, i);

            if (StringUtils.isBlank(user.getCode())) {
                errors.add(new ApiErrorResponse(ERROR_BUNDLE.invalidRequest.name(), "Code must not be null or emtpy"));
            } else {
                builder.put(ApiErrorConstant.CODE, user.getCode());
            }

            UUID userCode = null;
            try {
                userCode = UUID.fromString(user.getCode());
            } catch (IllegalArgumentException | NullPointerException ex) {
                errors.add(new ApiErrorResponse(ERROR_BUNDLE.invalidRequest.name(), "User code is invalid"));
            }

            if (CollectionUtils.isEmpty(errors)) {
                try {
                    Optional<User> userValue = null; //userRepository.findByCodeAndActiveTrue(userCode);
                    if (userValue.isPresent()) {
                        User entity = userValue.get();

                        if (user.getEnable() != null) {
                            entity.setEnable(user.getEnable());
                        }

                        if (StringUtils.isNotBlank(user.getName())) {
                            entity.setName(user.getName());
                        }

                        if (user.getPhone() != null) {
                            entity.setPhone(user.getPhone());
                        }

                        if (user.getPassword() != null) {
                            if (StringUtils.isNotBlank(user.getPassword())) {
                                entity.setPassword(passwordEncoder.encode(user.getPassword().trim()));
                                entity.setLastUpdatePasswordTime(now);
                            } else {
                                errors.add(new ApiErrorResponse(ERROR_BUNDLE.invalidPassword));
                            }
                        }

                        if (user.getResetPassword() != null) {
                            entity.setForceResetPassword(user.getResetPassword());
                        }

                        if (user.getEmail() != null) {
                            if (emailValidator.validate(user.getEmail())) {
                                entity.setEmail(user.getEmail());
                            } else {
                                errors.add(new ApiErrorResponse(ERROR_BUNDLE.badEmail));
                            }
                        }

                        entity.setModifiedUserId(createdUserId);
                        entity.setModifiedTime(now);

                        if (CollectionUtils.isEmpty(errors)) {
                            userRepository.save(entity);
                        } else {
                            builder.put(ApiErrorConstant.ERRORS, errors);
                        }

                    } else {
                        errors.add(new ApiErrorResponse(ERROR_BUNDLE.userNotFound));
                        builder.put(ApiErrorConstant.ERRORS, errors);
                    }
                } catch (Exception ex) {
                    logger.error("An error occurred while updating user, code:{}, error:{}", user.getCode(),
                            ex.getMessage());
                    errors.add(new ApiErrorResponse(ERROR_BUNDLE.internalError));
                    builder.put(ApiErrorConstant.ERRORS, errors);
                }
            } else {
                builder.put(ApiErrorConstant.ERRORS, errors);
            }

            result.add(builder.build());
        }

        return result.build();
    }

    @Override
    public List<?> deleteUsers(List<String> codes) {
        ApiBatchResult result = new ApiBatchResult();

        for (int i = 0; i < codes.size(); i++) {
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            String code = codes.get(i);
            builder.put(ApiErrorConstant.INDEX, i);
            builder.put(ApiErrorConstant.CODE, code);
            try {
                UUID userCode = UUID.fromString(code);
                Optional<User> userValue = null; //userRepository.findByCodeAndActiveTrueAndEnableTrue(userCode);
                if (userValue.isPresent()) {
                    userRepository.delete(userValue.get());
                    tokenService.removeTokenUseAccount(userValue.get().getAccount());
                } else {
                    errors.add(new ApiErrorResponse(ERROR_BUNDLE.userNotFound));
                    builder.put(ApiErrorConstant.ERRORS, errors);
                }
            } catch (IllegalArgumentException ex) {
                logger.error("User is invalid, code:{}, error:{}", code, ex.getMessage());
                errors.add(new ApiErrorResponse(ERROR_BUNDLE.invalidRequest, "User is invalid"));
                builder.put(ApiErrorConstant.ERRORS, errors);
            } catch (Exception ex) {
                logger.error("An error occurred while deleting user, code:{}, error:{}", code, ex.getMessage());
                errors.add(new ApiErrorResponse(ERROR_BUNDLE.internalError));
                builder.put(ApiErrorConstant.ERRORS, errors);
            }

            result.add(builder.build());
        }

        return result.build();
    }


    private UserVO transfer(User user) {
        UserVO instance = new UserVO();
        instance.setId(user.getId());
        instance.setCode(user.getCode().toString());
        instance.setActive(user.getActive());
        instance.setEnable(user.getEnable());
        instance.setValidated(user.getValidated());
        instance.setAppCode(user.getApplicationCode());

        try {
            Optional<String> appName = this.applicatonNames.get(user.getApplicationCode());
            if (appName.isPresent())
                instance.setAppName(appName.get());
            else
                instance.setAppName(user.getApplicationCode());
        } catch (ExecutionException e) {
            logger.error("Get application name error, error:{}", e.getMessage());
            instance.setAppName(user.getApplicationCode());
        }

        instance.setAccount(user.getAccount());
        instance.setName(user.getName());
        instance.setEmail(user.getEmail());
        instance.setPhone(user.getPhone());


        instance.setModifiesAt(user.getModifiedTime());
        return instance;
    }

    private User createUserInstance(UserAddVO newUser) {
        User instance = new User();
        instance.setCode(UUID.randomUUID());
        instance.setEnable(true);
        instance.setActive(true);

        instance.setSuUser(false);
        if (isSu()) {
            instance.setAdminUser(true);
            instance.setApplicationCode(newUser.getAppCode());
        } else {
            instance.setAdminUser(false);
            instance.setApplicationCode(getApplicationCode());
        }

        instance.setForceResetPassword(newUser.getResetPassword() == null ? false : newUser.getResetPassword());
        instance.setValidated(false);
        instance.setAccount(newUser.getAccount());
        instance.setName(newUser.getName());
        instance.setForceResetPassword(newUser.getResetPassword());
        instance.setEmail(newUser.getEmail());
        instance.setPhone(newUser.getPhone());
        instance.setPassword(passwordEncoder.encode(newUser.getPassword()));


        int createdUserId = getUserId();
        instance.setCreatedUserId(createdUserId);
        instance.setModifiedUserId(createdUserId);

        Date now = new Date();
        instance.setCreatedTime(now);
        instance.setModifiedTime(now);
        return instance;

    }

}