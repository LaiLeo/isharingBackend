package com.fih.ishareing.repository;

import com.fih.ishareing.repository.entity.vwUsersMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface vwUserMenuRepository extends BaseRepository<vwUsersMenu, Integer> {

    List<vwUsersMenu> findAllByIsActiveOrderByUsername(Integer isActive);
}