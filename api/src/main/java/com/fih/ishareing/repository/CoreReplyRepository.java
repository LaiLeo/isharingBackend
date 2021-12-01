package com.fih.ishareing.repository;

import com.fih.ishareing.repository.entity.tbReply;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface CoreReplyRepository extends BaseRepository<tbReply, Integer> {

    @Modifying
    void deleteAllByUserAccountId(Integer userId);
}