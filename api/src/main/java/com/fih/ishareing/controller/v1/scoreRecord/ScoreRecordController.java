package com.fih.ishareing.controller.v1.scoreRecord;

import com.fih.ishareing.common.ApiConstants;
import com.fih.ishareing.controller.base.BaseController;
import com.fih.ishareing.controller.base.vo.JsonBaseVO;
import com.fih.ishareing.repository.criteria.QueryConditionUtils;
import com.fih.ishareing.repository.criteria.SearchCriteria;
import com.fih.ishareing.repository.entity.tbCoreScoreRecords;
import com.fih.ishareing.service.scoreRecords.ScoreRecordsService;
import com.fih.ishareing.service.scoreRecords.model.ScoreRecordsVO;
import com.google.common.collect.ImmutableList;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/scoreRecords")
public class ScoreRecordController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ScoreRecordController.class);

    @Autowired
    private ScoreRecordsService scoreRecordsService;

    private Map<String, String> scoreRecordsSortable = new HashMap<String, String>();
    private Map<String, String> scoreRecordsSearchable = new HashMap<String, String>();

    @PostConstruct
    private void init() {
        this.scoreRecordsSortable = QueryConditionUtils.getSortColumn(ScoreRecordsVO.class);
        this.scoreRecordsSearchable = QueryConditionUtils.getSearchColumn(ScoreRecordsVO.class);
    }

    @GetMapping()
    public JsonBaseVO<ScoreRecordsVO> findScoreRecordss(@RequestParam(value = "search", required = false) String search,
                                            @RequestParam(value = "inlinecount", required = false, defaultValue = "true") boolean hasCount,
                                                        @RequestParam(value = "size", required = false, defaultValue = "65535") Integer size,
                                                        @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                        @RequestParam(value = "sort", required = false, defaultValue = "") String sort) {

        JsonBaseVO<ScoreRecordsVO> result = new JsonBaseVO<>();
        ImmutableList.Builder<SearchCriteria> builder = ImmutableList.builder();

        Specification<tbCoreScoreRecords> spec = specUtil.getSpecification(scoreRecordsSearchable, search, tbCoreScoreRecords.class, builder.build());
        List<ScoreRecordsVO> appConfigs = scoreRecordsService.findScoreRecords(spec, wrapPageable(scoreRecordsSortable, size, page, getSort(Encode.forUriComponent(sort)), ApiConstants.PAGE_SIZE_LIMIT.JSON));
        result.setResults(appConfigs);
        if (hasCount) {
            result.setCount(scoreRecordsService.count(spec));
        }

        return result;
    }

    @PostMapping()
    public Boolean calculateUsersScore() {
        return scoreRecordsService.calculateUsersScore();
    }

}
