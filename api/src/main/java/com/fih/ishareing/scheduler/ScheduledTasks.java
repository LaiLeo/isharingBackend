package com.fih.ishareing.scheduler;

import com.fih.ishareing.repository.CoreUserAccountRepository;
import com.fih.ishareing.repository.entity.tbCoreUserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledTasks {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CoreUserAccountRepository userAccountRep;

    @Transactional
    @Scheduled(cron = "0 0 3 * * ?") 
    public void updateRanking() {
        logger.info("===updateRanking Start===");
        Long rank = userAccountRep.count();

        // 更新score = 0
        Integer updateCount = userAccountRep.updateRankByScore(rank.intValue(), 0);

        // 更新score > 0
        List<tbCoreUserAccount> tbCoreUserAccountSaves = new ArrayList<>();
        List<tbCoreUserAccount> tbCoreUserAccounts = userAccountRep.findAllByScoreGreaterThanOrderByScoreDesc(0);
        Integer score = 0;
        Integer ranking = 1;
        for (int i = 0; i < tbCoreUserAccounts.size(); i++) {
            tbCoreUserAccount userAccount = tbCoreUserAccounts.get(i);

            if (i == 0) {
                score = userAccount.getScore();
            }

            if (!score.equals(userAccount.getScore())) {
                score = userAccount.getScore();
                ranking++;
            }

            userAccount.setRanking(ranking);

            tbCoreUserAccountSaves.add(userAccount);

            if (i % 100 == 0) {
                userAccountRep.saveAll(tbCoreUserAccountSaves);
                tbCoreUserAccountSaves = new ArrayList<>();
                try {
                    Thread.sleep(50L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        userAccountRep.saveAll(tbCoreUserAccountSaves);

        logger.info("更新Score = 0:{}, Rank:{}", updateCount, rank);
        logger.info("更新Score > 0:{}", tbCoreUserAccounts.size());
        logger.info("===updateRanking End===");
    }
}
