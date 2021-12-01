package com.fih.ishareing.controller.v1.npo;

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
import com.fih.ishareing.repository.entity.tbCoreNpo;
import com.fih.ishareing.service.npo.NpoService;
import com.fih.ishareing.service.npo.model.*;
import com.fih.ishareing.service.resource.ResourceService;
import com.fih.ishareing.service.user.model.ResourceUploadReqVO;
import com.fih.ishareing.service.user.model.ResourceUploadRespVO;
import com.fih.ishareing.utils.ResponseEntityHelper;
import com.google.common.collect.ImmutableList;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/npos")
public class NpoController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(NpoController.class);

    @Autowired
    private NpoService npoService;
    @Autowired
    private ResourceService resourceService;

    private Map<String, String> npoSortable = new HashMap<String, String>();
    private Map<String, String> npoSearchable = new HashMap<String, String>();
    private Map<String, String> npoPromoteSortable = new HashMap<String, String>();
    private Map<String, String> npoPromoteSearchable = new HashMap<String, String>();

    @PostConstruct
    private void init() {
        this.npoSortable = QueryConditionUtils.getSortColumn(NpoVO.class);
        this.npoSearchable = QueryConditionUtils.getSearchColumn(NpoVO.class);
        this.npoPromoteSortable = QueryConditionUtils.getSortColumn(NpoPromoteVO.class);
        this.npoPromoteSearchable = QueryConditionUtils.getSearchColumn(NpoPromoteVO.class);
    }

    @GetMapping()
    public JsonBaseVO<NpoVO> findNpos(@RequestParam(value = "search", required = false) String search,
                                            @RequestParam(value = "inlinecount", required = false, defaultValue = "true") boolean hasCount,
                                      @RequestParam(value = "size", required = false, defaultValue = "65535") Integer size,
                                      @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                      @RequestParam(value = "sort", required = false, defaultValue = "") String sort) {

        JsonBaseVO<NpoVO> result = new JsonBaseVO<>();
        ImmutableList.Builder<SearchCriteria> builder = ImmutableList.builder();

        Specification<tbCoreNpo> spec = specUtil.getSpecification(this.npoSearchable, search, tbCoreNpo.class, builder.build());
        List<NpoVO> npos = npoService.findNpos(spec, wrapPageable(npoSortable, size, page, getSort(Encode.forUriComponent(sort)), ApiConstants.PAGE_SIZE_LIMIT.JSON));
        result.setResults(npos);
        if (hasCount) {
            result.setCount(npoService.count(spec));
        }

        return result;
    }

    @GetMapping(value = "/promote")
    public JsonBaseVO<NpoPromoteVO> findPromoteNpos() {

        JsonBaseVO<NpoPromoteVO> result = new JsonBaseVO<>();
        List<NpoPromoteVO> npos = npoService.findPromoteNpos();
        result.setResults(npos);

        return result;
    }

    @PostMapping(value = "")
    public ResponseEntity<?> addNpos(@Valid @RequestBody ValidList<NposAddVO> npos, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntityHelper.result(bindingResult.getAllErrors());
        }
        List<?> result = npoService.addNpos(npos);
        return new ResponseEntity<List<?>>(result, HttpStatus.OK);
    }

    @PatchMapping()
    public ResponseEntity<?> updateNpos(@Valid @RequestBody ValidList<NposUpdateVO> npos, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntityHelper.result(bindingResult.getAllErrors());
        }
        List<?> result = npoService.updateNpos(npos);
        return new ResponseEntity<List<?>>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{ids}/active")
    public List<?> deleteNpos(@PathVariable("ids") String ids) {
        return npoService.deleteNpos(Constant.commaSpitter.splitToList(ids));
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribeNpos(@Valid @RequestBody ValidList<NpoSubscribeVO> npoIds) {
        List<?> result = npoService.subscribeNpos(npoIds);
        return new ResponseEntity<List<?>>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{ids}/unsubscribe")
    public List<?> unsubscribeNpos(@PathVariable("ids") String ids) {
        return npoService.unsubscribeNpos(Constant.commaSpitter.splitToList(ids));
    }


    @PutMapping(value = "/{id}/file/{type}")
    public ResponseEntity<?> uploadResource(@PathVariable("id") Integer id, @PathVariable("type") String type, @ModelAttribute ResourceUploadReqVO model) {
        String strImagePath = "";
        String strTargetPathName = "";
        if (!type.toLowerCase().equals("icon") && !type.toLowerCase().equals("verifiedimg") && !type.toLowerCase().equals("registerimg")) {
            throw new FileImageTypeInValidException();
        }
        if (!npoService.exist(id)) {
            return new ResponseEntity<ApiErrorResponse>(new ApiErrorResponse(ErrorHandlingConstants.ERROR_BUNDLE.npoNotFound.name(), ErrorHandlingConstants.ERROR_BUNDLE.npoNotFound.getDesc()), HttpStatus.OK);
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

        if (type.toLowerCase().equals("icon")) {
            // Scale image & saved
            try {
                strTargetPathName = String.format("npo/npo_icon/%s", fileName);

                strImagePath = resourceService.imageScaleTo(model.getImage(), strTargetPathName, false, null);
            } catch (Exception Exp) {
                logger.error("uploadResource {} error {}, {}, {}", "icon", id, model.getImage().getOriginalFilename(), fileName);
            }

            npoService.updateNpoIcon(id, strImagePath);
        }

        if (type.toLowerCase().equals("verifiedimg")) {
            // Scale image & saved
            try {
                strTargetPathName = String.format("npo/verified_image/%s", fileName);

                strImagePath = resourceService.imageScaleTo(model.getImage(), strTargetPathName, false, null);
            } catch (Exception Exp) {
                logger.error("uploadResource {} error {}, {}, {}", "icon", id, model.getImage().getOriginalFilename(), fileName);
            }

            npoService.updateNpoVerifiedImg(id, strImagePath);
        }

        if (type.toLowerCase().equals("registerimg")) {
            // Scale image & saved
            try {
                strTargetPathName = String.format("npo/goverment_register_image/%s", fileName);

                strImagePath = resourceService.imageScaleTo(model.getImage(), strTargetPathName, false, null);
            } catch (Exception Exp) {
                logger.error("uploadResource {} error {}, {}, {}", "icon", id, model.getImage().getOriginalFilename(), fileName);
            }

            npoService.updateNpoRegisterImg(id, strImagePath);
        }


        // Prepare return result
        String strURL = String.format("%s%s", strHTTPServerHost, strImagePath);
        ResourceUploadRespVO resourceUploadRespVOObj = new ResourceUploadRespVO();
        resourceUploadRespVOObj.setUrl(strURL);

        return new ResponseEntity<ResourceUploadRespVO>(resourceUploadRespVOObj, org.springframework.http.HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/file/{type}")
    public ResponseEntity<?> removeResource(@PathVariable("id") Integer id, @PathVariable("type") String type) {
        if (!type.toLowerCase().equals("icon") && !type.toLowerCase().equals("verifiedimg") && !type.toLowerCase().equals("registerimg")) {
            throw new FileImageTypeInValidException();
        }

        if (!npoService.exist(id)) {
            return new ResponseEntity<ApiErrorResponse>(new ApiErrorResponse(ErrorHandlingConstants.ERROR_BUNDLE.npoNotFound.name(), ErrorHandlingConstants.ERROR_BUNDLE.npoNotFound.getDesc()), HttpStatus.OK);
        }

        if (type.toLowerCase().equals("icon")) {
            npoService.removeNpoIcon(id);
        }
        if (type.toLowerCase().equals("verifiedimg")) {
            npoService.removeNpoVerifiedImg(id);
        }
        if (type.toLowerCase().equals("registerimg")) {
            npoService.removeNpoRegisterImg(id);
        }

        return new ResponseEntity<Void>(org.springframework.http.HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/menu")
    public List<?> getNposMenu(@RequestParam(value = "email", required = false) String email) {
        return npoService.getNposMenu(email);
    }
}
