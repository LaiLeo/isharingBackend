package com.fih.ishareing.service.report;

import com.fih.ishareing.repository.entity.vwDumpFubonEvent;
import com.fih.ishareing.repository.entity.vwDumpRanking;
import com.fih.ishareing.repository.entity.vwDumpTwmEvent;
import com.fih.ishareing.service.report.model.DumpFubonEventVO;
import com.fih.ishareing.service.report.model.DumpRankingVO;
import com.fih.ishareing.service.report.model.DumpTwmEventVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ReportService {
    long countDumpRanking(Specification<vwDumpRanking> spec);
    long countDumpFubonEvent(Specification<vwDumpFubonEvent> spec);
    long countDumpTwmEvent(Specification<vwDumpTwmEvent> spec);
    List<DumpRankingVO> getDumpRanking(Specification<vwDumpRanking> spec, Pageable pageable);
    List<DumpFubonEventVO> getDumpFubonEvent(Specification<vwDumpFubonEvent> spec, Pageable pageable);
    List<DumpTwmEventVO> getDumpTwmEvent(Specification<vwDumpTwmEvent> spec, Pageable pageable);
}