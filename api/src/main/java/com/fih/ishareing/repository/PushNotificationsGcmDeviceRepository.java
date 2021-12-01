package com.fih.ishareing.repository;

import com.fih.ishareing.repository.entity.tbPushNotificationsGcmdevice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface PushNotificationsGcmDeviceRepository extends BaseRepository<tbPushNotificationsGcmdevice, Integer> {
	@Modifying
	void deleteAllByUserId(Integer userId);

	tbPushNotificationsGcmdevice findByUserId(Integer userId);
}