package com.fih.ishareing.repository;

import com.fih.ishareing.repository.entity.tbCoreBanner;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface BannerRepository extends BaseRepository<tbCoreBanner, Integer> {
	
	boolean existsById(Long id);
	
	tbCoreBanner findByid(Long id);
	
	@Transactional			 
    @Modifying
	void deleteByid(Long id);
	
}
