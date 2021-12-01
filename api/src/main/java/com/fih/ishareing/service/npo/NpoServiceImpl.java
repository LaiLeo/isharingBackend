package com.fih.ishareing.service.npo;

import com.fih.ishareing.constant.ApiErrorConstant;
import com.fih.ishareing.controller.base.error.ApiErrorResponse;
import com.fih.ishareing.controller.base.vo.result.ApiBatchResult;
import com.fih.ishareing.errorHandling.ErrorHandlingConstants.ERROR_BUNDLE;
import com.fih.ishareing.repository.CoreUserSubscribeNpoRepository;
import com.fih.ishareing.repository.CoreNpoRepository;
import com.fih.ishareing.repository.entity.tbCoreNpo;
import com.fih.ishareing.repository.entity.tbCoreUserSubscribedNpo;
import com.fih.ishareing.repository.entity.vwCoreNpoMenu;
import com.fih.ishareing.repository.entity.vwNpoPromote;
import com.fih.ishareing.repository.vwCoreNpoMenuRepository;
import com.fih.ishareing.repository.vwNpoPromoteRepository;
import com.fih.ishareing.service.AbstractService;
import com.fih.ishareing.service.npo.model.*;
import com.fih.ishareing.service.resource.ResourceService;
import com.fih.ishareing.utils.MailUtils;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NpoServiceImpl extends AbstractService implements NpoService {
    private static Logger logger = LoggerFactory.getLogger(NpoServiceImpl.class);
    @Autowired
    private CoreNpoRepository npoRep;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private CoreUserSubscribeNpoRepository subscribeNpoRep;
    @Autowired
    private vwNpoPromoteRepository npoPromoteRep;
    @Autowired
    private MailUtils mailUtils;
    @Value("${npoAdminEmail}")
    protected String npoAdminEmail;
    @Autowired
    private vwCoreNpoMenuRepository npoMenuRep;

    @Override
    public long count(Specification<tbCoreNpo> spec) {
        return npoRep.count(spec);
    }

    @Override
    public long countPromoteNpos(Specification<vwNpoPromote> spec) {
        return npoPromoteRep.count(spec);
    }

    @Override
    public List<NpoVO> findNpos(Specification<tbCoreNpo> spec, Pageable pageable) {
        return npoRep.findAll(spec, pageable).stream().map(this::transfer).collect(Collectors.toList());
    }

    @Override
    public List<NpoPromoteVO> findPromoteNpos() {
        List<NpoPromoteVO> npos = new ArrayList<>();
        Set<Integer> npoIds = new HashSet<>();
        List<vwNpoPromote> vwNpoPromotes = npoPromoteRep.findAll();

        for (int i = 0; i < vwNpoPromotes.size(); i++) {
            if (npos.size() == 3) {
                break;
            }
            if (npoIds.contains(vwNpoPromotes.get(i).getId())) {
                continue;
            }
            npos.add(this.transferPromote(vwNpoPromotes.get(i)));
            npoIds.add(vwNpoPromotes.get(i).getId());
        }

        return npos;
    }

    @Transactional
    @Override
    public List<?> addNpos(List<NposAddVO> npos) {
        ApiBatchResult result = new ApiBatchResult();
        for (int i = 0; i < npos.size(); i++) {
            tbCoreNpo tbCoreNpo = null;
            NposAddVO npo = npos.get(i);
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            builder.put(ApiErrorConstant.INDEX, i);

            tbCoreNpo = npoRep.findByUserId(npo.getUserId());
            if (tbCoreNpo != null) {
                if (!tbCoreNpo.getEnterprise()) {
                    errors.add(
                            new ApiErrorResponse(ERROR_BUNDLE.userNpoExists.name(), ERROR_BUNDLE.userNpoExists.getDesc()));
                } else {
                    errors.add(
                            new ApiErrorResponse(ERROR_BUNDLE.npoEnterpriseNameExists.name(), ERROR_BUNDLE.npoEnterpriseNameExists.getDesc()));
                }
            }

            if (npoRep.countByName(npo.getName()) > 0) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.npoNameExists.name(), ERROR_BUNDLE.npoNameExists.getDesc()));
            }

            if (CollectionUtils.isEmpty(errors)) {
                try {
                    tbCoreNpo = new tbCoreNpo();
                    tbCoreNpo.setUserId(npo.getUserId());
                    tbCoreNpo.setName(npo.getName());
                    tbCoreNpo.setUid(UUID.randomUUID().toString());
                    tbCoreNpo.setNpoIcon("default_event_image.png");
                    tbCoreNpo.setDescription(npo.getDescription());
                    tbCoreNpo.setRegisterNumber(npo.getRegisterNumber());
                    tbCoreNpo.setSerialNumber(npo.getSerialNumber());
                    tbCoreNpo.setVerifiedImage("default_event_image.png");
                    tbCoreNpo.setServiceTarget(npo.getServiceTarget());
                    tbCoreNpo.setGovernmentRegisterImage("default_event_image.png");
                    tbCoreNpo.setPubDate(new Timestamp(System.currentTimeMillis()));
                    tbCoreNpo.setContactName(npo.getContactName());
                    tbCoreNpo.setContactPhone(npo.getContactPhone());
                    tbCoreNpo.setContactEmail(npo.getContactEmail());
                    tbCoreNpo.setContactJob(npo.getContactJob());
                    tbCoreNpo.setContact2Name(npo.getContact2Name());
                    tbCoreNpo.setContact2Phone(npo.getContact2Phone());
                    tbCoreNpo.setContact2Email(npo.getContact2Email());
                    tbCoreNpo.setContact2Job(npo.getContact2Job());
                    tbCoreNpo.setContactAddress(npo.getContactAddress());
                    tbCoreNpo.setContactWebsite(npo.getContactWebsite());
                    tbCoreNpo.setContactSite(npo.getContactSite());
                    tbCoreNpo.setVerified(false);
                    tbCoreNpo.setAdmViewed(false);
                    tbCoreNpo.setAdministratorId(npo.getAdministratorId());
                    tbCoreNpo.setThumbPath("");
                    tbCoreNpo.setRatingUserNum(0);
                    tbCoreNpo.setTotalRatingScore(0D);
                    tbCoreNpo.setSubscribedUserNum(0);
                    tbCoreNpo.setJoinedUserNum(0);
                    tbCoreNpo.setEventNum(0);
                    tbCoreNpo.setYoutubeCode(npo.getYoutubeCode());
                    tbCoreNpo.setInventory(npo.getInventory());
                    tbCoreNpo.setEnterprise(npo.getEnterprise());
                    tbCoreNpo.setPromote(npo.getPromote());

                    tbCoreNpo = npoRep.save(tbCoreNpo);

                    String content = serverHost + "/backend";
                    mailUtils.sendMail(npo.getContactEmail(), "<微樂志工iSharing> 邀請您成為夥伴", mailUtils.getAddNpoEmailTemplate(content));

                    builder.put(ApiErrorConstant.ID, tbCoreNpo.getId());
                } catch (Exception ex) {
                    logger.error("An error occurred while add new npo, index:{}, error:{}", i,
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
    public List<?> updateNpos(List<NposUpdateVO> npos) {
        ApiBatchResult result = new ApiBatchResult();
        for (int i = 0; i < npos.size(); i++) {
            NposUpdateVO npo = npos.get(i);
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            builder.put(ApiErrorConstant.INDEX, i);
            builder.put(ApiErrorConstant.ID, npo.getId());
            Optional<tbCoreNpo> tbNpoOptional = npoRep.findById(npo.getId());
            tbCoreNpo tbCoreNpo = null;
            Boolean sendFlag = false;
            if (!tbNpoOptional.isPresent()) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.npoNotFound.name(), ERROR_BUNDLE.npoNotFound.getDesc()));
            } else {
                tbCoreNpo = tbNpoOptional.get();
            }

            if (npo.getName() != null) {
                if (npoRep.countByNameAndIdIsNot(npo.getName(), npo.getId()) > 0) {
                    errors.add(
                            new ApiErrorResponse(ERROR_BUNDLE.npoNameExists.name(), ERROR_BUNDLE.npoNameExists.getDesc()));
                }
            }

            if (CollectionUtils.isEmpty(errors)) {
                try {
                    if (npo.getUserId() != null) {
                        tbCoreNpo.setUserId(npo.getUserId());
                    }
                    if (npo.getName() != null) {
                        tbCoreNpo.setName(npo.getName());
                    }
                    if (npo.getDescription() != null) {
                        tbCoreNpo.setDescription(npo.getDescription());
                    }
                    if (npo.getRegisterNumber() != null) {
                        tbCoreNpo.setRegisterNumber(npo.getRegisterNumber());
                    }
                    if (npo.getSerialNumber() != null) {
                        tbCoreNpo.setSerialNumber(npo.getSerialNumber());
                    }
                    if (npo.getServiceTarget() != null) {
                        tbCoreNpo.setServiceTarget(npo.getServiceTarget());
                    }
                    if (npo.getContactName() != null) {
                        if (tbCoreNpo.getContactName().trim().equalsIgnoreCase("")) {
                            sendFlag = true;
                        }
                        tbCoreNpo.setContactName(npo.getContactName());
                    }
                    if (npo.getContactPhone() != null) {
                        tbCoreNpo.setContactPhone(npo.getContactPhone());
                    }
                    if (npo.getContactEmail() != null) {
                        tbCoreNpo.setContactEmail(npo.getContactEmail());
                    }
                    if (npo.getContactJob() != null) {
                        tbCoreNpo.setContactJob(npo.getContactJob());
                    }
                    if (npo.getContact2Name() != null) {
                        tbCoreNpo.setContact2Name(npo.getContact2Name());
                    }
                    if (npo.getContact2Phone() != null) {
                        tbCoreNpo.setContact2Phone(npo.getContact2Phone());
                    }
                    if (npo.getContact2Email() != null) {
                        tbCoreNpo.setContact2Email(npo.getContact2Email());
                    }
                    if (npo.getContact2Job() != null) {
                        tbCoreNpo.setContact2Job(npo.getContact2Job());
                    }
                    if (npo.getContactAddress() != null) {
                        tbCoreNpo.setContactAddress(npo.getContactAddress());
                    }
                    if (npo.getContactWebsite() != null) {
                        tbCoreNpo.setContactWebsite(npo.getContactWebsite());
                    }
                    if (npo.getContactSite() != null) {
                        tbCoreNpo.setContactSite(npo.getContactSite());
                    }
                    if (npo.getVerified() != null) {
                        tbCoreNpo.setVerified(npo.getVerified());
                    }
                    if (npo.getAdmViewed() != null) {
                        tbCoreNpo.setAdmViewed(npo.getAdmViewed());
                    }
                    if (npo.getAdministratorId() != null) {
                        tbCoreNpo.setAdministratorId(npo.getAdministratorId());
                    }
                    if (npo.getYoutubeCode() != null) {
                        tbCoreNpo.setYoutubeCode(npo.getYoutubeCode());
                    }
                    if (npo.getInventory() != null) {
                        tbCoreNpo.setInventory(npo.getInventory());
                    }
                    if (npo.getEnterprise() != null) {
                        tbCoreNpo.setEnterprise(npo.getEnterprise());
                    }
                    if (npo.getPromote() != null) {
                        tbCoreNpo.setPromote(npo.getPromote());
                    }

                    npoRep.save(tbCoreNpo);

                    if (sendFlag) {
                        String content = serverHost + "/backend";
                        mailUtils.sendMail(npoAdminEmail, "微樂志工平台 新活動社團申請待審核", mailUtils.getNpoVerifiedEmailTemplate(content));
                    }
                } catch (Exception ex) {
                    logger.error("An error occurred while update donationNpo, index:{}, error:{}", i,
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
    public List<?> deleteNpos(List<String> npoIds) {
        ApiBatchResult result = new ApiBatchResult();

        for (int i = 0; i < npoIds.size(); i++) {
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            Integer npoId = Integer.valueOf(npoIds.get(i));
            builder.put(ApiErrorConstant.ID, npoId);
            Optional<tbCoreNpo> tbNpo = npoRep.findById(npoId);

            if (!tbNpo.isPresent()) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.npoNotFound.name(), ERROR_BUNDLE.npoNotFound.getDesc()));
            }
            if (CollectionUtils.isEmpty(errors)) {
                try {
                    npoRep.deleteById(npoId);
                } catch (Exception ex) {
                    logger.error("An error occurred while deleting npo, id:{}, error:{}", npoId, ex.getMessage());
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

    @Override
    public boolean exist(Integer id) {
        return npoRep.existsById(id);
    }

    @Transactional
    @Override
    public List<?> subscribeNpos(List<NpoSubscribeVO> npoIds) {
        ApiBatchResult result = new ApiBatchResult();

        for (int i = 0; i < npoIds.size(); i++) {
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            Integer npoId = Integer.valueOf(npoIds.get(i).getId());
            builder.put(ApiErrorConstant.ID, npoId);
            Optional<tbCoreNpo> tbNpoOptional = npoRep.findById(npoId);

            if (!tbNpoOptional.isPresent()) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.npoNotFound.name(), ERROR_BUNDLE.npoNotFound.getDesc()));
            }
            if (subscribeNpoRep.countByUserIdAndSubscribedNpoId(getUserId(), npoId) > 0) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.npoAlreadyBind.name(), ERROR_BUNDLE.npoAlreadyBind.getDesc()));
            }

            if (CollectionUtils.isEmpty(errors)) {
                try {
                    tbCoreNpo tbCoreNpo = tbNpoOptional.get();
                    Integer subscribedUserNum = tbCoreNpo.getSubscribedUserNum();
                    tbCoreNpo.setSubscribedUserNum(++subscribedUserNum);
                    tbCoreUserSubscribedNpo tbCoreUserSubscribedNpo = new tbCoreUserSubscribedNpo(getUserId(), npoId, new Timestamp(System.currentTimeMillis()));

                    npoRep.save(tbCoreNpo);
                    subscribeNpoRep.save(tbCoreUserSubscribedNpo);

                } catch (Exception ex) {
                    logger.error("An error occurred while subscribe npo, id:{}, error:{}", npoId, ex.getMessage());
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
    public List<?> unsubscribeNpos(List<String> npoIds) {
        ApiBatchResult result = new ApiBatchResult();

        for (int i = 0; i < npoIds.size(); i++) {
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            Integer npoId = Integer.valueOf(npoIds.get(i));
            builder.put(ApiErrorConstant.ID, npoId);
            Optional<tbCoreNpo> tbNpoOptional = npoRep.findById(npoId);

            if (!tbNpoOptional.isPresent()) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.npoNotFound.name(), ERROR_BUNDLE.npoNotFound.getDesc()));
            }
            if (subscribeNpoRep.countByUserIdAndSubscribedNpoId(getUserId(), npoId) == 0) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.npoBindNotFound.name(), ERROR_BUNDLE.npoBindNotFound.getDesc()));
            }

            if (CollectionUtils.isEmpty(errors)) {
                try {
                    tbCoreNpo tbCoreNpo = tbNpoOptional.get();
                    Integer subscribedUserNum = tbCoreNpo.getSubscribedUserNum();
                    tbCoreNpo.setSubscribedUserNum(--subscribedUserNum);

                    npoRep.save(tbCoreNpo);
                    subscribeNpoRep.deleteByUserIdAndSubscribedNpoId(getUserId(), npoId);

                } catch (Exception ex) {
                    logger.error("An error occurred while subscribe npo, id:{}, error:{}", npoId, ex.getMessage());
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
    public void updateNpoIcon(Integer npoId, String icon) {
        Optional<tbCoreNpo> tbNpoOptional = npoRep.findById(npoId);
        if (!tbNpoOptional.isPresent()) {
            new ApiErrorResponse(ERROR_BUNDLE.npoNotFound.name(), ERROR_BUNDLE.npoNotFound.getDesc());
        } else {
            tbCoreNpo tbCoreNpo = tbNpoOptional.get();
            tbCoreNpo.setNpoIcon(icon);
            tbCoreNpo.setThumbPath(icon);

            npoRep.save(tbCoreNpo);
        }
    }

    @Transactional
    @Override
    public void updateNpoVerifiedImg(Integer npoId, String verifiedImg) {
        Optional<tbCoreNpo> tbNpoOptional = npoRep.findById(npoId);
        if (!tbNpoOptional.isPresent()) {
            new ApiErrorResponse(ERROR_BUNDLE.npoNotFound.name(), ERROR_BUNDLE.npoNotFound.getDesc());
        } else {
            tbCoreNpo tbCoreNpo = tbNpoOptional.get();
            tbCoreNpo.setVerifiedImage(verifiedImg);

            npoRep.save(tbCoreNpo);
        }
    }

    @Transactional
    @Override
    public void updateNpoRegisterImg(Integer npoId, String registerImg) {
        Optional<tbCoreNpo> tbNpoOptional = npoRep.findById(npoId);
        if (!tbNpoOptional.isPresent()) {
            new ApiErrorResponse(ERROR_BUNDLE.npoNotFound.name(), ERROR_BUNDLE.npoNotFound.getDesc());
        } else {
            tbCoreNpo tbCoreNpo = tbNpoOptional.get();
            tbCoreNpo.setGovernmentRegisterImage(registerImg);

            npoRep.save(tbCoreNpo);
        }
    }

    @Transactional
    @Override
    public void removeNpoIcon(Integer npoId) {
        Optional<tbCoreNpo> tbNpoOptional = npoRep.findById(npoId);
        if (!tbNpoOptional.isPresent()) {
            new ApiErrorResponse(ERROR_BUNDLE.npoNotFound.name(), ERROR_BUNDLE.npoNotFound.getDesc());
        } else {
            tbCoreNpo tbCoreNpo = tbNpoOptional.get();
            resourceService.imageRemove(tbCoreNpo.getNpoIcon());
            tbCoreNpo.setNpoIcon("default_event_image.png");
            tbCoreNpo.setThumbPath("default_event_image.png");

            npoRep.save(tbCoreNpo);
        }
    }

    @Transactional
    @Override
    public void removeNpoVerifiedImg(Integer npoId) {
        Optional<tbCoreNpo> tbNpoOptional = npoRep.findById(npoId);
        if (!tbNpoOptional.isPresent()) {
            new ApiErrorResponse(ERROR_BUNDLE.npoNotFound.name(), ERROR_BUNDLE.npoNotFound.getDesc());
        } else {
            tbCoreNpo tbCoreNpo = tbNpoOptional.get();
            resourceService.imageRemove(tbCoreNpo.getVerifiedImage());
            tbCoreNpo.setVerifiedImage("default_event_image.png");

            npoRep.save(tbCoreNpo);
        }
    }

    @Transactional
    @Override
    public void removeNpoRegisterImg(Integer npoId) {
        Optional<tbCoreNpo> tbNpoOptional = npoRep.findById(npoId);
        if (!tbNpoOptional.isPresent()) {
            new ApiErrorResponse(ERROR_BUNDLE.npoNotFound.name(), ERROR_BUNDLE.npoNotFound.getDesc());
        } else {
            tbCoreNpo tbCoreNpo = tbNpoOptional.get();
            resourceService.imageRemove(tbCoreNpo.getGovernmentRegisterImage());
            tbCoreNpo.setGovernmentRegisterImage("default_event_image.png");

            npoRep.save(tbCoreNpo);
        }
    }

    @Override
    public List<?> getNposMenu(String contactEmail) {
        List<vwCoreNpoMenu> npos = null;
        if (contactEmail == null) {
            npos = npoMenuRep.findAllByVerifiedAndAdmViewed(true, true);
        } else {
            npos = npoMenuRep.findAllByVerifiedAndAdmViewedAndContactEmail(true, true, contactEmail);
        }
        return npos.stream().map(n -> {
            com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
            jsonObject.put("id", n.getId());
            jsonObject.put("name", n.getName());
            return jsonObject;
        }).collect(Collectors.toList());
    }

    private NpoVO transfer(tbCoreNpo npo) {
        NpoVO instance = new NpoVO();
        instance.setId(npo.getId());
        instance.setUserId(npo.getUserId());
        instance.setName(npo.getName());
        instance.setUid(npo.getUid());
        if (npo.getNpoIcon() != null) {
            instance.setNpoIcon(strHTTPServerHost + npo.getNpoIcon());
        }
        instance.setDescription(npo.getDescription());
        instance.setRegisterNumber(npo.getRegisterNumber());
        instance.setSerialNumber(npo.getSerialNumber());
        if (npo.getVerifiedImage() != null) {
            instance.setVerifiedImage(strHTTPServerHost + npo.getVerifiedImage());
        }
        instance.setServiceTarget(npo.getServiceTarget());
        if (npo.getGovernmentRegisterImage() != null) {
            instance.setRegisterImage(strHTTPServerHost + npo.getGovernmentRegisterImage());
        }
        instance.setPubDate(npo.getPubDate());
        instance.setContactName(npo.getContactName());
        instance.setContactPhone(npo.getContactPhone());
        instance.setContactEmail(npo.getContactEmail());
        instance.setContactJob(npo.getContactJob());
        instance.setContact2Name(npo.getContact2Name());
        instance.setContact2Phone(npo.getContact2Phone());
        instance.setContact2Email(npo.getContact2Email());
        instance.setContact2Job(npo.getContact2Job());
        instance.setContactAddress(npo.getContactAddress());
        instance.setContactWebsite(npo.getContactWebsite());
        instance.setContactSite(npo.getContactSite());
        instance.setVerified(npo.getVerified());
        instance.setAdmViewed(npo.getAdmViewed());
        instance.setAdministratorId(npo.getAdministratorId());
        if (npo.getThumbPath() != null) {
            instance.setThumbPath(strHTTPServerHost + npo.getThumbPath());
        }
        //https://tpe-jira2.fihtdc.com/browse/TM01-36
//        instance.setRatingUserNum(npo.getRatingUserNum());
//        instance.setTotalRatingScore(npo.getRatingUserNum());
        instance.setSubscribedUserNum(npo.getSubscribedUserNum());
        instance.setJoinedUserNum(npo.getJoinedUserNum());
        instance.setEventNum(npo.getEventNum());
        instance.setYoutubeCode(npo.getYoutubeCode());
        instance.setInventory(npo.getInventory());
        instance.setEnterprise(npo.getEnterprise());
        instance.setPromote(npo.getPromote());

        return instance;
    }

    private NpoPromoteVO transferPromote(vwNpoPromote npo) {
        NpoPromoteVO instance = new NpoPromoteVO();
        instance.setId(npo.getId());
        instance.setName(npo.getName());
        return instance;
    }


}