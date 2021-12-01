package com.fih.ishareing.service.notifications;

import com.fih.ishareing.repository.entity.tbPushNotificationsApnsdevice;
import com.fih.ishareing.repository.entity.tbPushNotificationsGcmdevice;
import com.fih.ishareing.service.notifications.model.NotificationsApnsPutVO;
import com.fih.ishareing.service.notifications.model.NotificationsApnsVO;
import com.fih.ishareing.service.notifications.model.NotificationsGcmPutVO;
import com.fih.ishareing.service.notifications.model.NotificationsGcmVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface NotificationsService {
    long countApns(Specification<tbPushNotificationsApnsdevice> spec);
    long countGcm(Specification<tbPushNotificationsGcmdevice> spec);
    boolean existApns(Integer id);
    boolean existGcm(Integer id);
    List<NotificationsApnsVO> getApns(Specification<tbPushNotificationsApnsdevice> spec, Pageable pageable);
    List<NotificationsGcmVO> getGcm(Specification<tbPushNotificationsGcmdevice> spec, Pageable pageable);
    List<?> putApns(List<NotificationsApnsPutVO> apns);
    List<?> putGcm(List<NotificationsGcmPutVO> gcm);
}