package com.fih.ishareing.service.twm;

import com.fih.ishareing.repository.entity.tbThirdTwmEnterprise;
import com.fih.ishareing.service.twm.model.TwmEnterpriseAddVO;
import com.fih.ishareing.service.twm.model.TwmEnterpriseVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface TwmService {
    long count(Specification<tbThirdTwmEnterprise> spec);
    boolean exist(Integer id);
    List<TwmEnterpriseVO> findTwmEnterprises(Specification<tbThirdTwmEnterprise> spec, Pageable pageable);
    List<?> addTwmEnterprises(List<TwmEnterpriseAddVO> twmEnterpriseAddVOS);
}