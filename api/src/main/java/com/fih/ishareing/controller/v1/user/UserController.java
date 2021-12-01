package com.fih.ishareing.controller.v1.user;

import com.fih.ishareing.common.ApiConstants;
import com.fih.ishareing.common.ApiConstants.PAGE_SIZE_LIMIT;
import com.fih.ishareing.constant.Constant;
import com.fih.ishareing.controller.base.BaseController;
import com.fih.ishareing.controller.base.factory.ColumnFactory;
import com.fih.ishareing.controller.base.vo.JsonBaseVO;
import com.fih.ishareing.errorHandling.exceptions.ParameterException;
import com.fih.ishareing.errorHandling.exceptions.PasswordNotMatchException;
import com.fih.ishareing.errorHandling.exceptions.TokenInValidException;
import com.fih.ishareing.errorHandling.exceptions.UserNotFoundException;
import com.fih.ishareing.repository.criteria.QueryConditionUtils;
import com.fih.ishareing.repository.criteria.SearchCriteria;
import com.fih.ishareing.repository.entity.*;
import com.fih.ishareing.service.thirdLogin.model.FubonUserValidVO;
import com.fih.ishareing.service.token.TokenService;
import com.fih.ishareing.service.user.UserService;
import com.fih.ishareing.service.user.model.*;
import com.fih.ishareing.validators.ValidatorUtils;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.modelmapper.ModelMapper;
import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserController extends BaseController {

    private Map<String, String> userSortable = new HashMap<String, String>();
    private Map<String, String> userSearchable = new HashMap<String, String>();
    private Map<String, String> userLicenseSortable = new HashMap<String, String>();
    private Map<String, String> userLicenseSearchable = new HashMap<String, String>();
    private Map<String, String> userSubscribedNpoSortable = new HashMap<String, String>();
    private Map<String, String> userSubscribedNpoSearchable = new HashMap<String, String>();
    private Map<String, String> userVisitSortable = new HashMap<String, String>();
    private Map<String, String> userVisitSearchable = new HashMap<String, String>();

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    private void init() {
        ColumnFactory factory = new ColumnFactory()
                .build("id", "eventID", true, true)
                .build("userid", "userId", true, true)
                .build("npoId", "npoID", true, true)
                .build("image1", "image1", true, true)
                .build("uid", "uid", true, true)
                .build("happenDate", "happenDate", true, true)
                .build("pubDate", "pubDate", true, true)
                .build("closeDate", "closeDate", true, true)
                .build("subject", "subject", true, true)
                .build("requiredVolunteerNum", "reqVolunteerNumer", true, true)
                .build("currentVolunteerNum", "currVolunteeNumber", true, true)
                .build("thumbPath", "thumbPath", true, true)
                .build("isRegistered", "register", true, true)
                .build("isFocus", "focus", true, true)
                .build("isJoined", "joined", false, true)
                .build("isLeaved", "leaved", false, true)
                .build("isVolunteerEvent", "volunteerEvent", false, true)
                .build("isEnterprise", "enterprise", false, true)
                .build("focusedDate", "focusedDate", true, true);

        ColumnFactory visitFactory = new ColumnFactory()
                .build("id", "eventID", true, true)
                .build("userid", "userId", true, true)
                .build("npoId", "npoID", true, true)
                .build("image1", "image1", true, true)
                .build("uid", "uid", true, true)
                .build("happenDate", "happenDate", true, true)
                .build("pubDate", "pubDate", true, true)
                .build("closeDate", "closeDate", true, true)
                .build("subject", "subject", true, true)
                .build("requiredVolunteerNum", "reqVolunteerNumer", true, true)
                .build("currentVolunteerNum", "currVolunteeNumber", true, true)
                .build("thumbPath", "thumbPath", true, true)
                .build("isRegistered", "register", true, true)
                .build("isFocus", "focus", true, true)
                .build("isJoined", "joined", false, true)
                .build("isVolunteerEvent", "volunteerEvent", false, true)
                .build("isEnterprise", "enterprise", false, true)
                .build("visitTime", "visitTime", true, true);

        this.userSortable = factory.getSortColumnMap();
        this.userSearchable = factory.getSearchColumnMap();
        this.userLicenseSortable = QueryConditionUtils.getSortColumn(UserLicenseImagesVO.class);
        this.userLicenseSearchable = QueryConditionUtils.getSearchColumn(UserLicenseImagesVO.class);
        this.userSubscribedNpoSortable = QueryConditionUtils.getSortColumn(UserSubscribedNpoVO.class);
        this.userSubscribedNpoSearchable = QueryConditionUtils.getSearchColumn(UserSubscribedNpoVO.class);
        this.userVisitSortable = visitFactory.getSortColumnMap();
        this.userVisitSearchable = visitFactory.getSearchColumnMap();
    }

    @GetMapping(value = "/profile")
    public UserProfileRespVO profile(HttpServletRequest request) {

        // Load user detail info
        vwUsers vwUsersObj = userService.findUserByUserId(getUserId());

        // Prepare return result
        UserProfileRespVO UserProfileRespVOObj = new UserProfileRespVO();
        UserProfileRespVOObj.setId(getUserId());
        UserProfileRespVOObj.setUsername(vwUsersObj.getUsername());
        UserProfileRespVOObj.setUid(vwUsersObj.getUid());
        UserProfileRespVOObj.setAboutMe(vwUsersObj.getAboutMe());
        UserProfileRespVOObj.setSkillsDescription(vwUsersObj.getSkillsDescription());
        UserProfileRespVOObj.setInterest(vwUsersObj.getInterest());
        UserProfileRespVOObj.setStaff(vwUsersObj.getStaff());
        UserProfileRespVOObj.setNpo(vwUsersObj.getNpo());
        UserProfileRespVOObj.setNpoId(vwUsersObj.getNpoId());
        UserProfileRespVOObj.setRanking(vwUsersObj.getRanking());
        UserProfileRespVOObj.setEventNum(vwUsersObj.getEventNum());
        UserProfileRespVOObj.setEventEnterpriseHour(vwUsersObj.getEventEnterpriseHour());
        UserProfileRespVOObj.setEventGeneralHour(vwUsersObj.getEventGeneralHour());
        UserProfileRespVOObj.setScore(vwUsersObj.getScore());
        if (vwUsersObj.getIcon() != null) {
            UserProfileRespVOObj.setIcon(strHTTPServerHost + vwUsersObj.getIcon());
        }
        UserProfileRespVOObj.setFubon(vwUsersObj.getFubon());
        UserProfileRespVOObj.setTwm(vwUsersObj.getTwm());
        UserProfileRespVOObj.setEmail(vwUsersObj.getEmail());
        UserProfileRespVOObj.setPhone(vwUsersObj.getPhone());
        UserProfileRespVOObj.setPublic(vwUsersObj.getPublic());
        UserProfileRespVOObj.setName(vwUsersObj.getName());

        return UserProfileRespVOObj;
    }

    @GetMapping(value = "/tokenCheck")
    public JSONObject tokenCheck(HttpServletRequest request) {
        // Load user detail info
        vwUsers vwUsersObj = userService.findUserByUserId(getUserId());

        // Prepare return result
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", vwUsersObj.getId());
        jsonObject.put("username", vwUsersObj.getUsername());
        jsonObject.put("uid", vwUsersObj.getUid());
        return jsonObject;
    }

    @PatchMapping(value = "/password")
    public void password(@RequestBody UserPasswordReqVO model, HttpServletRequest request) {
        // Validate parameters
        UserPasswordReqVO newVO = modelMapper.map(model, UserPasswordReqVO.class);
        Errors errors = ValidatorUtils.validate(newVO);
        List<FieldError> listFieldError = (errors != null && errors.getFieldErrorCount() > 0) ? errors.getFieldErrors() : null;

        if (listFieldError != null && listFieldError.size() > 0)
            throw new ParameterException(String.format("Parameter: %s.", listFieldError.get(0).getField()));

        String strXAccessToken = request.getHeader("X-Access-Token");

        if (strXAccessToken == null || strXAccessToken.isEmpty())
            throw new TokenInValidException();

        // Prepare user info
        Authentication auth = StringUtils.isNotBlank(strXAccessToken) ? tokenService.readAuthentication(strXAccessToken)
                : null;

        if (auth == null)
            throw new UserNotFoundException();

        AuthUser AuthUserObj = (AuthUser) auth.getPrincipal();

        // Validate oldpassword is correct
        Boolean bPasswordCorrect = userService.checkPasswordCorrect(AuthUserObj.getUserId(), model.getOldPassword());
        if (!bPasswordCorrect)
            throw new PasswordNotMatchException();

        // Update new password
        userService.updatePassword(AuthUserObj.getUserId(), model.getNewPassword());
    }

    @DeleteMapping(value = "/token")
    public void token(HttpServletRequest request) {

        // Validate parameters
        String strXAccessToken = request.getHeader("X-Access-Token");

        if (strXAccessToken == null || strXAccessToken.isEmpty())
            throw new TokenInValidException();

        // Prepare user info
        Authentication auth = StringUtils.isNotBlank(strXAccessToken) ? tokenService.readAuthentication(strXAccessToken)
                : null;

        if (auth == null)
            throw new UserNotFoundException();

        // Remove token
        tokenService.removeToken(auth.getName(), strXAccessToken);

        // Prepare return result
        UserTokenRespVO UserTokenRespVOObj = new UserTokenRespVO();
    }

    @GetMapping(value = "/registeredEvent")
    public JsonBaseVO<UserRegisteredEventListRespVO> listUserRegisterEvent(@RequestParam(value = "search", required = false) String search,
                                                                           @RequestParam(value = "inlinecount", required = false, defaultValue = "true") boolean hasCount,
                                                                           @RequestParam(value = "size", required = false, defaultValue = "65535") Integer size,
                                                                           @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                                           @RequestParam(value = "sort", required = false, defaultValue = "") String sort,
                                                                           HttpServletRequest request) {

        JsonBaseVO<UserRegisteredEventListRespVO> result = new JsonBaseVO<>();
        ImmutableList.Builder<SearchCriteria> builder = ImmutableList.builder();
        builder.add(new SearchCriteria("userId", "eq", getUserId()));

        if (search != null) {
            search = "isJoined eq 1 and isLeaved eq 1 and isVolunteerEvent eq 1 and " + search;
        } else {
            search = "isJoined eq 1 and isLeaved eq 1 and isVolunteerEvent eq 1 ";
        }

        Specification<vwUserRegisteredEvent> spec = specUtil.getSpecification(this.userSearchable, search, vwUserRegisteredEvent.class, builder.build());
        JSONObject JOQueryResult = userService.listUserRegisteredEvent(spec, wrapPageable(userSortable, size, page, getSort(Encode.forUriComponent(sort)), PAGE_SIZE_LIMIT.JSON));
        result.setCount(Long.parseLong(JOQueryResult.get("Count").toString()));
        result.setResults((List<UserRegisteredEventListRespVO>) JOQueryResult.get("Results"));

        return result;
    }

    @PostMapping(value = "/fubon")
    public com.alibaba.fastjson.JSONObject validFubonUser(@Valid @RequestBody FubonUserValidVO user) {
        return userService.validFubonUser(user);
    }

    @PostMapping(value = "/twm")
    public com.alibaba.fastjson.JSONObject validTwmUser(@Valid @RequestBody TwmEnterpriseUserValidVO user) {
        return userService.validTwmUser(user);
    }

    @GetMapping(value = "/licenseImage")
    public JsonBaseVO<UserLicenseImagesVO> findLicenseImages(@RequestParam(value = "search", required = false) String search,
                                                             @RequestParam(value = "inlinecount", required = false, defaultValue = "true") boolean hasCount,
                                                             @RequestParam(value = "size", required = false, defaultValue = "65535") Integer size,
                                                             @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                             @RequestParam(value = "sort", required = false, defaultValue = "") String sort) {

        JsonBaseVO<UserLicenseImagesVO> result = new JsonBaseVO<>();
        ImmutableList.Builder<SearchCriteria> builder = ImmutableList.builder();
        builder.add(new SearchCriteria("userId", "eq", getUserId()));

        Specification<tbCoreUserLicenseImage> spec = specUtil.getSpecification(this.userLicenseSearchable, search, tbCoreUserLicenseImage.class, builder.build());
        List<UserLicenseImagesVO> licenseImages = userService.findLicenseImages(spec, wrapPageable(userLicenseSortable, size, page, getSort(Encode.forUriComponent(sort)), ApiConstants.PAGE_SIZE_LIMIT.JSON));
        result.setResults(licenseImages);
        if (hasCount) {
            result.setCount(userService.countLicenseImages(spec));
        }

        return result;
    }


    @GetMapping(value = "/focusedEvent")
    public JsonBaseVO<UserFocusedEventListRespVO> listUserFocusedEvent(@RequestParam(value = "search", required = false) String search,
                                                                         @RequestParam(value = "inlinecount", required = false, defaultValue = "true") boolean hasCount,
                                                                       @RequestParam(value = "size", required = false, defaultValue = "65535") Integer size,
                                                                       @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                                       @RequestParam(value = "sort", required = false, defaultValue = "") String sort,
                                                                       HttpServletRequest request) {

        JsonBaseVO<UserFocusedEventListRespVO> result = new JsonBaseVO<>();
        ImmutableList.Builder<SearchCriteria> builder = ImmutableList.builder();
        builder.add(new SearchCriteria("userId", "eq", getUserId()));


        if (search != null) {
            search = "isVolunteerEvent eq 1 and " + search;
        } else {
            search = "isVolunteerEvent eq 1 ";
        }

        Specification<vwUserFocusedEvent> spec = specUtil.getSpecification(this.userSearchable, search, vwUserFocusedEvent.class, builder.build());
        JSONObject JOQueryResult = userService.listUserFocusedEvent(spec, wrapPageable(userSortable, size, page, getSort(Encode.forUriComponent(sort)), PAGE_SIZE_LIMIT.JSON));
        result.setCount(Long.parseLong(JOQueryResult.get("Count").toString()));
        result.setResults((List<UserFocusedEventListRespVO>) JOQueryResult.get("Results"));

        return result;
    }


    @GetMapping(value = "/suppliedEvent")
    public JsonBaseVO<UserRegisteredEventListRespVO> listUserSuppliedEvent(@RequestParam(value = "search", required = false) String search,
                                                                           @RequestParam(value = "inlinecount", required = false, defaultValue = "true") boolean hasCount,
                                                                           @RequestParam(value = "size", required = false, defaultValue = "65535") Integer size,
                                                                           @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                                           @RequestParam(value = "sort", required = false, defaultValue = "") String sort,
                                                                           HttpServletRequest request) {

 
        JsonBaseVO<UserRegisteredEventListRespVO> result = new JsonBaseVO<>();
        ImmutableList.Builder<SearchCriteria> builder = ImmutableList.builder();
        builder.add(new SearchCriteria("userId", "eq", getUserId()));


        if (search != null) {
            search = "isJoined eq 1 and isVolunteerEvent eq 0 and " + search;
        } else {
            search = "isJoined eq 1 and isVolunteerEvent eq 0 ";
        }

        Specification<vwUserRegisteredEvent> spec = specUtil.getSpecification(this.userSearchable, search, vwUserRegisteredEvent.class, builder.build());
        JSONObject JOQueryResult = userService.listUserRegisteredEvent(spec, wrapPageable(userSortable, size, page, getSort(Encode.forUriComponent(sort)), PAGE_SIZE_LIMIT.JSON));
        result.setCount(Long.parseLong(JOQueryResult.get("Count").toString()));
        result.setResults((List<UserRegisteredEventListRespVO>) JOQueryResult.get("Results"));

        return result;
    }

    @GetMapping(value = "/suppliedFocus")
    public JsonBaseVO<UserFocusedEventListRespVO> listUserSuppliedFocusEvent(@RequestParam(value = "search", required = false) String search,
                                                                         @RequestParam(value = "inlinecount", required = false, defaultValue = "true") boolean hasCount,
                                                                             @RequestParam(value = "size", required = false, defaultValue = "65535") Integer size,
                                                                             @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                                             @RequestParam(value = "sort", required = false, defaultValue = "") String sort,
                                                                             HttpServletRequest request) {

        JsonBaseVO<UserFocusedEventListRespVO> result = new JsonBaseVO<>();
        ImmutableList.Builder<SearchCriteria> builder = ImmutableList.builder();
        builder.add(new SearchCriteria("userId", "eq", getUserId()));


        if (search != null) {
            search = "isVolunteerEvent eq 0 and " + search;
        } else {
            search = "isVolunteerEvent eq 0 ";
        }

        Specification<vwUserFocusedEvent> spec = specUtil.getSpecification(this.userSearchable, search, vwUserFocusedEvent.class, builder.build());
        JSONObject JOQueryResult = userService.listUserFocusedEvent(spec, wrapPageable(userSortable, size, page, getSort(Encode.forUriComponent(sort)), PAGE_SIZE_LIMIT.JSON));
        result.setCount(Long.parseLong(JOQueryResult.get("Count").toString()));
        result.setResults((List<UserFocusedEventListRespVO>) JOQueryResult.get("Results"));

        return result;
    }

    @GetMapping(value = "/ratingEvent")
    public JsonBaseVO<UserRegisteredEventListRespVO> listUserRatingEvent(@RequestParam(value = "search", required = false) String search,
                                                                           @RequestParam(value = "inlinecount", required = false, defaultValue = "true") boolean hasCount,
                                                                         @RequestParam(value = "size", required = false, defaultValue = "65535") Integer size,
                                                                         @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                                         @RequestParam(value = "sort", required = false, defaultValue = "") String sort,
                                                                         HttpServletRequest request) {

        JsonBaseVO<UserRegisteredEventListRespVO> result = new JsonBaseVO<>();
        ImmutableList.Builder<SearchCriteria> builder = ImmutableList.builder();
        builder.add(new SearchCriteria("userId", "eq", getUserId()));


        if (search != null) {
            search = "isJoined eq 1 and " + search;
        } else {
            search = "isJoined eq 1 ";
        }

        Specification<vwUserRegisteredEvent> spec = specUtil.getSpecification(this.userSearchable, search, vwUserRegisteredEvent.class, builder.build());
        JSONObject JOQueryResult = userService.listUserRegisteredEvent(spec, wrapPageable(userSortable, size, page, getSort(Encode.forUriComponent(sort)), PAGE_SIZE_LIMIT.JSON));
        result.setCount(Long.parseLong(JOQueryResult.get("Count").toString()));
        result.setResults((List<UserRegisteredEventListRespVO>) JOQueryResult.get("Results"));

        return result;
    }


    @GetMapping(value = "/visitEvent")
    public JsonBaseVO<UserVisitedEventListRespVO> listUserVisitEvent(@RequestParam(value = "search", required = false) String search,
                                                                         @RequestParam(value = "inlinecount", required = false, defaultValue = "true") boolean hasCount,
                                                                     @RequestParam(value = "size", required = false, defaultValue = "65535") Integer size,
                                                                     @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                                     @RequestParam(value = "sort", required = false, defaultValue = "") String sort,
                                                                     HttpServletRequest request) {

        JsonBaseVO<UserVisitedEventListRespVO> result = new JsonBaseVO<>();
        ImmutableList.Builder<SearchCriteria> builder = ImmutableList.builder();
        builder.add(new SearchCriteria("userId", "eq", getUserId()));


        Specification<vwEventVisitHistory> spec = specUtil.getSpecification(this.userVisitSearchable, search, vwEventVisitHistory.class, builder.build());
        JSONObject JOQueryResult = userService.listUserVisitEvent(spec, wrapPageable(userVisitSortable, size, page, getSort(Encode.forUriComponent(sort)), PAGE_SIZE_LIMIT.JSON));
        result.setCount(Long.parseLong(JOQueryResult.get("Count").toString()));
        result.setResults((List<UserVisitedEventListRespVO>) JOQueryResult.get("Results"));

        return result;
    }

    @GetMapping(value = "/subscribedNpo")
    public JsonBaseVO<UserSubscribedNpoVO> listUserSubscribeNpos(@RequestParam(value = "search", required = false) String search,
                                                                        @RequestParam(value = "inlinecount", required = false, defaultValue = "true") boolean hasCount,
                                                                 @RequestParam(value = "size", required = false, defaultValue = "65535") Integer size,
                                                                 @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                                 @RequestParam(value = "sort", required = false, defaultValue = "") String sort,
                                                                 HttpServletRequest request) {

        JsonBaseVO<UserSubscribedNpoVO> result = new JsonBaseVO<>();
        ImmutableList.Builder<SearchCriteria> builder = ImmutableList.builder();
        builder.add(new SearchCriteria("userId", "eq", getUserId()));
        builder.add(new SearchCriteria("isVerified", "eq", true));
        builder.add(new SearchCriteria("admViewed", "eq", true));


        Specification<vwUserSubscribedNpos> spec = specUtil.getSpecification(this.userSubscribedNpoSearchable, search, vwUserSubscribedNpos.class, builder.build());
        JSONObject JOQueryResult = userService.listUserSubscribeNpos(spec, wrapPageable(userSubscribedNpoSortable, size, page, getSort(Encode.forUriComponent(sort)), PAGE_SIZE_LIMIT.JSON));
        result.setCount(Long.parseLong(JOQueryResult.get("Count").toString()));
        result.setResults((List<UserSubscribedNpoVO>) JOQueryResult.get("Results"));

        return result;
    }

    @DeleteMapping("/fubon")
    public com.alibaba.fastjson.JSONObject unbindFubon() {
        return userService.unbindFubon();
    }

    @DeleteMapping("/twm")
    public com.alibaba.fastjson.JSONObject unbindTwmEnterprise() {
        return userService.unbindTwmEnterprise();
    }

    @GetMapping(value = "/volunteerTime")
    public com.alibaba.fastjson.JSONObject getUserVolunteerTime(@RequestParam(value = "startTime", required = false) String startTime,
                                                                                     @RequestParam(value = "endTime", required = false) String endTime) {
        if (startTime == null && endTime == null) {
            throw new ParameterException();
        }
        if (startTime != null && startTime.trim().equalsIgnoreCase("")) {
            throw new ParameterException();
        }
        if (endTime != null && endTime.trim().equalsIgnoreCase("")) {
            throw new ParameterException();
        }
        String year = "[0-9]{4}";
        if (startTime != null && !startTime.matches(year)) {
            throw new ParameterException();
        }
        if (endTime != null && !endTime.matches(year)) {
            throw new ParameterException();
        }
        if (startTime != null && endTime != null && Integer.valueOf(startTime) > Integer.valueOf(endTime)) {
            throw new ParameterException();
        }

        return userService.getVolunteerTime(startTime, endTime);
    }
}
