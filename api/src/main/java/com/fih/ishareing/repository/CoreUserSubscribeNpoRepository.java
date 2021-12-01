package com.fih.ishareing.repository;

import com.fih.ishareing.repository.entity.tbCoreUserSubscribedNpo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface CoreUserSubscribeNpoRepository extends BaseRepository<tbCoreUserSubscribedNpo, Integer> {
	Integer countByUserIdAndSubscribedNpoId(Integer userId, Integer subscribedNpoId);

	@Modifying
	void deleteByUserIdAndSubscribedNpoId(Integer userId, Integer subscribedNpoId);

	@Modifying
	void deleteAllByUserId(Integer userId);
}