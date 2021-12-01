package com.fih.ishareing.service.banner;

import com.fih.ishareing.constant.ApiErrorConstant;
import com.fih.ishareing.controller.base.error.ApiErrorResponse;
import com.fih.ishareing.controller.base.vo.result.ApiBatchResult;
import com.fih.ishareing.errorHandling.ErrorHandlingConstants.ERROR_BUNDLE;
import com.fih.ishareing.errorHandling.exceptions.BannerNotFoundException;
import com.fih.ishareing.repository.BannerRepository;
import com.fih.ishareing.repository.entity.tbCoreBanner;
import com.fih.ishareing.service.AbstractService;
import com.fih.ishareing.service.banner.model.BannerAddVO;
import com.fih.ishareing.service.banner.model.BannerUpdateVO;
import com.fih.ishareing.service.banner.model.BannerVO;
import com.fih.ishareing.service.resource.ResourceService;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BannerServiceImpl extends AbstractService implements BannerService {
    private static Logger logger = LoggerFactory.getLogger(BannerServiceImpl.class);
    @Autowired
    private BannerRepository bannerRep;

	@Autowired
	ResourceService resourceService;
	
    @Override
    public long count(Specification<tbCoreBanner> spec) {
        return bannerRep.count(spec);
    }

    @Override
    public boolean exist(String account) {
        return false;
    }

    @Override
    public boolean exist(Integer id) {
        // TODO Auto-generated method stub
    	Long lId = Long.parseLong(id.toString());

    	return bannerRep.existsById(lId);
    }

    @Override
    public List<BannerVO> findBanners(Specification<tbCoreBanner> spec, Pageable pageable) {
        return bannerRep.findAll(spec, pageable).stream().map(this::transfer).collect(Collectors.toList());
    }
    @Transactional
    @Override
    public List<?> addBanners(List<BannerAddVO> banners) {
        ApiBatchResult result = new ApiBatchResult();
        for (int i = 0; i < banners.size(); i++) {
        	tbCoreBanner tbCoreBannerSaved = null;
        	BannerAddVO banner = banners.get(i);
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            builder.put(ApiErrorConstant.INDEX, i);

            if (StringUtils.isBlank(banner.getUrl())) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.invalidRequest.name(), "Url must not be null or emtpy"));
            }
            if (banner.getDisplaySort() == null) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.invalidRequest.name(), "Display must not be null or emtpy"));
            }

            if (CollectionUtils.isEmpty(errors)) {
                try {
                	
                	tbCoreBanner tbCoreBannerObj = new tbCoreBanner();
                	tbCoreBannerObj.setUrl(banner.getUrl());
                	tbCoreBannerObj.setDisplaySort(banner.getDisplaySort());

                	tbCoreBannerSaved = bannerRep.save(tbCoreBannerObj);
                	
                    builder.put(ApiErrorConstant.ID, tbCoreBannerSaved.getId());
                } catch (Exception ex) {
                    logger.error("An error occurred while add new banner, url:{}, error:{}", banner.getUrl(),
                            ex.getMessage());
                    errors.add(new ApiErrorResponse(ERROR_BUNDLE.internalError));
                    builder.put(ApiErrorConstant.ERRORS, errors);
                }
            } else {
                builder.put(ApiErrorConstant.ERRORS, errors);
            }

            result.add(builder.build());
        }

        return result.build();
    }
    @Transactional
    @Override
    public List<?> updateBanners(List<BannerUpdateVO> banners) {
        ApiBatchResult result = new ApiBatchResult();
        for (int i = 0; i < banners.size(); i++) {
        	tbCoreBanner tbCoreBannerSaved = null;
        	BannerUpdateVO banner = banners.get(i);
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            builder.put(ApiErrorConstant.INDEX, i);
            builder.put(ApiErrorConstant.ID, banner.getId());

            if (CollectionUtils.isEmpty(errors)) {
                try {
                	Long lId = Long.parseLong(banner.getId().toString());
                	tbCoreBanner tbCoreBannerObj = bannerRep.findByid(lId);
                    if (tbCoreBannerObj == null) {
                        errors.add(new ApiErrorResponse(ERROR_BUNDLE.bannerNotFound));
                        builder.put(ApiErrorConstant.ERRORS, errors);
                    }

                    if (errors.size() == 0) {
                        if (banner.getUrl() != null) {
                            tbCoreBannerObj.setUrl(banner.getUrl());
                        }
                        if (banner.getDisplaySort() != null) {
                            tbCoreBannerObj.setDisplaySort(banner.getDisplaySort());
                        }
                        tbCoreBannerSaved = bannerRep.save(tbCoreBannerObj);
                    }
                } catch (Exception ex) {
                    logger.error("An error occurred while updating banner, id:{}, url:{}, error:{}", banner.getId(), banner.getUrl(),
                            ex.getMessage());
                    errors.add(new ApiErrorResponse(ERROR_BUNDLE.internalError));
                    builder.put(ApiErrorConstant.ERRORS, errors);
                }
            } else {
                builder.put(ApiErrorConstant.ERRORS, errors);
            }

            result.add(builder.build());
        }

        return result.build();
    }
    @Transactional
    @Override
    public List<?> deleteBanners(List<String> banners) {
        ApiBatchResult result = new ApiBatchResult();

        for (int i = 0; i < banners.size(); i++) {
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            String BannerId = banners.get(i);
        	Long lId = Long.parseLong(BannerId);
            Integer nBannerID = Integer.parseInt(BannerId);
            builder.put(ApiErrorConstant.ID, nBannerID);
            try {
                bannerRep.deleteByid(lId);
            } catch (Exception ex) {
                logger.error("An error occurred while deleting banner, id:{}, error:{}", nBannerID, ex.getMessage());
                errors.add(new ApiErrorResponse(ERROR_BUNDLE.internalError));
                builder.put(ApiErrorConstant.ERRORS, errors);
            }

            result.add(builder.build());
        }

        return result.build();
    }

    private BannerVO transfer(tbCoreBanner banner) {
        BannerVO instance = new BannerVO();
        instance.setId(banner.getId().intValue());
        instance.setUrl(banner.getUrl());
        instance.setImage(strHTTPServerHost + banner.getImage());
        instance.setDisplaySort(banner.getDisplaySort());
        if (banner.getSmallImage() != null) {
            instance.setSmallImage(strHTTPServerHost + banner.getSmallImage());
        }

        return instance;
    }
    @Transactional
	@Override
	public void updateBannerImage(Integer id, String image) {
		// TODO Auto-generated method stub
    	Long lId = Long.parseLong(id.toString());
		tbCoreBanner tbCoreBannerObj = bannerRep.findByid(lId);

        if (tbCoreBannerObj != null) {
        	tbCoreBannerObj.setImage(image);

        	bannerRep.save(tbCoreBannerObj);
        } else {
            throw new BannerNotFoundException();
        }

	}
    @Transactional
	@Override
	public void removeBannerImage(Integer id) {
		// TODO Auto-generated method stub
    	Long lId = Long.parseLong(id.toString());
		tbCoreBanner tbCoreBannerObj = bannerRep.findByid(lId);

        if (tbCoreBannerObj != null && tbCoreBannerObj.getImage() != null &&
                !tbCoreBannerObj.getImage().isEmpty()) {
            resourceService.imageRemove(tbCoreBannerObj.getImage());

            tbCoreBannerObj.setImage("");

            bannerRep.save(tbCoreBannerObj);
        }		
	}

    @Transactional
    @Override
    public void updateBannerSmallImage(Integer id, String image) {
        // TODO Auto-generated method stub
        Long lId = Long.parseLong(id.toString());
        tbCoreBanner tbCoreBannerObj = bannerRep.findByid(lId);

        if (tbCoreBannerObj != null) {
            tbCoreBannerObj.setSmallImage(image);

            bannerRep.save(tbCoreBannerObj);
        } else {
            throw new BannerNotFoundException();
        }

    }
    @Transactional
    @Override
    public void removeBannerSmallImage(Integer id) {
        // TODO Auto-generated method stub
        Long lId = Long.parseLong(id.toString());
        tbCoreBanner tbCoreBannerObj = bannerRep.findByid(lId);

        if (tbCoreBannerObj != null && tbCoreBannerObj.getImage() != null &&
                !tbCoreBannerObj.getImage().isEmpty()) {
            resourceService.imageRemove(tbCoreBannerObj.getImage());

            tbCoreBannerObj.setSmallImage("");

            bannerRep.save(tbCoreBannerObj);
        }
    }
}