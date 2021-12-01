package com.fih.ishareing.repository;

import com.fih.ishareing.repository.entity.tbCoreEvent;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoreEventRepository extends BaseRepository<tbCoreEvent, Integer> {
    tbCoreEvent findByUid(String uid);

    List<tbCoreEvent> findAllByTagsIsNot(String tags);
}