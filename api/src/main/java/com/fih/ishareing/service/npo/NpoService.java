package com.fih.ishareing.service.npo;

import com.fih.ishareing.repository.entity.tbCoreNpo;
import com.fih.ishareing.repository.entity.vwNpoPromote;
import com.fih.ishareing.service.npo.model.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface NpoService {
    long count(Specification<tbCoreNpo> spec);

    List<NpoVO> findNpos(Specification<tbCoreNpo> spec, Pageable pageable);

    List<?> addNpos(List<NposAddVO> npos);

    List<?> updateNpos(List<NposUpdateVO> npos);

    List<?> deleteNpos(List<String> npoIds);

    boolean exist(Integer id);

    List<?> subscribeNpos(List<NpoSubscribeVO> npoIds);

    List<?> unsubscribeNpos(List<String> npoIds);

    void updateNpoIcon(Integer npoId, String icon);
    void updateNpoVerifiedImg(Integer npoId, String verifiedImg);
    void updateNpoRegisterImg(Integer npoId, String registerImg);

    void removeNpoIcon(Integer npoId);
    void removeNpoVerifiedImg(Integer npoId);
    void removeNpoRegisterImg(Integer npoId);

    List<NpoPromoteVO> findPromoteNpos();

    long countPromoteNpos(Specification<vwNpoPromote> spec);

    List<?> getNposMenu(String contactEmail);
}