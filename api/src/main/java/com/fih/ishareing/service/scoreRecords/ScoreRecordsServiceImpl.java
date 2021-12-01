package com.fih.ishareing.service.scoreRecords;

import com.fih.ishareing.repository.CoreScoreRecordsRepository;
import com.fih.ishareing.repository.CoreUserAccountRepository;
import com.fih.ishareing.repository.entity.tbCoreScoreRecords;
import com.fih.ishareing.repository.entity.tbCoreUserAccount;
import com.fih.ishareing.service.AbstractService;
import com.fih.ishareing.service.scoreRecords.model.ScoreRecordsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoreRecordsServiceImpl extends AbstractService implements ScoreRecordsService {
    @Autowired
    private CoreScoreRecordsRepository scoreRecordsRep;
    @Autowired
    private CoreUserAccountRepository userAccountRep;

    @Override
    public long count(Specification<tbCoreScoreRecords> spec) {
        return scoreRecordsRep.count(spec);
    }

    @Override
    public boolean exist(Integer id) {
        return scoreRecordsRep.existsById(id);
    }

    @Override
    public List<ScoreRecordsVO> findScoreRecords(Specification<tbCoreScoreRecords> spec, Pageable pageable) {
        return scoreRecordsRep.findAll(spec, pageable).stream().map(this::transfer).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Boolean calculateUsersScore() {
        Boolean result = false;
        List<tbCoreScoreRecords> tbCoreScoreRecordsList = new ArrayList<>();
        List<tbCoreUserAccount> userAccounts = userAccountRep.findAllByScoreGreaterThan(0);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (userAccounts.size() > 0) {
            result = true;
            for (int i = 0; i < userAccounts.size(); i++) {
                tbCoreScoreRecords tbCoreScoreRecord = new tbCoreScoreRecords();
                tbCoreScoreRecord.setUserId(userAccounts.get(i).getUserId());
                tbCoreScoreRecord.setScore(userAccounts.get(i).getScore());
                tbCoreScoreRecord.setComment("2021年以前的紀錄");
                tbCoreScoreRecord.setAddDate(now);

                tbCoreScoreRecordsList.add(tbCoreScoreRecord);

                if (i % 100 == 0) {
                    scoreRecordsRep.saveAll(tbCoreScoreRecordsList);
                    tbCoreScoreRecordsList = new ArrayList<>();
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

            scoreRecordsRep.saveAll(tbCoreScoreRecordsList);

            logger.info("Origin size {}", userAccounts.size());
        }

        return result;
    }

    private ScoreRecordsVO transfer(tbCoreScoreRecords scoreRecords) {
        ScoreRecordsVO instance = new ScoreRecordsVO();
        instance.setId(scoreRecords.getId());
        instance.setEventName(scoreRecords.getEventName());
        instance.setEventId(scoreRecords.getEventId());
        instance.setUserId(scoreRecords.getUserId());
        instance.setScore(scoreRecords.getScore());
        instance.setComment(scoreRecords.getComment());
        instance.setAddDate(scoreRecords.getAddDate());

        return instance;
    }
}