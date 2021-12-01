package com.fih.ishareing.repository;

import com.fih.ishareing.repository.entity.tbCoreNpo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface CoreNpoRepository extends BaseRepository<tbCoreNpo, Integer> {
    @Modifying
    void deleteAllByUserId(Integer userId);

    Integer countByUserId(Integer userId);

    Integer countByName(String name);

    Integer countByNameAndIdIsNot(String name, Integer npoId);

    tbCoreNpo findByUserId(Integer userId);
}