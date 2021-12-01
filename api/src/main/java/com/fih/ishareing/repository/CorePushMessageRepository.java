package com.fih.ishareing.repository;

import com.fih.ishareing.repository.entity.tbPushMessage;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface CorePushMessageRepository extends BaseRepository<tbPushMessage, Integer> {

    @Modifying
    void deleteAllByUserId(Integer userId);
}