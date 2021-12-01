package com.fih.ishareing.repository;

import com.fih.ishareing.repository.entity.AuthToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends BaseRepository<AuthToken, Integer> {
	@Modifying
	void deleteAllByUserId(Integer userId);

	AuthToken findByKey(String key);

}