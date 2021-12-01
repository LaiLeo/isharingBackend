package com.fih.ishareing.repository;

import com.fih.ishareing.repository.entity.tbCoreUserAccount;
import net.bytebuddy.description.field.FieldDescription;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CoreUserAccountRepository extends BaseRepository<tbCoreUserAccount, Integer> {
	
	tbCoreUserAccount findByUserId(Integer id);
	
    @Modifying
	@Transactional
	@Query(nativeQuery = true, value = "DELETE FROM core_useraccount WHERE user_id=?1 ")
	int deleteAllByID(Integer id);

	Integer countByUserId(Integer id);

	List<tbCoreUserAccount> findAllByScoreGreaterThan(Integer score);

	List<tbCoreUserAccount> findAllByScoreGreaterThanOrderByScoreDesc(Integer score);

	@Modifying
	@Query(nativeQuery = true, value = "UPDATE core_useraccount set ranking = ?1 WHERE score=?2")
	Integer updateRankByScore(Integer ranking, Integer score);
}