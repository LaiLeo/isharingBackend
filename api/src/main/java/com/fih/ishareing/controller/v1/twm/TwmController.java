package com.fih.ishareing.controller.v1.twm;

import com.fih.ishareing.common.ApiConstants;
import com.fih.ishareing.controller.base.BaseController;
import com.fih.ishareing.controller.base.vaild.ValidList;
import com.fih.ishareing.controller.base.vo.JsonBaseVO;
import com.fih.ishareing.repository.criteria.QueryConditionUtils;
import com.fih.ishareing.repository.criteria.SearchCriteria;
import com.fih.ishareing.repository.entity.tbThirdTwmEnterprise;
import com.fih.ishareing.service.twm.TwmService;
import com.fih.ishareing.service.twm.model.TwmEnterpriseAddVO;
import com.fih.ishareing.service.twm.model.TwmEnterpriseVO;
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
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/twmEnterprise")
public class TwmController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(TwmController.class);

    @Autowired
    private TwmService twmService;


    private Map<String, String> twmSortable = new HashMap<String, String>();
    private Map<String, String> twmSearchable = new HashMap<String, String>();


    @PostConstruct
    private void init() {
        this.twmSortable = QueryConditionUtils.getSortColumn(TwmEnterpriseVO.class);
        this.twmSearchable = QueryConditionUtils.getSearchColumn(TwmEnterpriseVO.class);
    }

    @GetMapping()
    public JsonBaseVO<TwmEnterpriseVO> findTwmEnterprises(@RequestParam(value = "search", required = false) String search,
                                            @RequestParam(value = "inlinecount", required = false, defaultValue = "true") boolean hasCount,
                                                          @RequestParam(value = "size", required = false, defaultValue = "65535") Integer size,
                                                          @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                          @RequestParam(value = "sort", required = false, defaultValue = "") String sort) {

        JsonBaseVO<TwmEnterpriseVO> result = new JsonBaseVO<>();
        ImmutableList.Builder<SearchCriteria> builder = ImmutableList.builder();

        Specification<tbThirdTwmEnterprise> spec = specUtil.getSpecification(this.twmSearchable, search, tbThirdTwmEnterprise.class, builder.build());
        List<TwmEnterpriseVO> twmEnterprises = twmService.findTwmEnterprises(spec, wrapPageable(twmSortable, size, page, getSort(Encode.forUriComponent(sort)), ApiConstants.PAGE_SIZE_LIMIT.JSON));
        result.setResults(twmEnterprises);
        if (hasCount) {
            result.setCount(twmService.count(spec));
        }

        return result;
    }

    @PostMapping()
    public ResponseEntity<?> addTwmEnterprises(@Valid @RequestBody ValidList<TwmEnterpriseAddVO> twmEnterprises, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntityHelper.result(bindingResult.getAllErrors());
        }
        List<?> result = twmService.addTwmEnterprises(twmEnterprises);
        return new ResponseEntity<List<?>>(result, HttpStatus.OK);
    }

}
