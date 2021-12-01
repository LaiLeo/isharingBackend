package com.fih.ishareing.controller.v1.statistics;

import com.fih.ishareing.controller.base.BaseController;
import com.fih.ishareing.service.statistics.StatisticsService;
import com.fih.ishareing.service.statistics.model.StatisticsStatusVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticsController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    private StatisticsService statisticsService;


    @GetMapping("/status")
    public StatisticsStatusVO getStatisticsStatus() {
        return statisticsService.calculateStatisticsStatus();
    }


}
