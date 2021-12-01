package com.fih.ishareing.repository;

import com.fih.ishareing.repository.entity.tbThirdTwmEnterpriseInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface ThirdTwmEnterpriseInfoRepository extends BaseRepository<tbThirdTwmEnterpriseInfo, Integer> {
    @Modifying
    int deleteByEnterpriseSerialNumber(String number);
}