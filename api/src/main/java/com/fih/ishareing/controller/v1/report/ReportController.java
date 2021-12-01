package com.fih.ishareing.controller.v1.report;

import com.fih.ishareing.common.ApiConstants;
import com.fih.ishareing.controller.base.BaseController;
import com.fih.ishareing.controller.base.vo.JsonBaseVO;
import com.fih.ishareing.repository.criteria.QueryConditionUtils;
import com.fih.ishareing.repository.criteria.SearchCriteria;
import com.fih.ishareing.repository.entity.vwDumpFubonEvent;
import com.fih.ishareing.repository.entity.vwDumpRanking;
import com.fih.ishareing.repository.entity.vwDumpTwmEvent;
import com.fih.ishareing.service.report.ReportService;
import com.fih.ishareing.service.report.model.DumpFubonEventVO;
import com.fih.ishareing.service.report.model.DumpRankingVO;
import com.fih.ishareing.service.report.model.DumpTwmEventVO;
import com.google.common.collect.ImmutableList;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/v1/report")
public class ReportController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private ReportService reportService;


    private Map<String, String> dumpRankingSortable = new HashMap<String, String>();
    private Map<String, String> dumpRankingSearchable = new HashMap<String, String>();
    private Map<String, String> dumpFubonEventSortable = new HashMap<String, String>();
    private Map<String, String> dumpFubonEventSearchable = new HashMap<String, String>();
    private Map<String, String> dumpTwmEventSortable = new HashMap<String, String>();
    private Map<String, String> dumpTwmEventSearchable = new HashMap<String, String>();

    @PostConstruct
    private void init() {
        this.dumpRankingSortable = QueryConditionUtils.getSortColumn(DumpRankingVO.class);
        this.dumpRankingSearchable = QueryConditionUtils.getSearchColumn(DumpRankingVO.class);
        this.dumpFubonEventSortable = QueryConditionUtils.getSortColumn(DumpFubonEventVO.class);
        this.dumpFubonEventSearchable = QueryConditionUtils.getSearchColumn(DumpFubonEventVO.class);
        this.dumpTwmEventSortable = QueryConditionUtils.getSortColumn(DumpTwmEventVO.class);
        this.dumpTwmEventSearchable = QueryConditionUtils.getSearchColumn(DumpTwmEventVO.class);
    }

    @GetMapping(value = "/dumpRanking")
    public JsonBaseVO<DumpRankingVO> getDumpRanking(@RequestParam(value = "search", required = false) String search,
                                                      @RequestParam(value = "inlinecount", required = false, defaultValue = "true") boolean hasCount,
                                                    @RequestParam(value = "size", required = false, defaultValue = "65535") Integer size,
                                                    @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                    @RequestParam(value = "sort", required = false, defaultValue = "") String sort) {

        JsonBaseVO<DumpRankingVO> result = new JsonBaseVO<>();
        ImmutableList.Builder<SearchCriteria> builder = ImmutableList.builder();

        Specification<vwDumpRanking> spec = specUtil.getSpecification(this.dumpRankingSearchable, search, vwDumpRanking.class, builder.build());
        List<DumpRankingVO> apns = reportService.getDumpRanking(spec, wrapPageable(dumpRankingSortable, size, page, getSort(Encode.forUriComponent(sort)), ApiConstants.PAGE_SIZE_LIMIT.JSON));
        result.setResults(apns);
        if (hasCount) {
            result.setCount(reportService.countDumpRanking(spec));
        }

        return result;
    }

    @GetMapping(value = "/dumpFubonEvent")
    public JsonBaseVO<DumpFubonEventVO> getDumpFubonEvent(@RequestParam(value = "search", required = false) String search,
                                                    @RequestParam(value = "inlinecount", required = false, defaultValue = "true") boolean hasCount,
                                                          @RequestParam(value = "size", required = false, defaultValue = "65535") Integer size,
                                                          @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                          @RequestParam(value = "sort", required = false, defaultValue = "") String sort) {

        JsonBaseVO<DumpFubonEventVO> result = new JsonBaseVO<>();
        ImmutableList.Builder<SearchCriteria> builder = ImmutableList.builder();

        Specification<vwDumpFubonEvent> spec = specUtil.getSpecification(this.dumpFubonEventSearchable, search, vwDumpFubonEvent.class, builder.build());
        List<DumpFubonEventVO> apns = reportService.getDumpFubonEvent(spec, wrapPageable(dumpFubonEventSortable, size, page, getSort(Encode.forUriComponent(sort)), ApiConstants.PAGE_SIZE_LIMIT.JSON));
        result.setResults(apns);
        if (hasCount) {
            result.setCount(reportService.countDumpFubonEvent(spec));
        }

        return result;
    }

    @GetMapping(value = "/dumpTwmEvent")
    public JsonBaseVO<DumpTwmEventVO> getDumpTwmEvent(@RequestParam(value = "search", required = false) String search,
                                                        @RequestParam(value = "inlinecount", required = false, defaultValue = "true") boolean hasCount,
                                                      @RequestParam(value = "size", required = false, defaultValue = "65535") Integer size,
                                                      @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                      @RequestParam(value = "sort", required = false, defaultValue = "") String sort) {

        JsonBaseVO<DumpTwmEventVO> result = new JsonBaseVO<>();
        ImmutableList.Builder<SearchCriteria> builder = ImmutableList.builder();

        Specification<vwDumpTwmEvent> spec = specUtil.getSpecification(this.dumpTwmEventSearchable, search, vwDumpTwmEvent.class, builder.build());
        List<DumpTwmEventVO> apns = reportService.getDumpTwmEvent(spec, wrapPageable(dumpTwmEventSortable, size, page, getSort(Encode.forUriComponent(sort)), ApiConstants.PAGE_SIZE_LIMIT.JSON));
        result.setResults(apns);
        if (hasCount) {
            result.setCount(reportService.countDumpTwmEvent(spec));
        }

        return result;
    }
}
