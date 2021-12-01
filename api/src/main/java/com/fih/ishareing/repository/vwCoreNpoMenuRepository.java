package com.fih.ishareing.repository;

import com.fih.ishareing.repository.entity.vwCoreNpoMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface vwCoreNpoMenuRepository extends BaseRepository<vwCoreNpoMenu, Integer> {

    List<vwCoreNpoMenu> findAllByVerifiedAndAdmViewed(Boolean verified, Boolean admViewed);

    List<vwCoreNpoMenu> findAllByVerifiedAndAdmViewedAndContactEmail(Boolean verified, Boolean admViewed, String contactEmail);

    Integer countByAdmViewedAndVerified(Boolean verified, Boolean admViewed);
}