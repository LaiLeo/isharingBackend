package com.fih.ishareing.service.statistics;

import com.fih.ishareing.repository.vwCoreNpoMenuRepository;
import com.fih.ishareing.repository.vwEventRepository;
import com.fih.ishareing.repository.vwUserMenuRepository;
import com.fih.ishareing.repository.vwUserRegisteredEventRepository;
import com.fih.ishareing.service.AbstractService;
import com.fih.ishareing.service.statistics.model.StatisticsStatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl extends AbstractService implements StatisticsService {
    @Autowired
    private vwEventRepository vwEventRep;
    @Autowired
    private vwUserMenuRepository vwUserMenuRep;
    @Autowired
    private vwUserRegisteredEventRepository vwUserRegisteredEventRep;
    @Autowired
    private vwCoreNpoMenuRepository vwCoreNpoMenuRep;

    @Override
    public StatisticsStatusVO calculateStatisticsStatus() {
        StatisticsStatusVO result = new StatisticsStatusVO();
        Integer eventCount = vwEventRep.countByVolunteerEventAndEnterprise(true, false);
        Integer enterpriseEventCount = vwEventRep.countByVolunteerEventAndEnterprise(true, true);
        Integer supplyEventCount = vwEventRep.countByVolunteerEvent(false);
        Long memberCount = vwUserMenuRep.count();
        Integer eventRegisterCount = vwUserRegisteredEventRep.countByVolunteerEventAndEnterprise(true, false);
        Integer eventFinishCount = vwUserRegisteredEventRep.countByVolunteerEventAndEnterpriseAndJoinedAndLeaved(true, false, true, true);
        Integer enterpriseRegisterCount = vwUserRegisteredEventRep.countByVolunteerEventAndEnterprise(true , true);
        Integer enterpriseFinishCount = vwUserRegisteredEventRep.countByVolunteerEventAndEnterpriseAndJoinedAndLeaved(true, true, true, true);
        Integer supplyEventRegisterCount = vwUserRegisteredEventRep.countByVolunteerEvent(false);
        Integer supplyFinishCount = vwUserRegisteredEventRep.countByVolunteerEventAndJoined(false, true);
        Integer npoVerifiedCount = vwCoreNpoMenuRep.countByAdmViewedAndVerified(true, true);
        Long npoCount = vwCoreNpoMenuRep.count();


        result.setEventCount(eventCount);
        result.setEnterpriseEventCount(enterpriseEventCount);
        result.setSupplyEventCount(supplyEventCount);
        result.setMemberCount(memberCount.intValue());
        result.setEventRegisterCount(eventRegisterCount);
        result.setEventFinishCount(eventFinishCount);
        result.setEnterpriseRegisterCount(enterpriseRegisterCount);
        result.setEnterpriseFinishCount(enterpriseFinishCount);
        result.setSupplyEventRegisterCount(supplyEventRegisterCount);
        result.setSupplyFinishCount(supplyFinishCount);
        result.setNpoVerifiedCount(npoVerifiedCount);
        result.setNpoCount(npoCount.intValue());


        return result;
    }
}