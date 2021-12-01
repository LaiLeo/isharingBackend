package com.fih.ishareing.service.donation;

import com.fih.ishareing.constant.ApiErrorConstant;
import com.fih.ishareing.controller.base.error.ApiErrorResponse;
import com.fih.ishareing.controller.base.vo.result.ApiBatchResult;
import com.fih.ishareing.errorHandling.ErrorHandlingConstants.ERROR_BUNDLE;
import com.fih.ishareing.errorHandling.exceptions.UserNotFoundException;
import com.fih.ishareing.repository.CoreDonationNpoRepository;
import com.fih.ishareing.repository.entity.tbCoreDonationNpo;
import com.fih.ishareing.repository.entity.tbCoreUserAccount;
import com.fih.ishareing.service.AbstractService;
import com.fih.ishareing.service.donation.model.DonationNposAddVO;
import com.fih.ishareing.service.donation.model.DonationNposListRespVO;
import com.fih.ishareing.service.donation.model.DonationNposUpdateVO;
import com.fih.ishareing.service.resource.ResourceService;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.collections4.CollectionUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DonationServiceImpl extends AbstractService implements DonationService {
    private static Logger logger = LoggerFactory.getLogger(DonationServiceImpl.class);

    @Autowired
    private CoreDonationNpoRepository coreDonationNpoRepo;

    @Autowired
    private ResourceService resourceService;

    @Override
    public boolean exist(Integer id) {
        return coreDonationNpoRepo.existsById(id);
    }

    @Override
    public JSONObject listDonationNpos(Specification<tbCoreDonationNpo> QuerySpec, Pageable QueryPageable) {
        JSONObject JOQueryResult = new JSONObject();
        List<DonationNposListRespVO> listDonationNposList = new ArrayList<DonationNposListRespVO>();

        List<tbCoreDonationNpo> listTBCoreDonationNpos = coreDonationNpoRepo.findAll(QuerySpec, QueryPageable).getContent();
        for (tbCoreDonationNpo tbCoreDonationNpos : listTBCoreDonationNpos) {
            DonationNposListRespVO DonationNposListRespVOObj = new DonationNposListRespVO();

            DonationNposListRespVOObj.setId(tbCoreDonationNpos.getId());
            DonationNposListRespVOObj.setName(tbCoreDonationNpos.getName());
            DonationNposListRespVOObj.setDescription(tbCoreDonationNpos.getDescription());
            DonationNposListRespVOObj.setCode(tbCoreDonationNpos.getCode());
            DonationNposListRespVOObj.setNewebpayUrl(tbCoreDonationNpos.getNewebpayUrl());
            if (tbCoreDonationNpos.getNpoIcon() != null) {
                DonationNposListRespVOObj.setNpoIcon(strHTTPServerHost + tbCoreDonationNpos.getNpoIcon());
            }
            if (tbCoreDonationNpos.getThumbPath() != null) {
                DonationNposListRespVOObj.setThumbPath(strHTTPServerHost + tbCoreDonationNpos.getThumbPath());
            }
            DonationNposListRespVOObj.setNewebpayPeriodUrl(tbCoreDonationNpos.getNewebpayPeriodUrl());
            DonationNposListRespVOObj.setDisplaySort(tbCoreDonationNpos.getDisplaySort());

            listDonationNposList.add(DonationNposListRespVOObj);
        }

        JOQueryResult.put("Count", coreDonationNpoRepo.count(QuerySpec));
        JOQueryResult.put("Results", listDonationNposList);

        return JOQueryResult;
    }

    @Transactional
    @Override
    public List<?> addDonationNpos(List<DonationNposAddVO> nposAddVOList) {
        ApiBatchResult result = new ApiBatchResult();
        for (int i = 0; i < nposAddVOList.size(); i++) {
            tbCoreDonationNpo tbCoreDonationNpoSaved = new tbCoreDonationNpo();
            DonationNposAddVO npo = nposAddVOList.get(i);
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            builder.put(ApiErrorConstant.INDEX, i);

            if (CollectionUtils.isEmpty(errors)) {
                try {
                    tbCoreDonationNpoSaved.setName(npo.getName());
                    tbCoreDonationNpoSaved.setDescription(npo.getDescription());
                    tbCoreDonationNpoSaved.setCode(npo.getCode());
                    tbCoreDonationNpoSaved.setNewebpayUrl(npo.getNewebpayUrl());
                    tbCoreDonationNpoSaved.setNewebpayPeriodUrl(npo.getNewebpayPeriodUrl());
                    tbCoreDonationNpoSaved.setDisplaySort(npo.getDisplaySort());
                    coreDonationNpoRepo.save(tbCoreDonationNpoSaved);

                    builder.put(ApiErrorConstant.ID, tbCoreDonationNpoSaved.getId());
                } catch (Exception ex) {
                    logger.error("An error occurred while add new donationNpo, index:{}, error:{}", i,
                            ex.getMessage());
                    errors.add(new ApiErrorResponse(ERROR_BUNDLE.internalError));
                    builder.put(ApiErrorConstant.ERRORS, errors);
                }
            } else {
                builder.put(ApiErrorConstant.ERRORS, errors);
//                builder.put(ApiErrorConstant.ID, 0);
            }

            result.add(builder.build());
        }

        return result.build();
    }
    @Transactional
    @Override
    public List<?> updateDonationNpos(List<DonationNposUpdateVO> nposUpdateVOList) {
        ApiBatchResult result = new ApiBatchResult();
        for (int i = 0; i < nposUpdateVOList.size(); i++) {
            DonationNposUpdateVO npo = nposUpdateVOList.get(i);
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            builder.put(ApiErrorConstant.INDEX, i);
            tbCoreDonationNpo tbCoreDonationNpo = coreDonationNpoRepo.findByid(npo.getId());
            if (tbCoreDonationNpo == null) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.npoNotFound.name(), ERROR_BUNDLE.npoNotFound.getDesc()));
            }

            if (CollectionUtils.isEmpty(errors)) {
                try {
                    if (npo.getName() != null) {
                        tbCoreDonationNpo.setName(npo.getName());
                    }

                    if (npo.getDescription() != null) {
                        tbCoreDonationNpo.setDescription(npo.getDescription());
                    }
                    if (npo.getCode() != null) {
                        tbCoreDonationNpo.setCode(npo.getCode());
                    }
                    if (npo.getNewebpayUrl() != null) {
                        tbCoreDonationNpo.setNewebpayUrl(npo.getNewebpayUrl());
                    }
                    if (npo.getNewebpayPeriodUrl() != null) {
                        tbCoreDonationNpo.setNewebpayPeriodUrl(npo.getNewebpayPeriodUrl());
                    }
                    if (npo.getDisplaySort() != null) {
                        tbCoreDonationNpo.setDisplaySort(npo.getDisplaySort());
                    }

                    coreDonationNpoRepo.save(tbCoreDonationNpo);

                    builder.put(ApiErrorConstant.ID, npo.getId());
                } catch (Exception ex) {
                    logger.error("An error occurred while update donationNpo, index:{}, error:{}", i,
                            ex.getMessage());
                    errors.add(new ApiErrorResponse(ERROR_BUNDLE.internalError));
                    builder.put(ApiErrorConstant.ERRORS, errors);
                }
            } else {
                builder.put(ApiErrorConstant.ERRORS, errors);
                builder.put(ApiErrorConstant.ID, npo.getId());
            }

            result.add(builder.build());
        }

        return result.build();
    }
    @Transactional
    @Override
    public List<?> deleteDonationNpos(List<String> nposList) {
        ApiBatchResult result = new ApiBatchResult();

        for (int i = 0; i < nposList.size(); i++) {
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            Integer npoId = Integer.valueOf(nposList.get(i));
            builder.put(ApiErrorConstant.ID, npoId);
            tbCoreDonationNpo tbCoreDonationNpo = coreDonationNpoRepo.findByid(npoId);
            if (tbCoreDonationNpo == null) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.npoNotFound.name(), ERROR_BUNDLE.npoNotFound.getDesc()));
            }
            if (CollectionUtils.isEmpty(errors)) {
                try {
                    coreDonationNpoRepo.deleteById(npoId);
                } catch (Exception ex) {
                    logger.error("An error occurred while deleting donationNpo, id:{}, error:{}", npoId, ex.getMessage());
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
    public void updateDonationIcon(Integer npoId, String icon) {
        tbCoreDonationNpo tbCoreDonationNpo = coreDonationNpoRepo.findByid(npoId);
        if (tbCoreDonationNpo == null) {
            new ApiErrorResponse(ERROR_BUNDLE.npoNotFound.name(), ERROR_BUNDLE.npoNotFound.getDesc());
        } else {
            tbCoreDonationNpo.setNpoIcon(icon);
//            tbCoreDonationNpo.setThumbPath(icon);

            coreDonationNpoRepo.save(tbCoreDonationNpo);
        }
    }

    @Transactional
    @Override
    public void removeDonationIcon(Integer npoId) {
        tbCoreDonationNpo tbCoreDonationNpo = coreDonationNpoRepo.findByid(npoId);
        if (tbCoreDonationNpo == null) {
            new ApiErrorResponse(ERROR_BUNDLE.npoNotFound.name(), ERROR_BUNDLE.npoNotFound.getDesc());
        } else {
            resourceService.imageRemove(tbCoreDonationNpo.getNpoIcon());

            tbCoreDonationNpo.setNpoIcon("");
            coreDonationNpoRepo.save(tbCoreDonationNpo);
        }
    }
}