package com.fih.ishareing.repository;

import com.fih.ishareing.repository.entity.tbCoreUserRegisteredEvent;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoreUserRegisteredEventRepository extends BaseRepository<tbCoreUserRegisteredEvent, Integer> {
	tbCoreUserRegisteredEvent findByUserIdAndRegisteredEventId(Integer userId, Integer eventId);

	List<tbCoreUserRegisteredEvent> findAllByEventSkillGroupId(Integer eventSkillGroupId);

	@Modifying
	void deleteAllByUserId(Integer userId);

	@Modifying
	void deleteByUserIdAndRegisteredEventId(Integer userId, Integer eventId);
}