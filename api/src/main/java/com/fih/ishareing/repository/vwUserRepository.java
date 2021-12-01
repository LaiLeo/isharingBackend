package com.fih.ishareing.repository;

import com.fih.ishareing.repository.entity.vwUsers;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface vwUserRepository extends BaseRepository<vwUsers, Integer> {
	
	vwUsers findByUsername(String username);

    vwUsers findByUsernameAndIsActive(String username, Integer isActive);

    vwUsers findByIdAndIsActive(Integer id, Integer isActive);

    List<vwUsers> findAllByIsActiveOrderByUsername(Integer isActive);

    Integer countById(Integer id);
}