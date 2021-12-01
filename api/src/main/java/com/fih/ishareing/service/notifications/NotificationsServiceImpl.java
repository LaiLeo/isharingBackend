package com.fih.ishareing.service.notifications;

import com.fih.ishareing.constant.ApiErrorConstant;
import com.fih.ishareing.controller.base.error.ApiErrorResponse;
import com.fih.ishareing.controller.base.vo.result.ApiBatchResult;
import com.fih.ishareing.errorHandling.ErrorHandlingConstants.ERROR_BUNDLE;
import com.fih.ishareing.repository.PushNotificationsApnsDeviceRepository;
import com.fih.ishareing.repository.PushNotificationsGcmDeviceRepository;
import com.fih.ishareing.repository.entity.tbPushNotificationsApnsdevice;
import com.fih.ishareing.repository.entity.tbPushNotificationsGcmdevice;
import com.fih.ishareing.repository.vwUserRepository;
import com.fih.ishareing.service.AbstractService;
import com.fih.ishareing.service.notifications.model.NotificationsApnsPutVO;
import com.fih.ishareing.service.notifications.model.NotificationsApnsVO;
import com.fih.ishareing.service.notifications.model.NotificationsGcmPutVO;
import com.fih.ishareing.service.notifications.model.NotificationsGcmVO;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class NotificationsServiceImpl extends AbstractService implements NotificationsService {
    private static Logger logger = LoggerFactory.getLogger(NotificationsServiceImpl.class);
    @Autowired
    private PushNotificationsApnsDeviceRepository pushNotificationsApnsDeviceRep;
    @Autowired
    private PushNotificationsGcmDeviceRepository pushNotificationsGcmDeviceRep;
    @Autowired
    private vwUserRepository vwUserRep;

    @Override
    public long countApns(Specification<tbPushNotificationsApnsdevice> spec) {
        return pushNotificationsApnsDeviceRep.count(spec);
    }

    @Override
    public long countGcm(Specification<tbPushNotificationsGcmdevice> spec) {
        return pushNotificationsGcmDeviceRep.count(spec);
    }

    @Override
    public boolean existApns(Integer id) {
        return pushNotificationsApnsDeviceRep.existsById(id);
    }

    @Override
    public boolean existGcm(Integer id) {
        return pushNotificationsGcmDeviceRep.existsById(id);
    }

    @Override
    public List<NotificationsApnsVO> getApns(Specification<tbPushNotificationsApnsdevice> spec, Pageable pageable) {
        return pushNotificationsApnsDeviceRep.findAll(spec, pageable).stream().map(this::transferApns).collect(Collectors.toList());
    }

    @Override
    public List<NotificationsGcmVO> getGcm(Specification<tbPushNotificationsGcmdevice> spec, Pageable pageable) {
        return pushNotificationsGcmDeviceRep.findAll(spec, pageable).stream().map(this::transferGcm).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<?> putApns(List<NotificationsApnsPutVO> apns) {
        ApiBatchResult result = new ApiBatchResult();
        for (int i = 0; i < apns.size(); i++) {
            NotificationsApnsPutVO apn = apns.get(i);
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            builder.put(ApiErrorConstant.INDEX, i);
            builder.put(ApiErrorConstant.ID, apn.getUserId());
            tbPushNotificationsApnsdevice tbPushNotificationsApnsdevice = null;
            if (vwUserRep.countById(apn.getUserId()) == 0) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.userNotFound.name(), ERROR_BUNDLE.userNotFound.getDesc()));
            }
            tbPushNotificationsApnsdevice = pushNotificationsApnsDeviceRep.findByUserId(apn.getUserId());
            if (tbPushNotificationsApnsdevice == null) {
                tbPushNotificationsApnsdevice = new tbPushNotificationsApnsdevice();
            }

            if (CollectionUtils.isEmpty(errors)) {
                try {
                    if (apn.getName() != null) {
                        tbPushNotificationsApnsdevice.setName(apn.getName());
                    }

                    if (apn.getDeviceId() != null) {
                        tbPushNotificationsApnsdevice.setDeviceId(apn.getDeviceId());
                    }

                    tbPushNotificationsApnsdevice.setActive(apn.getActive());
                    tbPushNotificationsApnsdevice.setUserId(apn.getUserId());
                    tbPushNotificationsApnsdevice.setRegistrationId(apn.getRegistrationId());
                    tbPushNotificationsApnsdevice.setDateCreated(new Timestamp(System.currentTimeMillis()));

                    pushNotificationsApnsDeviceRep.save(tbPushNotificationsApnsdevice);

                } catch (Exception ex) {
                    logger.error("An error occurred while put apns, error:{}", ex.getMessage());
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
    public List<?> putGcm(List<NotificationsGcmPutVO> gcms) {
        ApiBatchResult result = new ApiBatchResult();
        for (int i = 0; i < gcms.size(); i++) {
            NotificationsGcmPutVO gcm = gcms.get(i);
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            builder.put(ApiErrorConstant.INDEX, i);
            builder.put(ApiErrorConstant.ID, gcm.getUserId());
            tbPushNotificationsGcmdevice tbPushNotificationsGcmdevice = null;
            if (vwUserRep.countById(gcm.getUserId()) == 0) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.userNotFound.name(), ERROR_BUNDLE.userNotFound.getDesc()));
            }
            tbPushNotificationsGcmdevice = pushNotificationsGcmDeviceRep.findByUserId(gcm.getUserId());
            if (tbPushNotificationsGcmdevice == null) {
                tbPushNotificationsGcmdevice = new tbPushNotificationsGcmdevice();
            }

            if (CollectionUtils.isEmpty(errors)) {
                try {
                    if (gcm.getName() != null) {
                        tbPushNotificationsGcmdevice.setName(gcm.getName());
                    }

                    if (gcm.getDeviceId() != null) {
                        tbPushNotificationsGcmdevice.setDeviceId(gcm.getDeviceId());
                    }

                    tbPushNotificationsGcmdevice.setActive(gcm.getActive());
                    tbPushNotificationsGcmdevice.setUserId(gcm.getUserId());
                    tbPushNotificationsGcmdevice.setRegistrationId(gcm.getRegistrationId());
                    tbPushNotificationsGcmdevice.setDateCreated(new Timestamp(System.currentTimeMillis()));

                    pushNotificationsGcmDeviceRep.save(tbPushNotificationsGcmdevice);

                } catch (Exception ex) {
                    logger.error("An error occurred while put gcm, error:{}", ex.getMessage());
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

    private NotificationsApnsVO transferApns(tbPushNotificationsApnsdevice apns) {
        NotificationsApnsVO instance = new NotificationsApnsVO();
        instance.setId(apns.getId());
        instance.setName(apns.getName());
        instance.setActive(apns.getActive());
        instance.setUserId(apns.getUserId());
        instance.setDeviceId(apns.getDeviceId());
        instance.setRegistrationId(apns.getRegistrationId());
        instance.setDataCreated(apns.getDateCreated());

        return instance;
    }

    private NotificationsGcmVO transferGcm(tbPushNotificationsGcmdevice gcm) {
        NotificationsGcmVO instance = new NotificationsGcmVO();
        instance.setId(gcm.getId());
        instance.setName(gcm.getName());
        instance.setActive(gcm.getActive());
        instance.setUserId(gcm.getUserId());
        instance.setDeviceId(gcm.getDeviceId());
        instance.setRegistrationId(gcm.getRegistrationId());
        instance.setDataCreated(gcm.getDateCreated());

        return instance;
    }
}