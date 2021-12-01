package com.fih.ishareing.service.banner;

import com.fih.ishareing.repository.entity.tbCoreBanner;
import com.fih.ishareing.service.banner.model.BannerAddVO;
import com.fih.ishareing.service.banner.model.BannerUpdateVO;
import com.fih.ishareing.service.banner.model.BannerVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface BannerService {
    long count(Specification<tbCoreBanner> spec);

    boolean exist(String account);

    boolean exist(Integer id);

    List<BannerVO> findBanners(Specification<tbCoreBanner> spec, Pageable pageable);

    List<?> addBanners(List<BannerAddVO> banners);

    List<?> updateBanners(List<BannerUpdateVO> banners);

    List<?> deleteBanners(List<String> banners);
    
    void updateBannerImage(Integer id, String image);
    
    void removeBannerImage(Integer id);

    void updateBannerSmallImage(Integer id, String smallImage);

    void removeBannerSmallImage(Integer id);
}