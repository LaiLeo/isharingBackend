package com.fih.ishareing.service.scoreRecords;

import com.fih.ishareing.repository.entity.tbCoreScoreRecords;
import com.fih.ishareing.service.scoreRecords.model.ScoreRecordsVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ScoreRecordsService {
    long count(Specification<tbCoreScoreRecords> spec);

    boolean exist(Integer id);

    List<ScoreRecordsVO> findScoreRecords(Specification<tbCoreScoreRecords> spec, Pageable pageable);

    Boolean calculateUsersScore();
}