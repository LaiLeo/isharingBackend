package com.fih.ishareing.repository;

import com.fih.ishareing.repository.entity.tbCoreUserLicenseImage;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface CoreUserLicenseImageRepository extends BaseRepository<tbCoreUserLicenseImage, Integer> {
	
	tbCoreUserLicenseImage findByid(Integer id);
	
	tbCoreUserLicenseImage findByUserId(Integer userid);

	@Modifying
	void deleteAllByUserId(Integer userId);
}