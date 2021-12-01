package com.fih.ishareing.repository;

import com.fih.ishareing.repository.entity.vwEvent;
import org.springframework.stereotype.Repository;

@Repository
public interface vwEventRepository extends BaseRepository<vwEvent, Integer> {
    Integer countByVolunteerEventAndEnterprise(Boolean volunteerEvent, Boolean enterprise);
    Integer countByVolunteerEvent(Boolean volunteerEvent);
}