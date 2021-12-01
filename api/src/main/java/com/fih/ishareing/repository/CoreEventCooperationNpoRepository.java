package com.fih.ishareing.repository;

import com.fih.ishareing.repository.entity.tbCoreEventCooperationNpo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface CoreEventCooperationNpoRepository extends BaseRepository<tbCoreEventCooperationNpo, Integer> {

    @Modifying
    void deleteAllByEventId(Integer eventId);
}