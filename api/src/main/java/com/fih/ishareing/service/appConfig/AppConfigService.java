package com.fih.ishareing.service.appConfig;

import com.fih.ishareing.repository.entity.tbCoreAppConfig;
import com.fih.ishareing.service.appConfig.model.AppConfigAddVO;
import com.fih.ishareing.service.appConfig.model.AppConfigUpdateVO;
import com.fih.ishareing.service.appConfig.model.AppConfigVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface AppConfigService {
    long count(Specification<tbCoreAppConfig> spec);

    boolean exist(Integer id);

    List<AppConfigVO> findAppConfigs(Specification<tbCoreAppConfig> spec, Pageable pageable);

    List<?> addAppConfigs(List<AppConfigAddVO> appConfigs);

    List<?> updateAppConfigs(List<AppConfigUpdateVO> appConfigs);

    List<?> deleteAppConfigs(List<String> appConfigs);
}