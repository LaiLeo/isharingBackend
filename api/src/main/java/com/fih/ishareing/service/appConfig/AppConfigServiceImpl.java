package com.fih.ishareing.service.appConfig;

import com.fih.ishareing.constant.ApiErrorConstant;
import com.fih.ishareing.controller.base.error.ApiErrorResponse;
import com.fih.ishareing.controller.base.vo.result.ApiBatchResult;
import com.fih.ishareing.errorHandling.ErrorHandlingConstants.ERROR_BUNDLE;
import com.fih.ishareing.repository.CoreAppConfigRepository;
import com.fih.ishareing.repository.entity.tbCoreAppConfig;
import com.fih.ishareing.service.AbstractService;
import com.fih.ishareing.service.appConfig.model.AppConfigAddVO;
import com.fih.ishareing.service.appConfig.model.AppConfigUpdateVO;
import com.fih.ishareing.service.appConfig.model.AppConfigVO;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppConfigServiceImpl extends AbstractService implements AppConfigService {
    private static Logger logger = LoggerFactory.getLogger(AppConfigServiceImpl.class);
    @Autowired
    private CoreAppConfigRepository appConfigRep;
	
    @Override
    public long count(Specification<tbCoreAppConfig> spec) {
        return appConfigRep.count(spec);
    }

    @Override
    public boolean exist(Integer id) {
    	return appConfigRep.existsById(id);
    }

    @Override
    public List<AppConfigVO> findAppConfigs(Specification<tbCoreAppConfig> spec, Pageable pageable) {
        return appConfigRep.findAll(spec, pageable).stream().map(this::transfer).collect(Collectors.toList());
    }
    @Transactional
    @Override
    public List<?> addAppConfigs(List<AppConfigAddVO> appConfigs) {
        ApiBatchResult result = new ApiBatchResult();
        for (int i = 0; i < appConfigs.size(); i++) {
        	tbCoreAppConfig tbCoreAppConfigSaved = null;
        	AppConfigAddVO appConfig = appConfigs.get(i);
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            builder.put(ApiErrorConstant.INDEX, i);

            if (CollectionUtils.isEmpty(errors)) {
                try {
                	
                	tbCoreAppConfig tbCoreAppConfigObj = new tbCoreAppConfig();
                	tbCoreAppConfigObj.setIosVersion(appConfig.getIosVersion());
                	tbCoreAppConfigObj.setAndroidVersion(appConfig.getAndroidVersion());
                	tbCoreAppConfigObj.setForcedUpgrade(appConfig.getForcedUpgrade());
                	tbCoreAppConfigObj.setQuestionnaireUrl(appConfig.getQuestionnaireUrl());

                	tbCoreAppConfigSaved = appConfigRep.save(tbCoreAppConfigObj);
                	
                    builder.put(ApiErrorConstant.ID, tbCoreAppConfigSaved.getId());
                } catch (Exception ex) {
                    logger.error("An error occurred while add new AppConfig, url:{}, error:{}", appConfig.getQuestionnaireUrl(),
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
    @Transactional
    @Override
    public List<?> updateAppConfigs(List<AppConfigUpdateVO> appConfigs) {
        ApiBatchResult result = new ApiBatchResult();
        for (int i = 0; i < appConfigs.size(); i++) {
        	tbCoreAppConfig tbCoreAppConfigSaved = null;
        	AppConfigUpdateVO appConfig = appConfigs.get(i);
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            builder.put(ApiErrorConstant.INDEX, i);
            builder.put(ApiErrorConstant.ID, appConfig.getId());

            if (CollectionUtils.isEmpty(errors)) {
                try {
                	Optional<tbCoreAppConfig> tbCoreAppConfigObjOptional = appConfigRep.findById(appConfig.getId());
                    if (!tbCoreAppConfigObjOptional.isPresent()) {
                        errors.add(new ApiErrorResponse(ERROR_BUNDLE.appConfigsNotFound));
                        builder.put(ApiErrorConstant.ERRORS, errors);
                    }

                    if (errors.size() == 0) {
                        tbCoreAppConfig tbCoreAppConfigObj = tbCoreAppConfigObjOptional.get();
                        if (appConfig.getIosVersion() != null) {
                            tbCoreAppConfigObj.setIosVersion(appConfig.getIosVersion());
                        }
                        if (appConfig.getAndroidVersion() != null) {
                            tbCoreAppConfigObj.setAndroidVersion(appConfig.getAndroidVersion());
                        }
                        if (appConfig.getForcedUpgrade() != null) {
                            tbCoreAppConfigObj.setForcedUpgrade(appConfig.getForcedUpgrade());
                        }
                        if (appConfig.getQuestionnaireUrl() != null) {
                            tbCoreAppConfigObj.setQuestionnaireUrl(appConfig.getQuestionnaireUrl());
                        }


                        tbCoreAppConfigSaved = appConfigRep.save(tbCoreAppConfigObj);
                    }
                } catch (Exception ex) {
                    logger.error("An error occurred while updating AppConfig, id:{}, error:{}", appConfig.getId(),
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
    @Transactional
    @Override
    public List<?> deleteAppConfigs(List<String> appConfigs) {
        ApiBatchResult result = new ApiBatchResult();

        for (int i = 0; i < appConfigs.size(); i++) {
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            Integer appConfigId = Integer.valueOf(appConfigs.get(i));

            builder.put(ApiErrorConstant.ID, appConfigId);

            Optional<tbCoreAppConfig> tbCoreAppConfigObjOptional = appConfigRep.findById(appConfigId);
            if (!tbCoreAppConfigObjOptional.isPresent()) {
                errors.add(new ApiErrorResponse(ERROR_BUNDLE.appConfigsNotFound));
                builder.put(ApiErrorConstant.ERRORS, errors);
            }
            if (errors.size() == 0) {
                try {
                    appConfigRep.deleteById(appConfigId);
                } catch (Exception ex) {
                    logger.error("An error occurred while deleting AppConfig, id:{}, error:{}", appConfigId, ex.getMessage());
                    errors.add(new ApiErrorResponse(ERROR_BUNDLE.internalError));
                    builder.put(ApiErrorConstant.ERRORS, errors);
                }
            }

            result.add(builder.build());
        }

        return result.build();
    }

    private AppConfigVO transfer(tbCoreAppConfig appConfig) {
        AppConfigVO instance = new AppConfigVO();
        instance.setId(appConfig.getId());
        instance.setIosVersion(appConfig.getIosVersion());
        instance.setAndroidVersion(appConfig.getAndroidVersion());
        instance.setForcedUpgrade(appConfig.getForcedUpgrade());
        instance.setQuestionnaireUrl(appConfig.getQuestionnaireUrl());

        return instance;
    }
    
}