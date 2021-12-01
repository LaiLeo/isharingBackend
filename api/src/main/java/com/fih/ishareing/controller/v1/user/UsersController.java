package com.fih.ishareing.controller.v1.user;

import com.fih.ishareing.common.ApiConstants.PAGE_SIZE_LIMIT;
import com.fih.ishareing.constant.Constant;
import com.fih.ishareing.controller.base.BaseController;
import com.fih.ishareing.controller.base.factory.ColumnFactory;
import com.fih.ishareing.controller.base.vo.JsonBaseVO;
import com.fih.ishareing.errorHandling.exceptions.*;
import com.fih.ishareing.repository.criteria.QueryConditionUtils;
import com.fih.ishareing.repository.criteria.SearchCriteria;
import com.fih.ishareing.repository.entity.vwUsers;
import com.fih.ishareing.service.resource.ResourceService;
import com.fih.ishareing.service.resource.model.ScaleToConfig;
import com.fih.ishareing.service.user.UserService;
import com.fih.ishareing.service.user.model.*;
import com.google.common.collect.ImmutableList;
import org.json.simple.JSONObject;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(UsersController.class);

    private Map<String, String> userSortable = new HashMap<String, String>();
    private Map<String, String> userSearchable = new HashMap<String, String>();

    @Autowired
    private UserService userService;

    @Autowired
    private ResourceService resourceService;

    @PostConstruct
    private void init() {
        this.userSortable = QueryConditionUtils.getSortColumn(UserVO.class);
        this.userSearchable = QueryConditionUtils.getSearchColumn(UserVO.class);
    }

    @GetMapping(value = "")
    public JsonBaseVO<AuthUserListRespVO> listUsers(@RequestParam(value = "search", required = false) String search,
                                                    @RequestParam(value = "inlinecount", required = false, defaultValue = "true") boolean hasCount,
                                                    @RequestParam(value = "size", required = false, defaultValue = "65535") Integer size,
                                                    @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                    @RequestParam(value = "sort", required = false, defaultValue = "") String sort) {

        JsonBaseVO<AuthUserListRespVO> result = new JsonBaseVO<>();
        ImmutableList.Builder<SearchCriteria> builder = ImmutableList.builder();
        builder.add(new SearchCriteria("isActive", "eq", 1));

        ColumnFactory factory = new ColumnFactory()
                .build("id", "id", true, true)
                .build("isActive", "isActive", true, true)
                .build("username", "username", true, true)
                .build("firstName", "firstname", true, true)
                .build("lastName", "lastname", true, true)
                .build("email", "email", true, true)
                .build("isStaff", "isStaff", true, true)
                .build("lastLogin", "lastLogin", true, true)
                .build("dateJoined", "dateJoined", true, true)
                .build("uid", "uid", true, true)
                .build("photo", "photo", true, true)
                .build("name", "name", true, true)
                .build("phone", "phone", true, true)
                .build("securityId", "securityId", true, true)
                .build("skillsDescription", "skillsDescription", true, true)
                .build("birthday", "birthday", true, true)
                .build("guardianName", "guardianName", true, true)
                .build("guardianPhone", "guardianPhone", true, true)
                .build("Icon", "icon", true, true)
                .build("thumbPath", "thumbPath", true, true)
                .build("isPublic", "isPublic", true, true)
                .build("interest", "interest", true, true)
                .build("aboutMe", "aboutMe", true, true)
                .build("score", "score", true, true)
                .build("ranking", "ranking", true, true)
                .build("eventHour", "eventHour", true, true)
                .build("eventNum", "eventNum", true, true)
                .build("eventEnterpriseHour", "eventEnterpriseHour", true, true)
                .build("eventGeneralHour", "eventGeneralHour", true, true)
                .build("isFubon", "isFubon", false, true)
                .build("isTwm", "isTwm", false, true);

        this.userSortable = factory.getSortColumnMap();
        this.userSearchable = factory.getSearchColumnMap();

        Specification<vwUsers> spec = specUtil.getSpecification(this.userSearchable, search, vwUsers.class, builder.build());
        JSONObject JOQueryResult = userService.listUsers(spec, wrapPageable(userSortable, size, page, getSort(Encode.forUriComponent(sort)), PAGE_SIZE_LIMIT.JSON));
        result.setCount(Long.parseLong(JOQueryResult.get("Count").toString()));
        result.setResults((List<AuthUserListRespVO>) JOQueryResult.get("Results"));

        return result;
    }

    @PostMapping(value = "")
    public List<?> addUsers(@RequestBody List<AuthUserAddVO> users) {
        return userService.addUsers(users);
    }

    @PatchMapping()
    public List<?> updateUsers(@RequestBody List<AuthUserUpdateVO> users) {
        return userService.updateUsers(users);
    }

    @DeleteMapping("/{ids}/active")
    public List<?> deleteUsers(@PathVariable("ids") String ids) {
        return userService.deleteUsers(Constant.commaSpitter.splitToList(ids));
    }

    @GetMapping(value = "/menu")
    public List<?> getUsersMenu() {
        return userService.getUsersMenu();
    }


    @PutMapping(value = "/{id}/file/{type}")
    public ResourceUploadRespVO uploadResource(@PathVariable("id") Integer id, @PathVariable("type") String type, @ModelAttribute ResourceUploadReqVO model) {
        // Initial Variable
        String strImagePath = "";
        String strTargetPathName = "";

        if (!userService.exist(id)) {
            throw new UserNotFoundException();
        }
        if (model.getImage() == null || model.getImage().getContentType() == null) {
            throw new ParameterException();
        }
        if (model.getImage().getSize() > 1024 * 1024 * 10) {
            throw new ImageSizeInValidException();
        }
        if (!type.toLowerCase().equals("photo") && !type.toLowerCase().equals("icon") && !type.toLowerCase().equals("license")) {
            throw new FileImageTypeInValidException();
        }

        String[] fileList = getFileList(model.getImage().getContentType());
        String uuid = genUniqueId();
        String fileName = uuid + "." + fileList[1];
        String file420Name = uuid + "420" + "." + fileList[1];

        if (type.toLowerCase().equals("photo")) {
            // Scale image & saved
            try {
                strTargetPathName = String.format("user/%s", fileName);

                strImagePath = resourceService.imageScaleTo(model.getImage(), strTargetPathName, false, null);
            } catch (Exception Exp) {
                logger.error("uploadResource {} error {}, {}, {}", "photo", id, model.getImage().getOriginalFilename(), fileName);
            }

            // Update user photo
            userService.updateUserPhoto(id, strImagePath);
        } else if (type.toLowerCase().equals("icon")) {
            // Scale image & saved
            try {
                strTargetPathName = String.format("useraccount/%s", fileName);

                strImagePath = resourceService.imageScaleTo(model.getImage(), strTargetPathName, false, null);
            } catch (Exception Exp) {
                logger.error("uploadResource {} error {}, {}, {}", "icon", id, model.getImage().getOriginalFilename(), fileName);
            }

            // Update user icon
            userService.updateUserIcon(id, strImagePath);
        } else if (type.toLowerCase().equals("license")) {
            String strUserLicenseImage = "";
            String strUserLicenseThumbPath = "";

            // Scale image & saved
            try {
                strTargetPathName = String.format("user_license/%s", fileName);

                strUserLicenseImage = resourceService.imageScaleTo(model.getImage(), strTargetPathName, false, null);
                strImagePath = strUserLicenseImage;
            } catch (Exception Exp) {
                logger.error("uploadResource {} error {}, {}, {}", "license", id, model.getImage().getOriginalFilename(), fileName);
            }

            // Scale image & saved
            try {
                strTargetPathName = String.format("user_license/%s", file420Name);
                ScaleToConfig ScaleToConfigObj = new ScaleToConfig();
                ScaleToConfigObj.setHeight(420);
                ScaleToConfigObj.setWidth(420);

                strUserLicenseThumbPath = resourceService.imageScaleTo(model.getImage(), strTargetPathName, false, ScaleToConfigObj);
            } catch (Exception Exp) {
                logger.error("uploadResource {} error {}, {}, {}", "photo", id, model.getImage().getOriginalFilename(), file420Name);
            }

            // Update user license
            userService.updateUserLicenseImage(id, strUserLicenseImage, strUserLicenseThumbPath);
        }


        // Prepare return result
        String strURL = String.format("%s%s", strHTTPServerHost, strImagePath);
        ResourceUploadRespVO ResourceUploadRespVOObj = new ResourceUploadRespVO();
        ResourceUploadRespVOObj.setUrl(strURL);

        return ResourceUploadRespVOObj;
    }

    @DeleteMapping(value = "/{id}/file/{type}")
    public ResponseEntity<Void> removeResource(@PathVariable("id") Integer id, @PathVariable("type") String type) {
        if (!type.toLowerCase().equals("license") && !userService.exist(id)) {
            throw new UserNotFoundException();
        }
        if (type.toLowerCase().equals("license") && !userService.existLicenseImage(id)) {
            throw new UserLicenseImageNotFoundException();
        }
        if (!type.toLowerCase().equals("photo") && !type.toLowerCase().equals("icon") && !type.toLowerCase().equals("license")) {
            throw new FileImageTypeInValidException();
        }

        if (type.toLowerCase().equals("photo")) {
            // Remove user photo
            userService.removeUserPhoto(id);
        } else if (type.toLowerCase().equals("icon")) {
            // Remove user icon
            userService.removeUserIcon(id);
        } else if (type.toLowerCase().equals("license")) {
            // Remove user license
            userService.removeUserLicense(id);
        }
        return new ResponseEntity<Void>(org.springframework.http.HttpStatus.NO_CONTENT);
    }

}
