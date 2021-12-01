package com.fih.ishareing.repository;

import com.fih.ishareing.repository.entity.tbCoreDonationNpo;
import org.springframework.stereotype.Repository;

@Repository
public interface CoreDonationNpoRepository extends BaseRepository<tbCoreDonationNpo, Integer> {
	
	tbCoreDonationNpo findByid(Integer id);
}