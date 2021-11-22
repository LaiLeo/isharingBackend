package com.fih.ishareing.controller.v1.banner;

import com.fih.ishareing.common.ApiConstants;
import com.fih.ishareing.controller.base.BaseController;
import com.fih.ishareing.controller.base.vo.JsonBaseVO;
import com.fih.ishareing.repository.criteria.QueryConditionUtils;
import com.fih.ishareing.repository.criteria.SearchCriteria;
import com.fih.ishareing.repository.entity.tbBanner;
import com.fih.ishareing.service.banner.BannerService;
import com.fih.ishareing.service.banner.model.BannerVO;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/banners")
public class BannerController extends BaseController {

    @Autowired
    private BannerService bannerService;

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
                                            Pageable pageable) {

        JsonBaseVO<BannerVO> result = new JsonBaseVO<>();
        ImmutableList.Builder<SearchCriteria> builder = ImmutableList.builder();

        Specification<tbBanner> spec = specUtil.getSpecification(this.bannerSearchable, search, tbBanner.class, builder.build());
        List<BannerVO> banners = bannerService.findBanners(spec, wrapPageable(bannerSortable, pageable, ApiConstants.PAGE_SIZE_LIMIT.JSON));
        result.setResults(banners);
        if (hasCount) {
            result.setCount(bannerService.count(spec));
        }

        return result;
    }
}
