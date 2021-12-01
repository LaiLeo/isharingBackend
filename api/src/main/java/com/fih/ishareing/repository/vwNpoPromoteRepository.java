package com.fih.ishareing.repository;

import com.fih.ishareing.repository.entity.vwNpoPromote;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface vwNpoPromoteRepository extends BaseRepository<vwNpoPromote, Integer> {
    @Override
    List<vwNpoPromote> findAll();
}