package com.fih.ishareing.repository;

import com.fih.ishareing.repository.entity.tbCoreUserFocusedEvent;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface CoreUserFocusedEventRepository extends BaseRepository<tbCoreUserFocusedEvent, Integer> {
    int countByUserIdAndFocusedEventId(Integer userId, Integer eventId);
    @Modifying
    void deleteAllByUserId(Integer userId);

    @Modifying
    void deleteByUserIdAndFocusedEventId(Integer userId, Integer eventId);
}