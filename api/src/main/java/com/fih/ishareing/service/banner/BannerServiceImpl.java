package com.fih.ishareing.service.banner;

import com.fih.ishareing.repository.BannerRepository;
import com.fih.ishareing.repository.entity.tbBanner;
import com.fih.ishareing.service.AbstractAuthenticationService;
import com.fih.ishareing.service.banner.model.BannerAddVO;
import com.fih.ishareing.service.banner.model.BannerUpdateVO;
import com.fih.ishareing.service.banner.model.BannerVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BannerServiceImpl extends AbstractAuthenticationService implements BannerService {
    private static Logger logger = LoggerFactory.getLogger(BannerServiceImpl.class);
    @Autowired
    private BannerRepository bannerRep;

    @Override
    public long count(Specification<tbBanner> spec) {
        return bannerRep.count(spec);
    }

    @Override
    public boolean exist(String account) {
        return false;
    }

    @Override
    public List<BannerVO> findBanners(Specification<tbBanner> spec, Pageable pageable) {
        return bannerRep.findAll(spec, pageable).stream().map(this::transfer).collect(Collectors.toList());
    }

    @Override
    public List<?> addBanners(List<BannerAddVO> banners) {
        return null;
    }

    @Override
    public List<?> updateBanners(List<BannerUpdateVO> banners) {
        return null;
    }

    @Override
    public List<?> deleteBanners(List<String> banners) {
        return null;
    }

    private BannerVO transfer(tbBanner banner) {
        BannerVO instance = new BannerVO();
        instance.setId(banner.getId().intValue());
        instance.setUrl(banner.getUrl());
        instance.setImage(banner.getImage());

        return instance;
    }
}