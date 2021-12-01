package com.fih.ishareing.controller.v1.banner;

import com.fih.ishareing.common.ApiConstants;
import com.fih.ishareing.constant.Constant;
import com.fih.ishareing.controller.base.BaseController;
import com.fih.ishareing.controller.base.vo.JsonBaseVO;
import com.fih.ishareing.errorHandling.exceptions.BannerNotFoundException;
import com.fih.ishareing.errorHandling.exceptions.FileImageTypeInValidException;
import com.fih.ishareing.errorHandling.exceptions.ImageSizeInValidException;
import com.fih.ishareing.errorHandling.exceptions.ParameterException;
import com.fih.ishareing.repository.criteria.QueryConditionUtils;
import com.fih.ishareing.repository.criteria.SearchCriteria;
import com.fih.ishareing.repository.entity.tbCoreBanner;
import com.fih.ishareing.service.banner.BannerService;
import com.fih.ishareing.service.banner.model.BannerAddVO;
import com.fih.ishareing.service.banner.model.BannerUpdateVO;
import com.fih.ishareing.service.banner.model.BannerVO;
import com.fih.ishareing.service.resource.ResourceService;
import com.fih.ishareing.service.user.model.ResourceUploadReqVO;
import com.fih.ishareing.service.user.model.ResourceUploadRespVO;
import com.google.common.collect.ImmutableList;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/banners")
public class BannerController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(BannerController.class);

    @Autowired
    private BannerService bannerService;

    @Autowired
    ResourceService resourceService;

    private Map<String, String> bannerSortable = new HashMap<String, String>();
    private Map<String, String> bannerSearchable = new HashMap<String, String>();

    @PostConstruct
    private void init() {
        this.bannerSortable = QueryConditionUtils.getSortColumn(BannerVO.class);
        this.bannerSearchable = QueryConditionUtils.getSearchColumn(BannerVO.class);
    }

    @GetMapping()
    public JsonBaseVO<BannerVO> findBanners(@RequestParam(value = "search", required = false) String search,
                                            @RequestParam(value = "inlinecount", required = false, defaultValue = "true") boolean hasCount,
                                            @RequestParam(value = "size", required = false, defaultValue = "65535") Integer size,
                                            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                            @RequestParam(value = "sort", required = false, defaultValue = "") String sort) {

        JsonBaseVO<BannerVO> result = new JsonBaseVO<>();
        ImmutableList.Builder<SearchCriteria> builder = ImmutableList.builder();

        Specification<tbCoreBanner> spec = specUtil.getSpecification(this.bannerSearchable, search, tbCoreBanner.class, builder.build());
        List<BannerVO> banners = bannerService.findBanners(spec, wrapPageable(bannerSortable, size, page, getSort(Encode.forUriComponent(sort)), ApiConstants.PAGE_SIZE_LIMIT.JSON));
        result.setResults(banners);
        if (hasCount) {
            result.setCount(bannerService.count(spec));
        }

        return result;
    }

    @PostMapping(value = "")
    public List<?> addBanners(@RequestBody List<BannerAddVO> banners) {
        return bannerService.addBanners(banners);
    }

    @PatchMapping()
    public List<?> updateBanners(@RequestBody List<BannerUpdateVO> banners) {
        return bannerService.updateBanners(banners);
    }

    @DeleteMapping("/{ids}/active")
    public List<?> deleteBanners(@PathVariable("ids") String ids) {
        return bannerService.deleteBanners(Constant.commaSpitter.splitToList(ids));
    }

    @PutMapping(value = "/{id}/file/{type}")
    public ResourceUploadRespVO uploadResource(@PathVariable("id") Integer id, @PathVariable("type") String type, @ModelAttribute ResourceUploadReqVO model) {
        // Initial Variable
        String strImagePath = "";
        String strTargetPathName = "";

        if (!bannerService.exist(id)) {
            throw new BannerNotFoundException();
        }

        if (!type.toLowerCase().equals("image") && !type.equals("smallImage")) {
            throw new FileImageTypeInValidException();
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

        if (type.toLowerCase().equals("image")) {
            // Scale image & saved
            try {
                strTargetPathName = String.format("banner/%s", fileName);

                strImagePath = resourceService.imageScaleTo(model.getImage(), strTargetPathName, false, null);
            } catch (Exception Exp) {
                logger.error("uploadResource {} error {}, {}, {}", "image", id, model.getImage().getOriginalFilename(), fileName);
            }

            // Update banner image
            bannerService.updateBannerImage(id, strImagePath);
        }

        if (type.equals("smallImage")) {
            // Scale image & saved
            try {
                strTargetPathName = String.format("banner/small_%s", fileName);

                strImagePath = resourceService.imageScaleTo(model.getImage(), strTargetPathName, false, null);
            } catch (Exception Exp) {
                logger.error("uploadResource {} error {}, {}, {}", "image", id, model.getImage().getOriginalFilename(), fileName);
            }

            // Update banner image
            bannerService.updateBannerSmallImage(id, strImagePath);
        }

        // Prepare return result
        String strURL = String.format("%s%s", strHTTPServerHost, strImagePath);
        ResourceUploadRespVO ResourceUploadRespVOObj = new ResourceUploadRespVO();
        ResourceUploadRespVOObj.setUrl(strURL);

        return ResourceUploadRespVOObj;
    }

    @DeleteMapping(value = "/{id}/file/{type}")
    public ResponseEntity<Void> removeResource(@PathVariable("id") Integer id, @PathVariable("type") String type) {

        if (!bannerService.exist(id)) {
            throw new BannerNotFoundException();
        }

        if (!type.toLowerCase().equals("image")) {
            throw new FileImageTypeInValidException();
        }

        if (type.toLowerCase().equals("image")) {
            bannerService.removeBannerImage(id);
        }
        return new ResponseEntity<Void>(org.springframework.http.HttpStatus.NO_CONTENT);
    }
}
