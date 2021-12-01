package com.fih.ishareing.repository;

import com.fih.ishareing.repository.entity.vwUserRegisteredEvent;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface vwUserRegisteredEventRepository extends BaseRepository<vwUserRegisteredEvent, Integer> {
    vwUserRegisteredEvent findByUserIdAndUid(Integer userId, String uid);

    List<vwUserRegisteredEvent> findAllByUserId(Integer userId);

    Integer countByVolunteerEventAndEnterprise(Boolean volunteerEvent, Boolean enterprise);

    Integer countByVolunteerEventAndEnterpriseAndJoinedAndLeaved(Boolean volunteerEvent, Boolean enterprise, Boolean join, Boolean leave);

    Integer countByVolunteerEvent(Boolean volunteerEvent);

    Integer countByVolunteerEventAndJoined(Boolean volunteerEvent, Boolean join);
}