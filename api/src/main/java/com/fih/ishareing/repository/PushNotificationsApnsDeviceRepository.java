package com.fih.ishareing.repository;

import com.fih.ishareing.repository.entity.tbPushNotificationsApnsdevice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface PushNotificationsApnsDeviceRepository extends BaseRepository<tbPushNotificationsApnsdevice, Integer> {
	@Modifying
	void deleteAllByUserId(Integer userId);

	tbPushNotificationsApnsdevice findByUserId(Integer userId);
}