package com.fih.ishareing.service.banner;

import com.fih.ishareing.repository.entity.tbBanner;
import com.fih.ishareing.service.banner.model.BannerAddVO;
import com.fih.ishareing.service.banner.model.BannerUpdateVO;
import com.fih.ishareing.service.banner.model.BannerVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface BannerService {
    long count(Specification<tbBanner> spec);

    boolean exist(String account);

    List<BannerVO> findBanners(Specification<tbBanner> spec, Pageable pageable);

    List<?> addBanners(List<BannerAddVO> banners);

    List<?> updateBanners(List<BannerUpdateVO> banners);

    List<?> deleteBanners(List<String> banners);
}