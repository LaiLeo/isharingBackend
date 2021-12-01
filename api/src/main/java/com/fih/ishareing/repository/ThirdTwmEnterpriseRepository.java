package com.fih.ishareing.repository;

import com.fih.ishareing.repository.entity.tbThirdTwmEnterprise;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ThirdTwmEnterpriseRepository extends BaseRepository<tbThirdTwmEnterprise, Integer> {
    Integer countByEnterpriseSerialNumberAndEnterpriseSerialEmailAndEnterpriseSerialName(String number, String email, String name);

    @Modifying
    @Query(nativeQuery = true, value = "Truncate table third_twmEnterprise")
    Integer truncateThirdTwmEnterpriseTable();
}