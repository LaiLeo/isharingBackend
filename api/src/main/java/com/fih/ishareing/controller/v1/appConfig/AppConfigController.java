package com.fih.ishareing.controller.v1.appConfig;

import com.fih.ishareing.common.ApiConstants;
import com.fih.ishareing.constant.Constant;
import com.fih.ishareing.controller.base.BaseController;
import com.fih.ishareing.controller.base.vaild.ValidList;
import com.fih.ishareing.controller.base.vo.JsonBaseVO;
import com.fih.ishareing.errorHandling.exceptions.ParameterException;
import com.fih.ishareing.repository.criteria.QueryConditionUtils;
import com.fih.ishareing.repository.criteria.SearchCriteria;
import com.fih.ishareing.repository.entity.tbCoreAppConfig;
import com.fih.ishareing.service.appConfig.AppConfigService;
import com.fih.ishareing.service.appConfig.model.AppConfigAddVO;
import com.fih.ishareing.service.appConfig.model.AppConfigUpdateVO;
import com.fih.ishareing.service.appConfig.model.AppConfigVO;
import com.fih.ishareing.utils.ResponseEntityHelper;
import com.google.common.collect.ImmutableList;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/api/v1/appConfigs")
public class AppConfigController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(AppConfigController.class);

    @Autowired
    private AppConfigService appConfigService;

    private Map<String, String> appConfigSortable = new HashMap<String, String>();
    private Map<String, String> appConfigSearchable = new HashMap<String, String>();

    @PostConstruct
    private void init() {
        this.appConfigSortable = QueryConditionUtils.getSortColumn(AppConfigVO.class);
        this.appConfigSearchable = QueryConditionUtils.getSearchColumn(AppConfigVO.class);
    }

    @GetMapping()
    public JsonBaseVO<AppConfigVO> findAppConfigs(@RequestParam(value = "search", required = false) String search,
                                                  @RequestParam(value = "inlinecount", required = false, defaultValue = "true") boolean hasCount,
                                                  @RequestParam(value = "size", required = false, defaultValue = "65535") Integer size,
                                                  @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                  @RequestParam(value = "sort", required = false, defaultValue = "") String sort) {
        JsonBaseVO<AppConfigVO> result = new JsonBaseVO<>();
        ImmutableList.Builder<SearchCriteria> builder = ImmutableList.builder();

        Specification<tbCoreAppConfig> spec = specUtil.getSpecification(appConfigSearchable, search, tbCoreAppConfig.class, builder.build());
        List<AppConfigVO> appConfigs = appConfigService.findAppConfigs(spec, wrapPageable(appConfigSortable, size, page, getSort(Encode.forUriComponent(sort)), ApiConstants.PAGE_SIZE_LIMIT.JSON));
        result.setResults(appConfigs);
        if (hasCount) {
            result.setCount(appConfigService.count(spec));
        }

        return result;
    }

    @PostMapping(value = "")
    public ResponseEntity<?> addAppConfigs(@Valid @RequestBody ValidList<AppConfigAddVO> appConfigs, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntityHelper.result(bindingResult.getAllErrors());
        }
        List<?> result = appConfigService.addAppConfigs(appConfigs);
        return new ResponseEntity<List<?>>(result, HttpStatus.OK);
    }

    @PatchMapping()
    public ResponseEntity<?> updateAppConfigss(@Valid @RequestBody ValidList<AppConfigUpdateVO> appConfigs, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntityHelper.result(bindingResult.getAllErrors());
        }
        List<?> result = appConfigService.updateAppConfigs(appConfigs);
        return new ResponseEntity<List<?>>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{ids}/active")
    public List<?> deleteAppConfigss(@PathVariable("ids") String ids) {
        return appConfigService.deleteAppConfigs(Constant.commaSpitter.splitToList(ids));
    }

}
