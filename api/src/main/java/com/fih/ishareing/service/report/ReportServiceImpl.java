package com.fih.ishareing.service.report;

import com.fih.ishareing.repository.entity.vwDumpFubonEvent;
import com.fih.ishareing.repository.entity.vwDumpRanking;
import com.fih.ishareing.repository.entity.vwDumpTwmEvent;
import com.fih.ishareing.repository.vwDumpFubonEventRepository;
import com.fih.ishareing.repository.vwDumpRankingRepository;
import com.fih.ishareing.repository.vwDumpTwmEventRepository;
import com.fih.ishareing.service.AbstractService;
import com.fih.ishareing.service.report.model.DumpFubonEventVO;
import com.fih.ishareing.service.report.model.DumpRankingVO;
import com.fih.ishareing.service.report.model.DumpTwmEventVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl extends AbstractService implements ReportService {
    private static Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
    @Autowired
    private vwDumpRankingRepository vwDumpRankingRep;
    @Autowired
    private vwDumpFubonEventRepository vwDumpFubonEventRep;
    @Autowired
    private vwDumpTwmEventRepository vwDumpTwmEventRep;

    @Override
    public long countDumpRanking(Specification<vwDumpRanking> spec) {
        return vwDumpRankingRep.count(spec);
    }

    @Override
    public long countDumpFubonEvent(Specification<vwDumpFubonEvent> spec) {
        return vwDumpFubonEventRep.count(spec);
    }

    @Override
    public long countDumpTwmEvent(Specification<vwDumpTwmEvent> spec) {
        return vwDumpTwmEventRep.count(spec);
    }

    @Override
    public List<DumpRankingVO> getDumpRanking(Specification<vwDumpRanking> spec, Pageable pageable) {
        return vwDumpRankingRep.findAll(spec, pageable).stream().map(this::transferDumpRanking).collect(Collectors.toList());
    }

    @Override
    public List<DumpFubonEventVO> getDumpFubonEvent(Specification<vwDumpFubonEvent> spec, Pageable pageable) {
        return vwDumpFubonEventRep.findAll(spec, pageable).stream().map(this::transferDumpFubonEvent).collect(Collectors.toList());
    }

    @Override
    public List<DumpTwmEventVO> getDumpTwmEvent(Specification<vwDumpTwmEvent> spec, Pageable pageable) {
        return vwDumpTwmEventRep.findAll(spec, pageable).stream().map(this::transferDumpTwmEvent).collect(Collectors.toList());
    }

    private DumpRankingVO transferDumpRanking(vwDumpRanking dumpRanking) {
        DumpRankingVO instance = new DumpRankingVO();
        instance.setEmail(dumpRanking.getEmail());
        instance.setRanking(dumpRanking.getRanking());
        instance.setName(dumpRanking.getName());
        instance.setPhone(dumpRanking.getPhone());
        instance.setEventNames(dumpRanking.getEventNames());

        return instance;
    }

    private DumpFubonEventVO transferDumpFubonEvent(vwDumpFubonEvent dumpFubonEvent) {
        DumpFubonEventVO instance = new DumpFubonEventVO();
        instance.setEventId(dumpFubonEvent.getEventId());
        instance.setUserName(dumpFubonEvent.getUserName());
        instance.setSecurityId(dumpFubonEvent.getSecurityId());
        instance.setName(dumpFubonEvent.getName());
        instance.setEventUid(dumpFubonEvent.getEventUid());
        instance.setEventSubject(dumpFubonEvent.getEventSubject());
        instance.setNote(dumpFubonEvent.getNote());
        instance.setEventDate(dumpFubonEvent.getEventDate());
        instance.setEventHour(dumpFubonEvent.getEventHour());

        return instance;
    }

    private DumpTwmEventVO transferDumpTwmEvent(vwDumpTwmEvent dumpTwmEvent) {
        DumpTwmEventVO instance = new DumpTwmEventVO();
        instance.setEventId(dumpTwmEvent.getEventId());
        instance.setUserName(dumpTwmEvent.getUserName());
        instance.setSecurityId(dumpTwmEvent.getSecurityId());
        instance.setName(dumpTwmEvent.getName());
        instance.setEventUid(dumpTwmEvent.getEventUid());
        instance.setEventSubject(dumpTwmEvent.getEventSubject());
        instance.setNote(dumpTwmEvent.getNote());
        instance.setEventDate(dumpTwmEvent.getEventDate());
        instance.setEventHour(dumpTwmEvent.getEventHour());
        instance.setEnterpriseSerialNumber(dumpTwmEvent.getEnterpriseSerialNumber());
        instance.setEnterpriseSerialEmail(dumpTwmEvent.getEnterpriseSerialEmail());
        instance.setEnterpriseSerialDepartment(dumpTwmEvent.getEnterpriseSerialDepartment());
        instance.setEnterpriseSerialName(dumpTwmEvent.getEnterpriseSerialName());
        instance.setEnterpriseSerialId(dumpTwmEvent.getEnterpriseSerialId());
        instance.setEnterpriseSerialPhone(dumpTwmEvent.getEnterpriseSerialPhone());
        instance.setEnterpriseSerialType(dumpTwmEvent.getEnterpriseSerialType());
        instance.setEnterpriseSerialGroup(dumpTwmEvent.getEnterpriseSerialGroup());

        return instance;
    }
}