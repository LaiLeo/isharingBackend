package com.fih.ishareing.controller.v1.notifications;

import com.fih.ishareing.common.ApiConstants;
import com.fih.ishareing.controller.base.BaseController;
import com.fih.ishareing.controller.base.vaild.ValidList;
import com.fih.ishareing.controller.base.vo.JsonBaseVO;
import com.fih.ishareing.repository.criteria.QueryConditionUtils;
import com.fih.ishareing.repository.criteria.SearchCriteria;
import com.fih.ishareing.repository.entity.tbPushNotificationsApnsdevice;
import com.fih.ishareing.repository.entity.tbPushNotificationsGcmdevice;
import com.fih.ishareing.service.notifications.NotificationsService;
import com.fih.ishareing.service.notifications.model.NotificationsApnsPutVO;
import com.fih.ishareing.service.notifications.model.NotificationsApnsVO;
import com.fih.ishareing.service.notifications.model.NotificationsGcmPutVO;
import com.fih.ishareing.service.notifications.model.NotificationsGcmVO;
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
@RequestMapping("/api/v1/notifications")
public class NotificationsController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(NotificationsController.class);

    @Autowired
    private NotificationsService notificationsService;


    private Map<String, String> apnsSortable = new HashMap<String, String>();
    private Map<String, String> apnsSearchable = new HashMap<String, String>();
    private Map<String, String> gcmSortable = new HashMap<String, String>();
    private Map<String, String> gcmSearchable = new HashMap<String, String>();

    @PostConstruct
    private void init() {
        this.apnsSortable = QueryConditionUtils.getSortColumn(NotificationsApnsVO.class);
        this.apnsSearchable = QueryConditionUtils.getSearchColumn(NotificationsApnsVO.class);
        this.gcmSortable = QueryConditionUtils.getSortColumn(NotificationsGcmVO.class);
        this.gcmSearchable = QueryConditionUtils.getSearchColumn(NotificationsGcmVO.class);
    }

    @GetMapping(value = "/apns")
    public JsonBaseVO<NotificationsApnsVO> getApns(@RequestParam(value = "search", required = false) String search,
                                                      @RequestParam(value = "inlinecount", required = false, defaultValue = "true") boolean hasCount,
                                                   @RequestParam(value = "size", required = false, defaultValue = "65535") Integer size,
                                                   @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                   @RequestParam(value = "sort", required = false, defaultValue = "") String sort) {

        JsonBaseVO<NotificationsApnsVO> result = new JsonBaseVO<>();
        ImmutableList.Builder<SearchCriteria> builder = ImmutableList.builder();

        Specification<tbPushNotificationsApnsdevice> spec = specUtil.getSpecification(this.apnsSearchable, search, tbPushNotificationsApnsdevice.class, builder.build());
        List<NotificationsApnsVO> apns = notificationsService.getApns(spec, wrapPageable(apnsSortable, size, page, getSort(Encode.forUriComponent(sort)), ApiConstants.PAGE_SIZE_LIMIT.JSON));
        result.setResults(apns);
        if (hasCount) {
            result.setCount(notificationsService.countApns(spec));
        }

        return result;
    }


    @GetMapping(value = "/gcm")
    public JsonBaseVO<NotificationsGcmVO> getGcm(@RequestParam(value = "search", required = false) String search,
                                                               @RequestParam(value = "inlinecount", required = false, defaultValue = "true") boolean hasCount,
                                                 @RequestParam(value = "size", required = false, defaultValue = "65535") Integer size,
                                                 @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                 @RequestParam(value = "sort", required = false, defaultValue = "") String sort,
                                                 HttpServletRequest request) {

        JsonBaseVO<NotificationsGcmVO> result = new JsonBaseVO<>();
        ImmutableList.Builder<SearchCriteria> builder = ImmutableList.builder();


        Specification<tbPushNotificationsGcmdevice> spec = specUtil.getSpecification(gcmSearchable, search, tbPushNotificationsGcmdevice.class, builder.build());
        List<NotificationsGcmVO> eventRegisteredList = notificationsService.getGcm(spec, wrapPageable(gcmSortable, size, page, getSort(Encode.forUriComponent(sort)), ApiConstants.PAGE_SIZE_LIMIT.JSON));
        result.setResults(eventRegisteredList);
        if (hasCount) {
            result.setCount(notificationsService.countGcm(spec));
        }

        return result;
    }

    @PutMapping(value = "/apns")
    public ResponseEntity<?> putApns(@Valid @RequestBody ValidList<NotificationsApnsPutVO> apns, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntityHelper.result(bindingResult.getAllErrors());
        }
        List<?> result = notificationsService.putApns(apns);
        return new ResponseEntity<List<?>>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/gcm")
    public ResponseEntity<?> putGcm(@Valid @RequestBody ValidList<NotificationsGcmPutVO> gcm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntityHelper.result(bindingResult.getAllErrors());
        }
        List<?> result = notificationsService.putGcm(gcm);
        return new ResponseEntity<List<?>>(result, HttpStatus.OK);
    }

}
