package com.fih.ishareing.controller.v1.donation;

import com.fih.ishareing.common.ApiConstants.PAGE_SIZE_LIMIT;
import com.fih.ishareing.constant.Constant;
import com.fih.ishareing.controller.base.BaseController;
import com.fih.ishareing.controller.base.error.ApiErrorResponse;
import com.fih.ishareing.controller.base.factory.ColumnFactory;
import com.fih.ishareing.controller.base.vaild.ValidList;
import com.fih.ishareing.controller.base.vo.JsonBaseVO;
import com.fih.ishareing.controller.v1.user.UsersController;
import com.fih.ishareing.errorHandling.ErrorHandlingConstants;
import com.fih.ishareing.errorHandling.exceptions.FileImageTypeInValidException;
import com.fih.ishareing.errorHandling.exceptions.ImageSizeInValidException;
import com.fih.ishareing.errorHandling.exceptions.ParameterException;
import com.fih.ishareing.repository.criteria.SearchCriteria;
import com.fih.ishareing.repository.entity.tbCoreDonationNpo;
import com.fih.ishareing.service.donation.DonationService;
import com.fih.ishareing.service.donation.model.DonationNposAddVO;
import com.fih.ishareing.service.donation.model.DonationNposListRespVO;
import com.fih.ishareing.service.donation.model.DonationNposUpdateVO;
import com.fih.ishareing.service.resource.ResourceService;
import com.fih.ishareing.service.user.model.ResourceUploadReqVO;
import com.fih.ishareing.service.user.model.ResourceUploadRespVO;
import com.fih.ishareing.utils.ResponseEntityHelper;
import com.google.common.collect.ImmutableList;
import org.json.simple.JSONObject;
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
@RequestMapping("/api/v1/donationNpos")
public class DonationNposController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(DonationNposController.class);

    private Map<String, String> userSortable = new HashMap<String, String>();
    private Map<String, String> userSearchable = new HashMap<String, String>();

    @Autowired
    private DonationService donationService;
    @Autowired
    private ResourceService resourceService;

    @PostConstruct
    private void init() {
        this.userSortable = null;
        this.userSearchable = null;
    }

    @GetMapping(value = "")
    public JsonBaseVO<DonationNposListRespVO> listDonationNpos(@RequestParam(value = "search", required = false) String search,
                                                               @RequestParam(value = "inlinecount", required = false, defaultValue = "true") boolean hasCount,
                                                               @RequestParam(value = "size", required = false, defaultValue = "65535") Integer size,
                                                               @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                               @RequestParam(value = "sort", required = false, defaultValue = "") String sort) {

        JsonBaseVO<DonationNposListRespVO> result = new JsonBaseVO<>();
        ImmutableList.Builder<SearchCriteria> builder = ImmutableList.builder();

        ColumnFactory factory = new ColumnFactory()
                .build("id", "id", true, true)
                .build("name", "name", true, true)
                .build("description", "description", true, true)
                .build("code", "code", true, true)
                .build("newebpayUrl", "newebpayUrl", true, true)
                .build("npoIcon", "npoIcon", true, true)
                .build("thumbPath", "thumbPath", true, true)
                .build("displaySort", "displaySort", true, false);

        this.userSortable = factory.getSortColumnMap();
        this.userSearchable = factory.getSearchColumnMap();

        Specification<tbCoreDonationNpo> spec = specUtil.getSpecification(this.userSearchable, search, tbCoreDonationNpo.class, builder.build());
        JSONObject JOQueryResult = donationService.listDonationNpos(spec, wrapPageable(userSortable, size, page, getSort(Encode.forUriComponent(sort)), PAGE_SIZE_LIMIT.JSON));
        result.setCount(Long.parseLong(JOQueryResult.get("Count").toString()));
        result.setResults((List<DonationNposListRespVO>) JOQueryResult.get("Results"));

        return result;
    }

    @PostMapping(value = "")
    public ResponseEntity<?> addDonationNpos(@Valid @RequestBody ValidList<DonationNposAddVO> npos, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntityHelper.result(bindingResult.getAllErrors());
        }
        List<?> result = donationService.addDonationNpos(npos);
        return new ResponseEntity<List<?>>(result, HttpStatus.OK);
    }

    @PatchMapping()
    public ResponseEntity<?> updateDonationNpos(@Valid @RequestBody ValidList<DonationNposUpdateVO> npos, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntityHelper.result(bindingResult.getAllErrors());
        }
        List<?> result = donationService.updateDonationNpos(npos);
        return new ResponseEntity<List<?>>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{ids}/active")
    public List<?> deleteDonationNpos(@PathVariable("ids") String ids) {
        return donationService.deleteDonationNpos(Constant.commaSpitter.splitToList(ids));
    }


    @PutMapping(value = "/{id}/file/{type}")
    public ResponseEntity<?> uploadResource(@PathVariable("id") Integer id, @PathVariable("type") String type, @ModelAttribute ResourceUploadReqVO model) {
        String strImagePath = "";
        String strTargetPathName = "";
        if (!type.toLowerCase().equals("icon")) {
            throw new FileImageTypeInValidException();
        }
        if (!donationService.exist(id)) {
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
                strTargetPathName = String.format("donation_npo/%s", fileName);

                strImagePath = resourceService.imageScaleTo(model.getImage(), strTargetPathName, false, null);
            } catch (Exception Exp) {
                logger.error("uploadResource {} error {}, {}, {}", "icon", id, model.getImage().getOriginalFilename(), fileName);
            }

            donationService.updateDonationIcon(id, strImagePath);
        }


        // Prepare return result
        String strURL = String.format("%s%s", strHTTPServerHost, strImagePath);
        ResourceUploadRespVO resourceUploadRespVOObj = new ResourceUploadRespVO();
        resourceUploadRespVOObj.setUrl(strURL);

        return new ResponseEntity<ResourceUploadRespVO>(resourceUploadRespVOObj, org.springframework.http.HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/file/{type}")
    public ResponseEntity<?> removeResource(@PathVariable("id") Integer id, @PathVariable("type") String type) {
        if (!type.toLowerCase().equals("icon")) {
            throw new FileImageTypeInValidException();
        }

        if (!donationService.exist(id)) {
            return new ResponseEntity<ApiErrorResponse>(new ApiErrorResponse(ErrorHandlingConstants.ERROR_BUNDLE.npoNotFound.name(), ErrorHandlingConstants.ERROR_BUNDLE.npoNotFound.getDesc()), HttpStatus.OK);
        }

        if (type.toLowerCase().equals("icon")) {
            donationService.removeDonationIcon(id);
        }

        return new ResponseEntity<Void>(org.springframework.http.HttpStatus.NO_CONTENT);
    }
}
