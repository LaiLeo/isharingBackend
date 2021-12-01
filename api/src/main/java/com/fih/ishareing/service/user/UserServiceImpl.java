package com.fih.ishareing.service.user;

import com.fih.ishareing.configurations.security.Pbkdf2Sha256PasswordEncoder;
import com.fih.ishareing.constant.ApiErrorConstant;
import com.fih.ishareing.controller.base.error.ApiErrorResponse;
import com.fih.ishareing.controller.base.vo.result.ApiBatchResult;
import com.fih.ishareing.errorHandling.ErrorHandlingConstants;
import com.fih.ishareing.errorHandling.ErrorHandlingConstants.ERROR_BUNDLE;
import com.fih.ishareing.errorHandling.exceptions.InternalErrorException;
import com.fih.ishareing.errorHandling.exceptions.UserNotFoundException;
import com.fih.ishareing.repository.*;
import com.fih.ishareing.repository.entity.*;
import com.fih.ishareing.repository.pocservice.entity.User;
import com.fih.ishareing.service.AbstractService;
import com.fih.ishareing.service.auth.model.AuthTokenRespVO;
import com.fih.ishareing.service.resource.ResourceService;
import com.fih.ishareing.service.thirdLogin.model.FubonUserValidVO;
import com.fih.ishareing.service.token.TokenService;
import com.fih.ishareing.service.user.model.*;
import com.fih.ishareing.utils.MD5Utils;
import com.fih.ishareing.utils.date.DateUtils;
import com.fih.ishareing.validators.EmailValidator;
import com.fih.ishareing.validators.PasswordValidator;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends AbstractService implements UserService, UserDetailsService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthUserRepository AuthUserRepo;

    @Autowired
    private vwUserRepository vwUserRepo;

    @Autowired
    private vwUserMenuRepository vwUserMenuRepo;

    @Autowired
    private CoreUserAccountRepository coreUserAccountRepo;

    @Autowired
    private CoreUserLicenseImageRepository coreUserLicenseImageRepo;

    @Autowired
    private vwUserRegisteredEventRepository vwUserRegisteredEventRepo;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private CoreUserThirdRepository userThirdRep;

    @Autowired
    private MD5Utils md5Utils;

    @Autowired
    private CoreUserLicenseImageRepository coreUserLicenseImageRep;

    @Autowired
    private vwUserFocusedEventRepository vwUserFocusedEventRep;

    @Autowired
    private vwEventVisitHistoryRepository vwEventVisitHistoryRep;

    @Autowired
    private vwUserSubscribedNposRepository vwUserSubscribedNposRep;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private AuthRepository authRep;

    @Autowired
    private CoreEventVisithistoryRepository eventVisithistoryRep;

    @Autowired
    private CoreNpoRepository npoRep;

    @Autowired
    private CorePushMessageRepository pushMessageRep;

    @Autowired
    private CoreReplyRepository replyRep;

    @Autowired
    private CoreUserFocusedEventRepository userFocusedEventRep;

    @Autowired
    private CoreUserLicenseImageRepository userLicenseImageRep;

    @Autowired
    private CoreUserRegisteredEventRepository userRegisteredEventRep;

    @Autowired
    private CoreUserSubscribeNpoRepository userSubscribeNpoRep;

    @Autowired
    private PushNotificationsApnsDeviceRepository pushNotificationsApnsDeviceRep;

    @Autowired
    private PushNotificationsGcmDeviceRepository pushNotificationsGcmDeviceRep;

    @Autowired
    private PasswordValidator passwordValidator;

    @Autowired
    private vwUserRegisteredEventRepository vwUserRegisteredEventRep;

    @Autowired
    private ThirdTwmEnterpriseRepository thirdTwmEnterpriseRep;

    @Autowired
    private ThirdTwmEnterpriseInfoRepository thirdTwmEnterpriseInfoRep;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        tbAuthUser userValue = AuthUserRepo.findByUsernameAndIsActive(username, 1); //userRepository.findByAccountAndActiveTrueAndEnableTrue(username);
        if (userValue != null) {
            tbAuthUser user = userValue;

            Set<GrantedAuthority> authoritys = new HashSet<>();
            boolean forceResetPassword = false;
            return new AuthUser(user.getId(), "twm", (user.getIsSu() == 1) ? true : false, (user.getIsSu() == 1) ? true : false,
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
    public boolean exist(Integer id) {
        // TODO Auto-generated method stub
        return AuthUserRepo.existsByIdAndIsActive(id, 1);
    }

    @Override
    public boolean existLicenseImage(Integer id) {
        return userLicenseImageRep.existsById(id);
    }

    @Override
    public JSONObject listUsers(Specification<vwUsers> QuerySpec, Pageable QueryPageable) {
        JSONObject JOQueryResult = new JSONObject();
        List<AuthUserListRespVO> listAuthUserList = new ArrayList<AuthUserListRespVO>();

        List<vwUsers> listVWUsers = vwUserRepo.findAll(QuerySpec, QueryPageable).getContent();
        for (vwUsers vwUser : listVWUsers) {
            AuthUserListRespVO AuthUserListRespObj = new AuthUserListRespVO();

            String str = vwUser.getEventHour().toString();
            Integer in = vwUser.getEventHour().intValue();
            AuthUserListRespObj.setId(vwUser.getId());
            AuthUserListRespObj.setActive((vwUser.getIsActive() == 1));
            AuthUserListRespObj.setUsername(vwUser.getUsername());
            AuthUserListRespObj.setFirstName(vwUser.getFirstname());
            AuthUserListRespObj.setLastName(vwUser.getLastname());
            AuthUserListRespObj.setEmail(vwUser.getEmail());
            AuthUserListRespObj.setStaff(vwUser.getStaff().toString());
            AuthUserListRespObj.setLastLogin(vwUser.getLastLogin());
            AuthUserListRespObj.setDateJoined(vwUser.getDateJoined());
            AuthUserListRespObj.setUid(vwUser.getUid());
            if (vwUser.getPhoto() != null) {
                AuthUserListRespObj.setPhoto(strHTTPServerHost + vwUser.getPhoto());
            }
            AuthUserListRespObj.setName(vwUser.getName());
            AuthUserListRespObj.setPhone(vwUser.getPhone());
            AuthUserListRespObj.setSecurityId(vwUser.getSecurityId());
            AuthUserListRespObj.setSkillsDescription(vwUser.getSkillsDescription());
            if (vwUser.getBirthday().equalsIgnoreCase("1900-01-01")) {
                AuthUserListRespObj.setBirthday("");
            } else {
                AuthUserListRespObj.setBirthday(vwUser.getBirthday());
            }
            AuthUserListRespObj.setGuardianName(vwUser.getGuardianName());
            AuthUserListRespObj.setGuardianPhone(vwUser.getGuardianPhone());
            if (vwUser.getIcon() != null) {
                AuthUserListRespObj.setIcon(strHTTPServerHost + vwUser.getIcon());
            }
            if (vwUser.getThumbPath() != null) {
                AuthUserListRespObj.setThumbPath(strHTTPServerHost + vwUser.getThumbPath());
            }
            AuthUserListRespObj.setPublic(vwUser.getPublic());
            AuthUserListRespObj.setInterest(vwUser.getInterest());
            AuthUserListRespObj.setAboutMe(vwUser.getAboutMe());
            AuthUserListRespObj.setScore(vwUser.getScore());
            AuthUserListRespObj.setRanking(vwUser.getRanking());
            AuthUserListRespObj.setEventHour(vwUser.getEventHour());
            AuthUserListRespObj.setEventNum(vwUser.getEventNum());
            AuthUserListRespObj.setEventEnterpriseHour(vwUser.getEventEnterpriseHour());
            AuthUserListRespObj.setEventGeneralHour(vwUser.getEventGeneralHour());
            AuthUserListRespObj.setFubon(vwUser.getFubon());
            AuthUserListRespObj.setTwm(vwUser.getTwm());

            if (vwUser.getUserLicenseImageList() != null && vwUser.getUserLicenseImageList().size() > 0) {
                List<UserLicenseImagesVO> userLicenseImagesVOList = new ArrayList<>();
                for (int i = 0; i < vwUser.getUserLicenseImageList().size(); i++) {
                    tbCoreUserLicenseImage licenseImage = vwUser.getUserLicenseImageList().get(i);
                    userLicenseImagesVOList.add(new UserLicenseImagesVO(licenseImage.getId(), licenseImage.getUserId(), strHTTPServerHost + licenseImage.getImage(), strHTTPServerHost + licenseImage.getThumbPath()));
                }
                if (userLicenseImagesVOList.size() > 0) {
                    AuthUserListRespObj.setLicenseImages(userLicenseImagesVOList);
                }
            }

            listAuthUserList.add(AuthUserListRespObj);
        }

        JOQueryResult.put("Count", vwUserRepo.count(QuerySpec));
        JOQueryResult.put("Results", listAuthUserList);

        return JOQueryResult;
    }

    @Transactional
    @Override
    public List<?> addUsers(List<AuthUserAddVO> users) {
        Timestamp now = DateUtils.GetNowTimestamp();
        Pbkdf2Sha256PasswordEncoder Pbkdf2Sha256PasswordEncoderObj = new Pbkdf2Sha256PasswordEncoder();

        ApiBatchResult result = new ApiBatchResult();
        for (int i = 0; i < users.size(); i++) {
            tbAuthUser tbAuthUserSaved = null;
            AuthUserAddVO user = users.get(i);
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            builder.put(ApiErrorConstant.INDEX, i);
            String thirdPartyInfoString = null;
            if (StringUtils.isBlank(user.getUsername())) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.invalidRequest.name(), "Username must not be null or emtpy"));
            } else {
                builder.put(ApiErrorConstant.ACCOUNT, user.getUsername());
            }

            if (StringUtils.isBlank(user.getPassword())) {
                errors.add(new ApiErrorResponse(ERROR_BUNDLE.invalidPassword));
            }
            if (!StringUtils.isBlank(user.getPassword()) && !passwordValidator.validate(user.getPassword())) {
                errors.add(new ApiErrorResponse(ERROR_BUNDLE.badPassword));
            }

            if (StringUtils.isBlank(user.getEmail()) || !emailValidator.validate(user.getEmail())) {
                errors.add(new ApiErrorResponse(ERROR_BUNDLE.badEmail));
            }

            if (user.getAccessCode() != null) {
                thirdPartyInfoString = redisTemplate.opsForValue().get(user.getAccessCode());
                if (thirdPartyInfoString == null) {
                    errors.add(new ApiErrorResponse(ERROR_BUNDLE.accessTokenTimeout.name(), ERROR_BUNDLE.accessTokenTimeout.getDesc()));
                }
            }

            // check create site admin user only one and appcode exist
//            if (isSu()) {
//                boolean exist = applicationService.existAppCode(user.getAppCode());
//                if (exist) {
//                    exist = userRepository.existsByApplicationCodeAndActiveTrueAndAdminUserTrue(user.getAppCode());
//                    if (exist) {
//                        errors.add(new ApiErrorResponse(ERROR_BUNDLE.userAccountExists, "Admin user already exist"));
//                    }
//                } else {
//                    errors.add(new ApiErrorResponse(ERROR_BUNDLE.applicationNotFound));
//                }
//            }

            if (CollectionUtils.isEmpty(errors)) {
                try {
                    boolean exist = AuthUserRepo.existsByUsernameAndIsActive(user.getUsername(), 1);
                    //userRepository.existsByAccountAndActiveTrueAndEnableTrue(user.getAccount());
                    if (exist) {
                        errors.add(new ApiErrorResponse(ERROR_BUNDLE.userAccountExists));
                        builder.put(ApiErrorConstant.ERRORS, errors);
                    } else {
                        String strPassword = Pbkdf2Sha256PasswordEncoderObj.encode(user.getPassword());
                        com.alibaba.fastjson.JSONObject thirdPartyInfo = null;
                        tbAuthUser tbAuthUserObj = new tbAuthUser();
                        tbAuthUserObj.setPassword(strPassword);
                        tbAuthUserObj.setLastLogin(now);
                        tbAuthUserObj.setIsSu((user.getIsSuperuser() != null && user.getIsSuperuser()) ? 1 : 0);
                        tbAuthUserObj.setUsername(user.getUsername());
                        tbAuthUserObj.setFirstname(user.getFirstName());
                        tbAuthUserObj.setLastname(user.getLastName());
                        tbAuthUserObj.setEmail("");
                        tbAuthUserObj.setIsStaff((user.getIsStaff() != null && user.getIsStaff()) ? 1 : 0);
                        tbAuthUserObj.setIsActive(1);
                        tbAuthUserObj.setDateJoined(now);
                        tbAuthUserObj.setResource("isharing");
                        if (thirdPartyInfoString != null) {
                            thirdPartyInfo = com.alibaba.fastjson.JSONObject.parseObject(thirdPartyInfoString);
                            tbAuthUserObj.setResource(thirdPartyInfo.getString("enterpriseSerialCode"));
                        }

                        tbAuthUserSaved = AuthUserRepo.save(tbAuthUserObj);

                        if (tbAuthUserSaved != null) {
                            builder.put(ApiErrorConstant.ID, tbAuthUserSaved.getId());

                            tbCoreUserAccount tbCoreUserAccountObj = new tbCoreUserAccount();
                            tbCoreUserAccountObj.setId(tbAuthUserSaved.getId());
                            tbCoreUserAccountObj.setUserId(tbAuthUserSaved.getId());
                            tbCoreUserAccountObj.setEmail(tbAuthUserSaved.getEmail());
                            tbCoreUserAccountObj.setIcon("default_userprofile_image.png");
                            tbCoreUserAccountObj.setPhoto("default_userprofile_image.png");
                            tbCoreUserAccountObj.setIsPublic(0);
                            tbCoreUserAccountObj.setBirthday("1900-01-01");
                            tbCoreUserAccountObj.setEventHour(0D);
                            tbCoreUserAccountObj.setEventGeneralHour(0D);
                            tbCoreUserAccountObj.setEventEnterpriseHour(0D);

                            coreUserAccountRepo.save(tbCoreUserAccountObj);

                            Authentication authentication = authenticationManager
                                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
                            AuthTokenRespVO AuthTokenRespVOObj = tokenService.generateToken(authentication);

                            builder.put(ApiErrorConstant.TOKEN, AuthTokenRespVOObj);

                            //save thirdParty info
                            if (thirdPartyInfo != null) {
                                tbCoreUserThird tbCoreUserThird = new tbCoreUserThird();
                                tbCoreUserThird.setUserId(tbAuthUserSaved.getId());
                                tbCoreUserThird.setEnterpriseSerialCode(thirdPartyInfo.getString("enterpriseSerialCode"));
                                tbCoreUserThird.setEnterpriseSerialNumber(thirdPartyInfo.getString("enterpriseSerialNumber"));
                                String enterpriseSeralEmail = (thirdPartyInfo.getString("enterpriseSerialEmail") != null) ? thirdPartyInfo.getString("enterpriseSerialEmail") : tbAuthUserSaved.getUsername();
                                tbCoreUserThird.setEnterpriseSerialEmail(enterpriseSeralEmail);

                                userThirdRep.save(tbCoreUserThird);
                            }
                        }

                    }
                } catch (DataIntegrityViolationException ex) {
                    logger.error("Add new user fail, account:{}, error:{}", user.getUsername(), ex.getMessage());
                    errors.add(new ApiErrorResponse(ERROR_BUNDLE.userAccountExists));
                    builder.put(ApiErrorConstant.ERRORS, errors);
                } catch (Exception ex) {
                    logger.error("An error occurred while add new user, account:{}, error:{}", user.getUsername(),
                            ex.getMessage());
                    errors.add(new ApiErrorResponse(ERROR_BUNDLE.internalError));
                    builder.put(ApiErrorConstant.ERRORS, errors);
                }
            } else {
                builder.put(ApiErrorConstant.ERRORS, errors);
//                builder.put(ApiErrorConstant.ID, 0);
            }

            result.add(builder.build());
        }

        return result.build();
    }

    @Transactional
    @Override
    public List<?> updateUsers(List<AuthUserUpdateVO> users) {
        ApiBatchResult result = new ApiBatchResult();
        for (int i = 0; i < users.size(); i++) {
            AuthUserUpdateVO user = users.get(i);
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            builder.put(ApiErrorConstant.INDEX, i);
            builder.put(ApiErrorConstant.USERNAME, user.getUsername());

            if (CollectionUtils.isEmpty(errors)) {
                try {
                    tbAuthUser tbAuthUserObj = AuthUserRepo.findByUsername(user.getUsername());
                    if (tbAuthUserObj != null) {
                        tbCoreUserAccount tbCoreUserAccountObj = coreUserAccountRepo.findByUserId(tbAuthUserObj.getId());
                        if (user.getFirstName() != null) {
                            tbAuthUserObj.setFirstname(user.getFirstName());
                        }
                        if (user.getLastName() != null) {
                            tbAuthUserObj.setLastname(user.getLastName());
                        }
                        if (StringUtils.isNotBlank(user.getEmail())) {
                            if (emailValidator.validate(user.getEmail())) {
                                tbAuthUserObj.setEmail(user.getEmail());
                            } else {
                                errors.add(new ApiErrorResponse(ERROR_BUNDLE.badEmail));
                            }
                        }

                        if (user.getIsStaff() != null) {
                            tbAuthUserObj.setIsStaff(user.getIsStaff() ? 1 : 0);
                        }
                        if (StringUtils.isNotBlank(user.getBirthday())) {
                            String strBirthday = user.getBirthday();
                            tbCoreUserAccountObj.setBirthday(strBirthday);
                        }
                        if (user.getName() != null) {
                            tbCoreUserAccountObj.setName(user.getName());
                        }
                        if (user.getPhone() != null) {
                            tbCoreUserAccountObj.setPhone(user.getPhone());
                        }
                        if (StringUtils.isNotBlank(user.getEmail())) {
                            tbCoreUserAccountObj.setEmail(user.getEmail());
                        }
                        if (user.getSkillsDescription() != null) {
                            tbCoreUserAccountObj.setSkillsDescription(user.getSkillsDescription());
                        }
                        if (user.getGuardianName() != null) {
                            tbCoreUserAccountObj.setGuardianName(user.getGuardianName());
                        }
                        if (user.getGuardianPhone() != null) {
                            tbCoreUserAccountObj.setGuardianPhone(user.getGuardianPhone());
                        }
                        if (user.getInterest() != null) {
                            tbCoreUserAccountObj.setInterest(user.getInterest());
                        }
                        if (user.getAboutMe() != null) {
                            tbCoreUserAccountObj.setAboutMe(user.getAboutMe());
                        }
                        if (user.getSercurityId() != null) {
                            tbCoreUserAccountObj.setSecurityId(user.getSercurityId());
                        }
                        if (user.getIsPublic() != null) {
                            tbCoreUserAccountObj.setIsPublic(user.getIsPublic() ? 1 : 0);
                        }
                        if (user.getRanking() != null) {
                            tbCoreUserAccountObj.setRanking(user.getRanking());
                        }
                        if (user.getScore() != null) {
                            tbCoreUserAccountObj.setScore(user.getScore());
                        }
                        if (user.getEventHour() != null) {
                            tbCoreUserAccountObj.setEventHour((double) user.getEventHour());
                        }
                        if (user.getEventNum() != null) {
                            tbCoreUserAccountObj.setEventNum(user.getEventNum());
                        }
                        if (user.getEventEnterpriseHour() != null) {
                            tbCoreUserAccountObj.setEventEnterpriseHour((double) user.getEventEnterpriseHour());
                        }
                        if (user.getEventGeneralHour() != null) {
                            tbCoreUserAccountObj.setEventGeneralHour((double) user.getEventGeneralHour());
                        }

                        if (CollectionUtils.isEmpty(errors)) {
                            AuthUserRepo.save(tbAuthUserObj);
                            coreUserAccountRepo.save(tbCoreUserAccountObj);

                            if (user.getFubonAccount() != null && user.getFubonAccount().equalsIgnoreCase("")) {
                                userThirdRep.deleteByUserIdAndEnterpriseSerialCode(getUserId(), "fubon");
                            }
                            if (user.getTwmAccount() != null && user.getTwmAccount().equalsIgnoreCase("")) {
                                userThirdRep.deleteByUserIdAndEnterpriseSerialCode(getUserId(), "twmEnterprise");
                            }
                        } else {
                            builder.put(ApiErrorConstant.ERRORS, errors);
                        }

                    } else {
                        errors.add(new ApiErrorResponse(ERROR_BUNDLE.userNotFound));
                        builder.put(ApiErrorConstant.ERRORS, errors);
                    }
                } catch (Exception ex) {
                    logger.error("An error occurred while updating user, username:{}, error:{}", user.getUsername(),
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

    @Transactional
    @Override
    public List<?> deleteUsers(List<String> ids) {
        ApiBatchResult result = new ApiBatchResult();

        for (int i = 0; i < ids.size(); i++) {
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            String UserId = ids.get(i);
            Integer nUserID = Integer.parseInt(UserId);
            String strUserName = "";
            builder.put(ApiErrorConstant.INDEX, i);
            builder.put(ApiErrorConstant.ID, UserId);
            try {
                tbAuthUser tbAuthUserObj = AuthUserRepo.findByid(nUserID);
                if (tbAuthUserObj != null) {
                    strUserName = tbAuthUserObj.getUsername();
                    AuthUserRepo.deleteAllByID(nUserID);
                    coreUserAccountRepo.deleteAllByID(nUserID);
                    authRep.deleteAllByUserId(nUserID);
                    eventVisithistoryRep.deleteAllByUserId(nUserID);
                    tokenService.removeTokenUseAccount(strUserName);
                    npoRep.deleteAllByUserId(nUserID);
                    pushMessageRep.deleteAllByUserId(nUserID);
                    replyRep.deleteAllByUserAccountId(nUserID);
                    userFocusedEventRep.deleteAllByUserId(nUserID);
                    userLicenseImageRep.deleteAllByUserId(nUserID);
                    userRegisteredEventRep.deleteAllByUserId(nUserID);
                    userSubscribeNpoRep.deleteAllByUserId(nUserID);
                    userThirdRep.deleteAllByUserId(nUserID);
                    pushNotificationsApnsDeviceRep.deleteAllByUserId(nUserID);
                    pushNotificationsGcmDeviceRep.deleteAllByUserId(nUserID);
                } else {
                    errors.add(new ApiErrorResponse(ERROR_BUNDLE.userNotFound));
                    builder.put(ApiErrorConstant.ERRORS, errors);
                }
            } catch (IllegalArgumentException ex) {
                logger.error("User is invalid, username:{}, error:{}", strUserName, ex.getMessage());
                errors.add(new ApiErrorResponse(ERROR_BUNDLE.invalidRequest, "User is invalid"));
                builder.put(ApiErrorConstant.ERRORS, errors);
            } catch (Exception ex) {
                logger.error("An error occurred while deleting user, username:{}, error:{}", strUserName, ex.getMessage());
                errors.add(new ApiErrorResponse(ERROR_BUNDLE.internalError));
                builder.put(ApiErrorConstant.ERRORS, errors);
            }

            result.add(builder.build());
        }

        return result.build();
    }

    @Override
    public vwUsers findUserByUserName(String username) {
        // TODO Auto-generated method stub
        return vwUserRepo.findByUsername(username);
    }

    @Override
    public vwUsers findUserByUserId(Integer userId) {
        Optional<vwUsers> usersOptional = vwUserRepo.findById(userId);
        if (!usersOptional.isPresent()) {
            throw new UserNotFoundException();
        }
        return usersOptional.get();
    }

    @Transactional
    @Override
    public void updateUserPhoto(Integer userid, String photo) {
        // TODO Auto-generated method stub
        tbCoreUserAccount tbCoreUserAccountObj = coreUserAccountRepo.findByUserId(userid);

        if (tbCoreUserAccountObj != null) {
            tbCoreUserAccountObj.setPhoto(photo);

            coreUserAccountRepo.save(tbCoreUserAccountObj);
        } else {
            throw new UserNotFoundException();
        }
    }

    @Transactional
    @Override
    public void updateUserIcon(Integer userid, String icon) {
        // TODO Auto-generated method stub
        tbCoreUserAccount tbCoreUserAccountObj = coreUserAccountRepo.findByUserId(userid);

        if (tbCoreUserAccountObj != null) {
            tbCoreUserAccountObj.setIcon(icon);

            coreUserAccountRepo.save(tbCoreUserAccountObj);
        } else {
            throw new UserNotFoundException();
        }

    }

    @Transactional
    @Override
    public void updateUserLicenseImage(Integer userid, String image, String thumbpath) {
        // TODO Auto-generated method stub
        tbCoreUserLicenseImage tbCoreUserLicenseImageObj = new tbCoreUserLicenseImage();
        tbCoreUserLicenseImageObj.setImage(image);
        tbCoreUserLicenseImageObj.setThumbPath(thumbpath);
        tbCoreUserLicenseImageObj.setUserId(userid);

        coreUserLicenseImageRepo.save(tbCoreUserLicenseImageObj);
    }

    @Transactional
    @Override
    public void removeUserPhoto(Integer userid) {
        // TODO Auto-generated method stub
        tbCoreUserAccount tbCoreUserAccountObj = coreUserAccountRepo.findByUserId(userid);

        if (tbCoreUserAccountObj != null && tbCoreUserAccountObj.getPhoto() != null &&
                !tbCoreUserAccountObj.getPhoto().isEmpty()) {
            resourceService.imageRemove(tbCoreUserAccountObj.getPhoto());

            tbCoreUserAccountObj.setPhoto("default_userprofile_image.png");

            coreUserAccountRepo.save(tbCoreUserAccountObj);
        }
    }

    @Transactional
    @Override
    public void removeUserIcon(Integer userid) {
        // TODO Auto-generated method stub
        tbCoreUserAccount tbCoreUserAccountObj = coreUserAccountRepo.findByUserId(userid);

        if (tbCoreUserAccountObj != null && tbCoreUserAccountObj.getIcon() != null &&
                !tbCoreUserAccountObj.getIcon().isEmpty()) {
            resourceService.imageRemove(tbCoreUserAccountObj.getIcon());

            tbCoreUserAccountObj.setIcon("default_userprofile_image.png");

            coreUserAccountRepo.save(tbCoreUserAccountObj);
        }

    }

    @Transactional
    @Override
    public void removeUserLicense(Integer licenseId) {
        // TODO Auto-generated method stub
        coreUserLicenseImageRepo.deleteById(licenseId);
    }

    @Transactional
    @Override
    public void updatePassword(Integer userid, String newpassword) {
        // TODO Auto-generated method stub
        Pbkdf2Sha256PasswordEncoder Pbkdf2Sha256PasswordEncoderObj = new Pbkdf2Sha256PasswordEncoder();

        // Update password
        String strPassword = Pbkdf2Sha256PasswordEncoderObj.encode(newpassword);

        tbAuthUser tbAuthUserObj = AuthUserRepo.findByid(userid);
        if (tbAuthUserObj == null)
            throw new UserNotFoundException();

        tbAuthUserObj.setPassword(strPassword);

        try {
            AuthUserRepo.save(tbAuthUserObj);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new InternalErrorException(String.format("Update password failed. [Exception: %s]", e.getMessage()));
        }
    }

    @Override
    public Boolean checkPasswordCorrect(Integer userid, String oldpassword) {
        // TODO Auto-generated method stub
        Pbkdf2Sha256PasswordEncoder Pbkdf2Sha256PasswordEncoderObj = new Pbkdf2Sha256PasswordEncoder();

        tbAuthUser tbAuthUserObj = AuthUserRepo.findByid(userid);
        if (tbAuthUserObj == null) {
            throw new UserNotFoundException();
        }

        return Pbkdf2Sha256PasswordEncoderObj.matches(oldpassword, tbAuthUserObj.getPassword());
    }

    @Override
    public JSONObject listUserRegisteredEvent(Specification<vwUserRegisteredEvent> QuerySpec, Pageable QueryPageable) {
        // TODO Auto-generated method stub
        JSONObject JOQueryResult = new JSONObject();
        List<UserRegisteredEventListRespVO> listUserRegEventList = new ArrayList<UserRegisteredEventListRespVO>();

        List<vwUserRegisteredEvent> listVWUsers = vwUserRegisteredEventRepo.findAll(QuerySpec, QueryPageable).getContent();
        for (vwUserRegisteredEvent vwUserRegisteredEventObj : listVWUsers) {
            UserRegisteredEventListRespVO UserRegisteredEventListRespObj = new UserRegisteredEventListRespVO();

            UserRegisteredEventListRespObj.setId(vwUserRegisteredEventObj.getEventID());
            UserRegisteredEventListRespObj.setNpoId(vwUserRegisteredEventObj.getNpoID());
            if (vwUserRegisteredEventObj.getImage1() != null) {
                UserRegisteredEventListRespObj.setImage1(strHTTPServerHost + vwUserRegisteredEventObj.getImage1());
            }
            UserRegisteredEventListRespObj.setUid(vwUserRegisteredEventObj.getUid());
            UserRegisteredEventListRespObj.setPubDate(vwUserRegisteredEventObj.getPubDate());
            UserRegisteredEventListRespObj.setHappenDate(vwUserRegisteredEventObj.getHappenDate());
            UserRegisteredEventListRespObj.setSubject(vwUserRegisteredEventObj.getSubject());
            UserRegisteredEventListRespObj.setRequiredVolunteerNum(vwUserRegisteredEventObj.getReqVolunteerNumer());
            UserRegisteredEventListRespObj.setCurrentVolunteerNum(vwUserRegisteredEventObj.getCurrVolunteeNumber());
            if (vwUserRegisteredEventObj.getThumbPath() != null) {
                UserRegisteredEventListRespObj.setThumbPath(strHTTPServerHost + vwUserRegisteredEventObj.getThumbPath());
            }
            UserRegisteredEventListRespObj.setIsRegistered((vwUserRegisteredEventObj.getRegister()) ? true : false);
            UserRegisteredEventListRespObj.setIsFocus((vwUserRegisteredEventObj.getFocus()) ? true : false);
            UserRegisteredEventListRespObj.setVolunteerEvent(vwUserRegisteredEventObj.getVolunteerEvent());

            listUserRegEventList.add(UserRegisteredEventListRespObj);
        }

        JOQueryResult.put("Count", vwUserRegisteredEventRepo.count(QuerySpec));
        JOQueryResult.put("Results", listUserRegEventList);

        return JOQueryResult;
    }

    @Override
    public List<?> getUsersMenu() {
        List<vwUsersMenu> users = vwUserMenuRepo.findAllByIsActiveOrderByUsername(1);
        return users.stream().map(u -> {
            com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
            jsonObject.put("id", u.getId());
            jsonObject.put("username", u.getUsername());
            return jsonObject;
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public com.alibaba.fastjson.JSONObject validFubonUser(FubonUserValidVO user) {
        com.alibaba.fastjson.JSONObject result = new com.alibaba.fastjson.JSONObject();
        ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
        List<Object> errors = new ArrayList<Object>();
        result.put("username", user.getUsername());

        Boolean isMember = thirdPartyApiUtils.validFubonUser(user.getUsername(), md5Utils.getMd5(user.getPassword()));
        if (!isMember) {
            errors.add(new ApiErrorResponse(ErrorHandlingConstants.ERROR_BUNDLE.userNotFound));
            builder.put(ApiErrorConstant.ERRORS, errors);
        } else if (userThirdRep.countByUserIdAndEnterpriseSerialCodeAndEnterpriseSerialEmail(getUserId(), "fubon", user.getUsername()) > 0) {
            errors.add(new ApiErrorResponse(ERROR_BUNDLE.fubonAlreadyBind));
            builder.put(ApiErrorConstant.ERRORS, errors);
        } else if (userThirdRep.countByEnterpriseSerialCodeAndEnterpriseSerialEmail("fubon", user.getUsername()) > 0) {
            errors.add(new ApiErrorResponse(ERROR_BUNDLE.fubonAlreadyBindUser));
            builder.put(ApiErrorConstant.ERRORS, errors);
        }


        if (errors.size() == 0) {
            // bind account
            userThirdRep.save(new tbCoreUserThird(getUserId(), "fubon", "", user.getUsername()));
        } else {
            result.put("errors", errors);
        }

        return result;
    }

    @Transactional
    @Override
    public com.alibaba.fastjson.JSONObject validTwmUser(TwmEnterpriseUserValidVO user) {
        com.alibaba.fastjson.JSONObject result = new com.alibaba.fastjson.JSONObject();
        ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
        List<Object> errors = new ArrayList<Object>();
        result.put("enterpriseSerialNumber", user.getEnterpriseSerialNumber());

        Boolean isMember = (thirdTwmEnterpriseRep.countByEnterpriseSerialNumberAndEnterpriseSerialEmailAndEnterpriseSerialName(user.getEnterpriseSerialNumber(), user.getEnterpriseSerialEmail(), user.getEnterpriseSerialName()) == 0) ? false : true;
        if (!isMember) {
            errors.add(new ApiErrorResponse(ErrorHandlingConstants.ERROR_BUNDLE.userNotFound));
            builder.put(ApiErrorConstant.ERRORS, errors);
        } else if (userThirdRep.countByUserIdAndEnterpriseSerialCodeAndEnterpriseSerialEmail(getUserId(), "twmEnterprise", user.getEnterpriseSerialEmail()) > 0) {
            errors.add(new ApiErrorResponse(ERROR_BUNDLE.twmAlreadyBind));
            builder.put(ApiErrorConstant.ERRORS, errors);
        } else if (userThirdRep.countByEnterpriseSerialCodeAndEnterpriseSerialEmail("twmEnterprise", user.getEnterpriseSerialEmail()) > 0) {
            errors.add(new ApiErrorResponse(ERROR_BUNDLE.twmAlreadyBindUser));
            builder.put(ApiErrorConstant.ERRORS, errors);
        }


        if (errors.size() == 0) {
            // bind account
            userThirdRep.save(new tbCoreUserThird(getUserId(), "twmEnterprise", user.getEnterpriseSerialNumber(), user.getEnterpriseSerialEmail()));
            // update account
            tbThirdTwmEnterpriseInfo tbThirdTwmEnterpriseInfo = new tbThirdTwmEnterpriseInfo();
            tbThirdTwmEnterpriseInfo.setEnterpriseSerialNumber(user.getEnterpriseSerialNumber());
            tbThirdTwmEnterpriseInfo.setEnterpriseSerialId(user.getEnterpriseSerialId());
            tbThirdTwmEnterpriseInfo.setEnterpriseSerialPhone(user.getEnterpriseSerialPhone());
            tbThirdTwmEnterpriseInfo.setEnterpriseSerialDepartment(user.getEnterpriseSerialDepartment());
            tbThirdTwmEnterpriseInfo.setEnterpriseSerialType(user.getEnterpriseSerialType());
            tbThirdTwmEnterpriseInfo.setEnterpriseSerialGroup(user.getEnterpriseSerialGroup());

            thirdTwmEnterpriseInfoRep.save(tbThirdTwmEnterpriseInfo);

        } else {
            result.put("errors", errors);
        }

        return result;
    }

    @Override
    public long countLicenseImages(Specification<tbCoreUserLicenseImage> spec) {
        return coreUserLicenseImageRep.count(spec);
    }

    @Override
    public List<UserLicenseImagesVO> findLicenseImages(Specification<tbCoreUserLicenseImage> spec, Pageable pageable) {
        return coreUserLicenseImageRep.findAll(spec, pageable).stream().map(this::transferLicenseImages).collect(Collectors.toList());
    }

    private UserLicenseImagesVO transferLicenseImages(tbCoreUserLicenseImage licenseImage) {
        UserLicenseImagesVO instance = new UserLicenseImagesVO();
        instance.setLicenseId(licenseImage.getId());
        instance.setUserId(licenseImage.getUserId());
        if (licenseImage.getImage() != null) {
            instance.setImage(strHTTPServerHost + licenseImage.getImage());
        }
        if (licenseImage.getThumbPath() != null) {
            instance.setThumbPath(strHTTPServerHost + licenseImage.getThumbPath());
        }

        return instance;
    }

    @Override
    public JSONObject listUserFocusedEvent(Specification<vwUserFocusedEvent> querySpec, Pageable queryPageable) {
        JSONObject JOQueryResult = new JSONObject();
        List<UserFocusedEventListRespVO> listUserRegEventList = new ArrayList<UserFocusedEventListRespVO>();

        List<vwUserFocusedEvent> listVWUsers = vwUserFocusedEventRep.findAll(querySpec, queryPageable).getContent();
        for (vwUserFocusedEvent vwUserRegisteredEventObj : listVWUsers) {
            UserFocusedEventListRespVO UserRegisteredEventListRespObj = new UserFocusedEventListRespVO();

            UserRegisteredEventListRespObj.setId(vwUserRegisteredEventObj.getEventID());
            UserRegisteredEventListRespObj.setNpoId(vwUserRegisteredEventObj.getNpoID());
            if (vwUserRegisteredEventObj.getImage1() != null) {
                UserRegisteredEventListRespObj.setImage1(strHTTPServerHost + vwUserRegisteredEventObj.getImage1());
            }
            UserRegisteredEventListRespObj.setUid(vwUserRegisteredEventObj.getUid());
            UserRegisteredEventListRespObj.setPubDate(vwUserRegisteredEventObj.getPubDate());
            UserRegisteredEventListRespObj.setHappenDate(vwUserRegisteredEventObj.getHappenDate());
            UserRegisteredEventListRespObj.setSubject(vwUserRegisteredEventObj.getSubject());
            UserRegisteredEventListRespObj.setRequiredVolunteerNum(vwUserRegisteredEventObj.getReqVolunteerNumer());
            UserRegisteredEventListRespObj.setCurrentVolunteerNum(vwUserRegisteredEventObj.getCurrVolunteeNumber());
            if (vwUserRegisteredEventObj.getThumbPath() != null) {
                UserRegisteredEventListRespObj.setThumbPath(strHTTPServerHost + vwUserRegisteredEventObj.getThumbPath());
            }
            UserRegisteredEventListRespObj.setRegistered(vwUserRegisteredEventObj.getRegister());
            UserRegisteredEventListRespObj.setFocus(vwUserRegisteredEventObj.getFocus());
            UserRegisteredEventListRespObj.setEnterprise(vwUserRegisteredEventObj.getEnterprise());
            UserRegisteredEventListRespObj.setFocusedDate(vwUserRegisteredEventObj.getFocusedDate());
            UserRegisteredEventListRespObj.setVolunteerEvent(vwUserRegisteredEventObj.getVolunteerEvent());
            UserRegisteredEventListRespObj.setCloseDate(vwUserRegisteredEventObj.getClosedDate());

            listUserRegEventList.add(UserRegisteredEventListRespObj);
        }

        JOQueryResult.put("Count", vwUserFocusedEventRep.count(querySpec));
        JOQueryResult.put("Results", listUserRegEventList);

        return JOQueryResult;
    }

    @Override
    public JSONObject listUserVisitEvent(Specification<vwEventVisitHistory> spec, Pageable pageable) {
        JSONObject JOQueryResult = new JSONObject();
        List<UserVisitedEventListRespVO> listUserRegEventList = new ArrayList<UserVisitedEventListRespVO>();

        List<vwEventVisitHistory> listVWUsers = vwEventVisitHistoryRep.findAll(spec, pageable).getContent();
        for (vwEventVisitHistory vwEventVisitHistory : listVWUsers) {
            UserVisitedEventListRespVO userVisitedEventListRespVO = new UserVisitedEventListRespVO();

            userVisitedEventListRespVO.setId(vwEventVisitHistory.getEventID());
            userVisitedEventListRespVO.setNpoId(vwEventVisitHistory.getNpoID());
            if (vwEventVisitHistory.getImage1() != null) {
                userVisitedEventListRespVO.setImage1(strHTTPServerHost + vwEventVisitHistory.getImage1());
            }
            userVisitedEventListRespVO.setUid(vwEventVisitHistory.getUid());
            userVisitedEventListRespVO.setCloseDate(vwEventVisitHistory.getCloseDate());
            userVisitedEventListRespVO.setHappenDate(vwEventVisitHistory.getHappenDate());
            userVisitedEventListRespVO.setSubject(vwEventVisitHistory.getSubject());
            userVisitedEventListRespVO.setRequiredVolunteerNum(vwEventVisitHistory.getReqVolunteerNumer());
            userVisitedEventListRespVO.setCurrentVolunteerNum(vwEventVisitHistory.getCurrVolunteeNumber());
            if (vwEventVisitHistory.getThumbPath() != null) {
                userVisitedEventListRespVO.setThumbPath(strHTTPServerHost + vwEventVisitHistory.getThumbPath());
            }
            userVisitedEventListRespVO.setRegistered((vwEventVisitHistory.getRegister()) ? true : false);
            userVisitedEventListRespVO.setFocus((vwEventVisitHistory.getFocus()) ? true : false);
            userVisitedEventListRespVO.setVisitTime(vwEventVisitHistory.getVisitTime());
            userVisitedEventListRespVO.setEnterprise(vwEventVisitHistory.getEnterprise());
            userVisitedEventListRespVO.setVolunteerEvent(vwEventVisitHistory.getVolunteerEvent());

            listUserRegEventList.add(userVisitedEventListRespVO);
        }

        JOQueryResult.put("Count", vwEventVisitHistoryRep.count(spec));
        JOQueryResult.put("Results", listUserRegEventList);

        return JOQueryResult;
    }

    @Override
    public JSONObject listUserSubscribeNpos(Specification<vwUserSubscribedNpos> spec, Pageable pageable) {
        JSONObject JOQueryResult = new JSONObject();
        List<UserSubscribedNpoVO> listUserSubscribeNpoList = new ArrayList<UserSubscribedNpoVO>();

        List<vwUserSubscribedNpos> listTbNpos = vwUserSubscribedNposRep.findAll(spec, pageable).getContent();
        for (vwUserSubscribedNpos vwUserSubscribedNpos : listTbNpos) {
            UserSubscribedNpoVO userSubscribedNpoVO = new UserSubscribedNpoVO();
            userSubscribedNpoVO.setUserId(vwUserSubscribedNpos.getUserId());
            userSubscribedNpoVO.setNpoId(vwUserSubscribedNpos.getNpoId());
            userSubscribedNpoVO.setNpoName(vwUserSubscribedNpos.getNpoName());
            userSubscribedNpoVO.setNpoUid(vwUserSubscribedNpos.getNpoUid());
            if (vwUserSubscribedNpos.getNpoIcon() != null) {
                userSubscribedNpoVO.setNpoIcon(strHTTPServerHost + vwUserSubscribedNpos.getNpoIcon());
            }
            userSubscribedNpoVO.setRatingUserNum(vwUserSubscribedNpos.getRatingUserNum());
            userSubscribedNpoVO.setTotalRatingScore(vwUserSubscribedNpos.getRatingUserNum());
            userSubscribedNpoVO.setSubscribedUserNum(vwUserSubscribedNpos.getSubscribedUserNum());
            userSubscribedNpoVO.setJoinedUserNum(vwUserSubscribedNpos.getJoinedUserNum());
            userSubscribedNpoVO.setEventNum(vwUserSubscribedNpos.getEventNum());
            userSubscribedNpoVO.setFocusedDate(vwUserSubscribedNpos.getFocusedDate());

            listUserSubscribeNpoList.add(userSubscribedNpoVO);
        }

        JOQueryResult.put("Count", vwUserSubscribedNposRep.count(spec));
        JOQueryResult.put("Results", listUserSubscribeNpoList);

        return JOQueryResult;
    }

    @Transactional
    @Override
    public com.alibaba.fastjson.JSONObject unbindFubon() {
        com.alibaba.fastjson.JSONObject result = new com.alibaba.fastjson.JSONObject();
        if (userThirdRep.countByUserIdAndEnterpriseSerialCode(getUserId(), "fubon") == 0) {
            result.put("error", new ApiErrorResponse(ErrorHandlingConstants.ERROR_BUNDLE.fubonNotFound));
        } else {
            userThirdRep.deleteByUserIdAndEnterpriseSerialCode(getUserId(), "fubon");
        }

        return result;
    }

    @Transactional
    @Override
    public com.alibaba.fastjson.JSONObject unbindTwmEnterprise() {
        com.alibaba.fastjson.JSONObject result = new com.alibaba.fastjson.JSONObject();
        if (userThirdRep.countByUserIdAndEnterpriseSerialCode(getUserId(), "twmEnterprise") == 0) {
            result.put("error", new ApiErrorResponse(ERROR_BUNDLE.twmNotFound));
        } else {
            tbCoreUserThird tbCoreUserThird = userThirdRep.findByUserIdAndEnterpriseSerialCode(getUserId(), "twmEnterprise");
            thirdTwmEnterpriseInfoRep.deleteByEnterpriseSerialNumber(tbCoreUserThird.getEnterpriseSerialNumber());
            userThirdRep.deleteByUserIdAndEnterpriseSerialCode(getUserId(), "twmEnterprise");
        }

        return result;
    }

    @Override
    public com.alibaba.fastjson.JSONObject getVolunteerTime(String startTime, String endTime) {
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
        List<vwUserRegisteredEvent> vwUserRegisteredEventList = vwUserRegisteredEventRep.findAllByUserId(getUserId());
        Integer eventEnterpriseHour = 0;
        Integer eventGeneralHour = 0;
        if (startTime != null) {
            vwUserRegisteredEventList = vwUserRegisteredEventList.stream().filter(event -> {
                Integer eventYear = getYear(event.getCloseDate());
                return Integer.valueOf(startTime) <= eventYear;
            }).collect(Collectors.toList());
        }
        if (endTime != null) {
            vwUserRegisteredEventList = vwUserRegisteredEventList.stream().filter(event -> {
                Integer eventYear = getYear(event.getCloseDate());
                return Integer.valueOf(endTime) >= eventYear;
            }).collect(Collectors.toList());
        }
        for (int i = 0; i < vwUserRegisteredEventList.size(); i++) {
            Integer eventHour = vwUserRegisteredEventList.get(i).getEventHour();
            if (vwUserRegisteredEventList.get(i).getEnterprise()) {
                eventEnterpriseHour += eventHour;
            } else {
                eventGeneralHour += eventHour;
            }
        }

        jsonObject.put("eventEnterpriseHour", eventEnterpriseHour);
        jsonObject.put("eventGeneralHour", eventGeneralHour);
        jsonObject.put("totalHour", eventEnterpriseHour + eventGeneralHour);


        return jsonObject;
    }


    private Integer getYear(Timestamp timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        return cal.get(Calendar.YEAR);
    }
}