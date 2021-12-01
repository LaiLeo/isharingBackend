package com.fih.ishareing.repository;

import com.fih.ishareing.repository.entity.tbCoreUserThird;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface CoreUserThirdRepository extends BaseRepository<tbCoreUserThird, Integer> {
    Integer countByUserIdAndEnterpriseSerialCodeAndEnterpriseSerialEmail(Integer userId, String code, String email);
    Integer countByEnterpriseSerialCodeAndEnterpriseSerialEmail(String code, String email);
    Integer countByUserIdAndEnterpriseSerialCode(Integer userId, String code);

    tbCoreUserThird findByEnterpriseSerialCodeAndEnterpriseSerialEmail(String code, String email);
    tbCoreUserThird findByEnterpriseSerialCodeAndEnterpriseSerialNumber(String code, String number);

    tbCoreUserThird findByUserIdAndEnterpriseSerialCodeAndEnterpriseSerialNumber(Integer userId, String code, String number);
    tbCoreUserThird findByUserIdAndEnterpriseSerialCode(Integer userId, String code);

    @Modifying
    void deleteByUserIdAndEnterpriseSerialCode(Integer userId, String code);

    @Modifying
    void deleteAllByUserId(Integer userId);
}