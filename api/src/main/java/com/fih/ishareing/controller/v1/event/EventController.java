package com.fih.ishareing.controller.v1.event;

import com.alibaba.fastjson.JSONObject;
import com.fih.ishareing.common.ApiConstants;
import com.fih.ishareing.constant.Constant;
import com.fih.ishareing.controller.base.BaseController;
import com.fih.ishareing.controller.base.error.ApiErrorResponse;
import com.fih.ishareing.controller.base.vaild.ValidList;
import com.fih.ishareing.controller.base.vo.JsonBaseVO;
import com.fih.ishareing.errorHandling.ErrorHandlingConstants;
import com.fih.ishareing.errorHandling.exceptions.FileImageTypeInValidException;
import com.fih.ishareing.errorHandling.exceptions.ImageSizeInValidException;
import com.fih.ishareing.errorHandling.exceptions.ParameterException;
import com.fih.ishareing.repository.criteria.QueryConditionUtils;
import com.fih.ishareing.repository.criteria.SearchCriteria;
import com.fih.ishareing.repository.entity.vwEvent;
import com.fih.ishareing.repository.entity.vwUserRegisteredEvent;
import com.fih.ishareing.service.event.EventService;
import com.fih.ishareing.service.event.model.*;
import com.fih.ishareing.service.resource.ResourceService;
import com.fih.ishareing.service.user.model.ResourceUploadReqVO;
import com.fih.ishareing.service.user.model.ResourceUploadRespVO;
import com.fih.ishareing.utils.ResponseEntityHelper;
import com.google.common.collect.ImmutableList;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/events")
public class EventController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventService eventService;
    @Autowired
    private ResourceService resourceService;

    private Map<String, String> eventSortable = new HashMap<String, String>();
    private Map<String, String> eventSearchable = new HashMap<String, String>();
    private Map<String, String> eventRegisteredSortable = new HashMap<String, String>();
    private Map<String, String> eventRegisteredSearchable = new HashMap<String, String>();

    @PostConstruct
    private void init() {
        this.eventSortable = QueryConditionUtils.getSortColumn(EventVO.class);
        this.eventSearchable = QueryConditionUtils.getSearchColumn(EventVO.class);
        this.eventRegisteredSortable = QueryConditionUtils.getSortColumn(EventRegisteredVO.class);
        this.eventRegisteredSearchable = QueryConditionUtils.getSearchColumn(EventRegisteredVO.class);
    }

    @GetMapping()
    public JsonBaseVO<EventVO> findEvents(@RequestParam(value = "search", required = false) String search,
                                            @RequestParam(value = "inlinecount", required = false, defaultValue = "true") boolean hasCount,
                                          @RequestParam(value = "size", required = false, defaultValue = "65535") Integer size,
                                          @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                          @RequestParam(value = "sort", required = false, defaultValue = "") String sort) {

        JsonBaseVO<EventVO> result = new JsonBaseVO<>();
        ImmutableList.Builder<SearchCriteria> builder = ImmutableList.builder();

        Specification<vwEvent> spec = specUtil.getSpecification(this.eventSearchable, search, vwEvent.class, builder.build());
        List<EventVO> events = eventService.findEvents(spec, wrapPageable(eventSortable, size, page, getSort(Encode.forUriComponent(sort)), ApiConstants.PAGE_SIZE_LIMIT.JSON));
        result.setResults(events);
        if (hasCount) {
            result.setCount(eventService.count(spec));
        }

        return result;
    }

    @GetMapping(path = "/volunteer")
    public JsonBaseVO<EventVO> findVolunteerEvents(@RequestParam(value = "search", required = false) String search,
                                          @RequestParam(value = "inlinecount", required = false, defaultValue = "true") boolean hasCount,
                                                   @RequestParam(value = "size", required = false, defaultValue = "65535") Integer size,
                                                   @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                   @RequestParam(value = "sort", required = false, defaultValue = "") String sort) {

        JsonBaseVO<EventVO> result = new JsonBaseVO<>();
        ImmutableList.Builder<SearchCriteria> builder = ImmutableList.builder();

        if (search != null) {
            search = "isVolunteerEvent eq true and isEnterprise eq false and " + search;
        } else {
            search = "isVolunteerEvent eq true and isEnterprise eq false ";
        }

        Specification<vwEvent> spec = specUtil.getSpecification(this.eventSearchable, search, vwEvent.class, builder.build());
        List<EventVO> events = eventService.findEvents(spec, wrapPageable(eventSortable, size, page, getSort(Encode.forUriComponent(sort)), ApiConstants.PAGE_SIZE_LIMIT.JSON));
        result.setResults(events);
        if (hasCount) {
            result.setCount(eventService.count(spec));
        }

        return result;
    }

    @GetMapping(path = "/enterprise")
    public JsonBaseVO<EventVO> findEnterpriseEvents(@RequestParam(value = "search", required = false) String search,
                                          @RequestParam(value = "inlinecount", required = false, defaultValue = "true") boolean hasCount,
                                                    @RequestParam(value = "size", required = false, defaultValue = "65535") Integer size,
                                                    @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                    @RequestParam(value = "sort", required = false, defaultValue = "") String sort) {

        JsonBaseVO<EventVO> result = new JsonBaseVO<>();
        ImmutableList.Builder<SearchCriteria> builder = ImmutableList.builder();

        if (search != null) {
            search = "isVolunteerEvent eq true and isEnterprise eq true and " + search;
        } else {
            search = "isVolunteerEvent eq true and isEnterprise eq true ";
        }

        Specification<vwEvent> spec = specUtil.getSpecification(this.eventSearchable, search, vwEvent.class, builder.build());
        List<EventVO> events = eventService.findEvents(spec, wrapPageable(eventSortable, size, page, getSort(Encode.forUriComponent(sort)), ApiConstants.PAGE_SIZE_LIMIT.JSON));
        result.setResults(events);
        if (hasCount) {
            result.setCount(eventService.count(spec));
        }

        return result;
    }

    @GetMapping(path = "/supply")
    public JsonBaseVO<EventVO> findSupplyEvents(@RequestParam(value = "search", required = false) String search,
                                          @RequestParam(value = "inlinecount", required = false, defaultValue = "true") boolean hasCount,
                                                @RequestParam(value = "size", required = false, defaultValue = "65535") Integer size,
                                                @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                @RequestParam(value = "sort", required = false, defaultValue = "") String sort) {

        JsonBaseVO<EventVO> result = new JsonBaseVO<>();
        ImmutableList.Builder<SearchCriteria> builder = ImmutableList.builder();
        if (search != null) {
            search = "isVolunteerEvent eq false and " + search;
        } else {
            search = "isVolunteerEvent eq false ";
        }

        Specification<vwEvent> spec = specUtil.getSpecification(eventSearchable, search, vwEvent.class, builder.build());
        List<EventVO> events = eventService.findEvents(spec, wrapPageable(eventSortable, size, page, getSort(Encode.forUriComponent(sort)), ApiConstants.PAGE_SIZE_LIMIT.JSON));
        result.setResults(events);
        if (hasCount) {
            result.setCount(eventService.count(spec));
        }

        return result;
    }


    @GetMapping(value = "/registered")
    public JsonBaseVO<EventRegisteredVO> findUserRegistered(@RequestParam(value = "search", required = false) String search,
                                                               @RequestParam(value = "inlinecount", required = false, defaultValue = "true") boolean hasCount,
                                                            @RequestParam(value = "size", required = false, defaultValue = "65535") Integer size,
                                                            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                            @RequestParam(value = "sort", required = false, defaultValue = "") String sort,
                                                            HttpServletRequest request) {

        JsonBaseVO<EventRegisteredVO> result = new JsonBaseVO<>();
        ImmutableList.Builder<SearchCriteria> builder = ImmutableList.builder();


        Specification<vwUserRegisteredEvent> spec = specUtil.getSpecification(eventRegisteredSearchable, search, vwUserRegisteredEvent.class, builder.build());
        List<EventRegisteredVO> eventRegisteredList = eventService.findUserRegistered(spec, wrapPageable(eventRegisteredSortable, size, page, getSort(Encode.forUriComponent(sort)), ApiConstants.PAGE_SIZE_LIMIT.JSON));
        result.setResults(eventRegisteredList);
        if (hasCount) {
            result.setCount(eventService.countRegistered(spec));
        }

        return result;
    }

    @PostMapping()
    public ResponseEntity<?> addEvents(@Valid @RequestBody ValidList<EventAddVO> events, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntityHelper.result(bindingResult.getAllErrors());
        }
        List<?> result = eventService.addEvents(events);
        return new ResponseEntity<List<?>>(result, HttpStatus.OK);
    }

    @PatchMapping()
    public ResponseEntity<?> updateEvents(@Valid @RequestBody ValidList<EventUpdateVO> events, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntityHelper.result(bindingResult.getAllErrors());
        }
        List<?> result = eventService.updateEvents(events);
        return new ResponseEntity<List<?>>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{ids}/active")
    public List<?> deleteEvents(@PathVariable("ids") String ids) {
        return eventService.deleteEvents(Constant.commaSpitter.splitToList(ids));
    }

    @PatchMapping("/registered")
    public ResponseEntity<?> updateUserRegistered(@Valid @RequestBody ValidList<EventRegisteredUpdateVO> registeredUpdateVOList, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntityHelper.result(bindingResult.getAllErrors());
        }
        List<?> result = eventService.updateUserRegistered(registeredUpdateVOList);
        return new ResponseEntity<List<?>>(result, HttpStatus.OK);
    }

    @PostMapping("/skillGroup")
    public ResponseEntity<?> addEventSkillGroups(@Valid @RequestBody ValidList<EventSkillGroupAddVO> eventSkillGroups, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntityHelper.result(bindingResult.getAllErrors());
        }
        List<?> result = eventService.addEventSkillGroups(eventSkillGroups);
        return new ResponseEntity<List<?>>(result, HttpStatus.OK);
    }

    @PatchMapping("/skillGroup")
    public ResponseEntity<?> updateEventSkillGroups(@Valid @RequestBody ValidList<EventSkillGroupUpdateVO> eventSkillGroups, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntityHelper.result(bindingResult.getAllErrors());
        }
        List<?> result = eventService.updateEventSkillGroups(eventSkillGroups);
        return new ResponseEntity<List<?>>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{ids}/skillGroup")
    public List<?> deleteEventSkillGroups(@PathVariable("ids") String ids) {
        return eventService.deleteEventSkillGroups(Constant.commaSpitter.splitToList(ids));
    }

    @PostMapping("/focus")
    public ResponseEntity<?> addEventFocus(@Valid @RequestBody ValidList<EventFocusVO> eventFocusVOS, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntityHelper.result(bindingResult.getAllErrors());
        }
        List<?> result = eventService.focusEvent(eventFocusVOS);
        return new ResponseEntity<List<?>>(result, HttpStatus.OK);
    }
    @DeleteMapping("/{ids}/unfocus")
    public List<?> deleteEventFocus(@PathVariable("ids") String ids) {
        return eventService.deleteFocusEvent(Constant.commaSpitter.splitToList(ids));
    }

    @PutMapping(value = "/{id}/file/{type}")
    public ResponseEntity<?> uploadResource(@PathVariable("id") Integer id, @PathVariable("type") String type, @ModelAttribute ResourceUploadReqVO model) {
        String strImagePath = "";
        String strTargetPathName = "";
        if (!type.toLowerCase().equals("image1") && !type.toLowerCase().equals("image2") && !type.toLowerCase().equals("image3") && !type.toLowerCase().equals("image4") && !type.toLowerCase().equals("image5")) {
            throw new FileImageTypeInValidException();
        }
        if (!eventService.exist(id)) {
            return new ResponseEntity<ApiErrorResponse>(new ApiErrorResponse(ErrorHandlingConstants.ERROR_BUNDLE.eventNotFound.name(), ErrorHandlingConstants.ERROR_BUNDLE.eventNotFound.getDesc()), HttpStatus.OK);
        }
        if (model.getImage() == null || model.getImage().getContentType() == null) {
            throw new ParameterException();
        }
        if (model.getImage().getSize() > 1024 * 1024 * 10) {
            throw new ImageSizeInValidException();
        }

        String[] fileList = getFileList(model.getImage().getContentType());
        String uuid = genUniqueId();
        String fileName = uuid + "." + fileList[1];

        // Scale image & saved
        try {
            strTargetPathName = String.format("origin/event_%s", fileName);

            strImagePath = resourceService.imageScaleTo(model.getImage(), strTargetPathName, false, null);
        } catch (Exception Exp) {
            logger.error("uploadResource {} error {}, {}, {}", "icon", id, model.getImage().getOriginalFilename(), fileName);
        }

        String typeString = "image1";
        if (type.toLowerCase().equals("image2")) {
            typeString = "image2";
        }
        if (type.toLowerCase().equals("image3")) {
            typeString = "image3";
        }
        if (type.toLowerCase().equals("image4")) {
            typeString = "image4";
        }
        if (type.toLowerCase().equals("image5")) {
            typeString = "image5";
        }
        eventService.updateEventImg(id, strImagePath, typeString);

        // Prepare return result
        String strURL = String.format("%s%s", strHTTPServerHost, strImagePath);
        ResourceUploadRespVO resourceUploadRespVOObj = new ResourceUploadRespVO();
        resourceUploadRespVOObj.setUrl(strURL);

        return new ResponseEntity<ResourceUploadRespVO>(resourceUploadRespVOObj, org.springframework.http.HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/file/{type}")
    public ResponseEntity<?> removeResource(@PathVariable("id") Integer id, @PathVariable("type") String type) {
        if (!type.toLowerCase().equals("image1") && !type.toLowerCase().equals("image2") && !type.toLowerCase().equals("image3") && !type.toLowerCase().equals("image4") && !type.toLowerCase().equals("image5")) {
            throw new FileImageTypeInValidException();
        }

        if (!eventService.exist(id)) {
            return new ResponseEntity<ApiErrorResponse>(new ApiErrorResponse(ErrorHandlingConstants.ERROR_BUNDLE.eventNotFound.name(), ErrorHandlingConstants.ERROR_BUNDLE.eventNotFound.getDesc()), HttpStatus.OK);
        }

        String typeString = "image1";
        if (type.toLowerCase().equals("image2")) {
            typeString = "image2";
        }
        if (type.toLowerCase().equals("image3")) {
            typeString = "image3";
        }
        if (type.toLowerCase().equals("image4")) {
            typeString = "image4";
        }
        if (type.toLowerCase().equals("image5")) {
            typeString = "image5";
        }
        eventService.removeEventImg(id, typeString);

        return new ResponseEntity<Void>(org.springframework.http.HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{id}/resultFile/{type}")
    public ResponseEntity<?> uploadResultFileResource(@PathVariable("id") Integer id, @PathVariable("type") String type, @ModelAttribute ResourceUploadReqVO model) {
        String strImagePath = "";
        String strTargetPathName = "";
        if (!type.toLowerCase().equals("image")) {
            throw new FileImageTypeInValidException();
        }
        if (!eventService.exist(id)) {
            return new ResponseEntity<ApiErrorResponse>(new ApiErrorResponse(ErrorHandlingConstants.ERROR_BUNDLE.eventNotFound.name(), ErrorHandlingConstants.ERROR_BUNDLE.eventNotFound.getDesc()), HttpStatus.OK);
        }
        if (model.getImage() == null || model.getImage().getContentType() == null) {
            throw new ParameterException();
        }
        if (model.getImage().getSize() > 1024 * 1024 * 10) {
            throw new ImageSizeInValidException();
        }

        String[] fileList = getFileList(model.getImage().getContentType());
        String uuid = genUniqueId();
        String fileName = uuid + "." + fileList[1];

        // Scale image & saved
        try {
            strTargetPathName = String.format("event_result/%s", fileName);

            strImagePath = resourceService.imageScaleTo(model.getImage(), strTargetPathName, false, null);
        } catch (Exception Exp) {
            logger.error("uploadResource {} error {}, {}, {}", "icon", id, model.getImage().getOriginalFilename(), fileName);
        }

        eventService.updateEventResultFileImg(id, strImagePath, model.getDisplaySort());

        // Prepare return result
        String strURL = String.format("%s%s", strHTTPServerHost, strImagePath);
        ResourceUploadRespVO resourceUploadRespVOObj = new ResourceUploadRespVO();
        resourceUploadRespVOObj.setUrl(strURL);

        return new ResponseEntity<ResourceUploadRespVO>(resourceUploadRespVOObj, org.springframework.http.HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/resultFile/{type}")
    public ResponseEntity<?> removeResultFileResource(@PathVariable("id") Integer id, @PathVariable("type") String type) {
        if (!type.toLowerCase().equals("image")) {
            throw new FileImageTypeInValidException();
        }

        if (!eventService.existReplyFile(id)) {
            return new ResponseEntity<ApiErrorResponse>(new ApiErrorResponse(ErrorHandlingConstants.ERROR_BUNDLE.eventResultFileNotFound.name(), ErrorHandlingConstants.ERROR_BUNDLE.eventResultFileNotFound.getDesc()), HttpStatus.OK);
        }

        eventService.removeEventResultFileImg(id);

        return new ResponseEntity<Void>(org.springframework.http.HttpStatus.NO_CONTENT);
    }

    @PostMapping("/join")
    public ResponseEntity<?> addEventJoin(@Valid @RequestBody ValidList<EventJoinVO> eventJoinVOS, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntityHelper.result(bindingResult.getAllErrors());
        }
        List<?> result = eventService.joinEvent(eventJoinVOS);
        return new ResponseEntity<List<?>>(result, HttpStatus.OK);
    }
    @PostMapping("/leave")
    public ResponseEntity<?> leaveEventJoing(@Valid @RequestBody ValidList<EventJoinVO> eventJoinVOS, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntityHelper.result(bindingResult.getAllErrors());
        }
        List<?> result = eventService.leaveEvent(eventJoinVOS);
        return new ResponseEntity<List<?>>(result, HttpStatus.OK);
    }
    @PostMapping("/visit")
    public ResponseEntity<?> addEventVisit(@Valid @RequestBody ValidList<EventFocusVO> eventFocusVOS, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntityHelper.result(bindingResult.getAllErrors());
        }
        List<?> result = eventService.visitEvent(eventFocusVOS);
        return new ResponseEntity<List<?>>(result, HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerEvent(@Valid @RequestBody ValidList<EventRegisterVO> eventRegisterVOS, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntityHelper.result(bindingResult.getAllErrors());
        }
        List<?> result = eventService.registerEvent(eventRegisterVOS);
        return new ResponseEntity<List<?>>(result, HttpStatus.OK);
    }
    @DeleteMapping(value = "/{id}/unregister")
    public List<?> unregisterEvent(@PathVariable("id") String ids) {
        return eventService.unregisterEvents(Constant.commaSpitter.splitToList(ids));
    }
    @GetMapping(value = "/tags")
    public JSONObject findAllTags() {
        return eventService.findAllTags();
    }
    @DeleteMapping(value = "/visit")
    public Boolean pruneVisitEvent() {
        return eventService.deleteVisitEvent();
    }

    @GetMapping(value = "/menu")
    public List<?> getUsersMenu() {
        return eventService.getEventMenu();
    }
}
