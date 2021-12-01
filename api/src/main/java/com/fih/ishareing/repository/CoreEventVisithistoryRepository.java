package com.fih.ishareing.repository;

import com.fih.ishareing.repository.entity.tbCoreEventVisithistory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface CoreEventVisithistoryRepository extends BaseRepository<tbCoreEventVisithistory, Integer> {
    tbCoreEventVisithistory findFirstByEventIdAndUserIdOrderByVisitTimeDesc(Integer eventId, Integer userId);

    @Modifying
    void deleteAllByUserId(Integer userId);
}