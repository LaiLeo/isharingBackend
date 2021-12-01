package com.fih.ishareing.service.twm;

import com.fih.ishareing.constant.ApiErrorConstant;
import com.fih.ishareing.controller.base.error.ApiErrorResponse;
import com.fih.ishareing.controller.base.vo.result.ApiBatchResult;
import com.fih.ishareing.errorHandling.ErrorHandlingConstants.ERROR_BUNDLE;
import com.fih.ishareing.repository.ThirdTwmEnterpriseRepository;
import com.fih.ishareing.repository.entity.tbThirdTwmEnterprise;
import com.fih.ishareing.service.AbstractService;
import com.fih.ishareing.service.twm.model.TwmEnterpriseAddVO;
import com.fih.ishareing.service.twm.model.TwmEnterpriseVO;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TwmServiceImpl extends AbstractService implements TwmService {
    private static Logger logger = LoggerFactory.getLogger(TwmServiceImpl.class);
    @Autowired
    private ThirdTwmEnterpriseRepository twmEnterpriseRep;

    @Override
    public long count(Specification<tbThirdTwmEnterprise> spec) {
        return twmEnterpriseRep.count(spec);
    }

    @Override
    public boolean exist(Integer id) {
        return twmEnterpriseRep.existsById(id);
    }

    @Override
    public List<TwmEnterpriseVO> findTwmEnterprises(Specification<tbThirdTwmEnterprise> spec, Pageable pageable) {
        return twmEnterpriseRep.findAll(spec, pageable).stream().map(this::transfer).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<?> addTwmEnterprises(List<TwmEnterpriseAddVO> twmEnterpriseAddVOS) {
        ApiBatchResult result = new ApiBatchResult();
        //Data will be cleared every time
        twmEnterpriseRep.truncateThirdTwmEnterpriseTable();
        for (int i = 0; i < twmEnterpriseAddVOS.size(); i++) {
            tbThirdTwmEnterprise tbThirdTwmEnterprise = new tbThirdTwmEnterprise();
            TwmEnterpriseAddVO twmEnterpriseAddVO = twmEnterpriseAddVOS.get(i);
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            builder.put(ApiErrorConstant.INDEX, i);

            if (CollectionUtils.isEmpty(errors)) {
                try {
                    tbThirdTwmEnterprise.setEnterpriseSerialEmail(twmEnterpriseAddVO.getEnterpriseSerialEmail());
                    tbThirdTwmEnterprise.setEnterpriseSerialName(twmEnterpriseAddVO.getEnterpriseSerialName());
                    tbThirdTwmEnterprise.setEnterpriseSerialNumber(twmEnterpriseAddVO.getEnterpriseSerialNumber());

                    twmEnterpriseRep.save(tbThirdTwmEnterprise);

                } catch (Exception ex) {
                    logger.error("An error occurred while add new TwmEnterprises, index:{}, error:{}", i,
                            ex.getMessage());
                    errors.add(new ApiErrorResponse(ERROR_BUNDLE.internalError));
                    builder.put(ApiErrorConstant.ERRORS, errors);
                }
            } else {
                builder.put(ApiErrorConstant.ERRORS, errors);
            }

            result.add(builder.build());
        }

        return result.build();
    }

    private TwmEnterpriseVO transfer(tbThirdTwmEnterprise twmEnterprise) {
        TwmEnterpriseVO instance = new TwmEnterpriseVO();
        instance.setId(twmEnterprise.getId());
        instance.setEnterpriseSerialNumber(twmEnterprise.getEnterpriseSerialNumber());
        instance.setEnterpriseSerialName(twmEnterprise.getEnterpriseSerialName());
        instance.setEnterpriseSerialEmail(twmEnterprise.getEnterpriseSerialEmail());

        return instance;
    }
}