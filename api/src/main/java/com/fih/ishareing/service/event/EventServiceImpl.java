package com.fih.ishareing.service.event;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fih.ishareing.constant.ApiErrorConstant;
import com.fih.ishareing.controller.base.error.ApiErrorResponse;
import com.fih.ishareing.controller.base.vo.result.ApiBatchResult;
import com.fih.ishareing.errorHandling.ErrorHandlingConstants.ERROR_BUNDLE;
import com.fih.ishareing.repository.*;
import com.fih.ishareing.repository.entity.*;
import com.fih.ishareing.service.AbstractService;
import com.fih.ishareing.service.event.model.*;
import com.fih.ishareing.service.resource.ResourceService;
import com.fih.ishareing.utils.MailUtils;
import com.fih.ishareing.utils.StringUtils;
import com.fih.ishareing.utils.date.DateUtils;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl extends AbstractService implements EventService {
    private static Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);
    @Autowired
    private CoreEventRepository eventRep;
    @Autowired
    private CoreNpoRepository npoRep;
    @Autowired
    private vwEventRepository vwEventRep;
    @Autowired
    private vwUserRegisteredEventRepository vwUserRegisteredEventRep;
    @Autowired
    private CoreUserRegisteredEventRepository userRegisteredEventRep;
    @Autowired
    private CoreSkillGroupRepository skillGroupRep;
    @Autowired
    private CoreEventResultImageRepository eventResultImageRep;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private CoreUserFocusedEventRepository userFocusedEventRep;
    @Autowired
    private CoreEventCooperationNpoRepository eventCooperationNpoRep;
    @Autowired
    private CoreUserAccountRepository userAccountRep;
    @Autowired
    private CoreEventVisithistoryRepository eventVisithistoryRep;
    @Autowired
    private CoreScoreRecordsRepository scoreRecordsRep;
    @Autowired
    private vwEventMenuRepository vwEventMenuRep;
    @Autowired
    private MailUtils mailUtils;

    @Override
    public long count(Specification<vwEvent> spec) {
        return vwEventRep.count(spec);
    }

    @Override
    public long countRegistered(Specification<vwUserRegisteredEvent> spec) {
        return vwUserRegisteredEventRep.count(spec);
    }

    @Override
    public List<EventVO> findEvents(Specification<vwEvent> spec, Pageable pageable) {
        return vwEventRep.findAll(spec, pageable).stream().map(this::transfer).collect(Collectors.toList());
    }

    @Override
    public List<EventRegisteredVO> findUserRegistered(Specification<vwUserRegisteredEvent> spec, Pageable pageable) {
        return vwUserRegisteredEventRep.findAll(spec, pageable).stream().map(this::transferRegistered).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<?> addEvents(List<EventAddVO> events) {
        ApiBatchResult result = new ApiBatchResult();
        for (int i = 0; i < events.size(); i++) {
            tbCoreEvent tbCoreEvent = new tbCoreEvent();
            EventAddVO event = events.get(i);
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            builder.put(ApiErrorConstant.INDEX, i);
            Optional<tbCoreNpo> tbNpoOptional = npoRep.findById(event.getNpoId());
            if (!tbNpoOptional.isPresent()) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.npoNotFound.name(), ERROR_BUNDLE.npoNotFound.getDesc()));
            }

            if (CollectionUtils.isEmpty(errors)) {
                try {
                    tbCoreEvent.setOwnerNPOId(event.getNpoId());
                    tbCoreEvent.setImageLink1("default_event_image.png");
                    tbCoreEvent.setImageLink2("");
                    tbCoreEvent.setImageLink3("");
                    tbCoreEvent.setImageLink4("");
                    tbCoreEvent.setImageLink5("");
                    tbCoreEvent.setUid(UUID.randomUUID().toString());
                    tbCoreEvent.setTags(event.getTags());
                    tbCoreEvent.setPubDate(new Timestamp(System.currentTimeMillis()));
                    tbCoreEvent.setHappenDate(new Timestamp(DateUtils.toDate(event.getHappenDate()).getTime()));
                    tbCoreEvent.setCloseDate(new Timestamp(DateUtils.toDate(event.getCloseDate()).getTime()));
                    tbCoreEvent.setRegisterDeadlineDate(new Timestamp(DateUtils.toDate(event.getRegisterDeadlineDate()).getTime()));
                    tbCoreEvent.setSubject(event.getSubject());
                    tbCoreEvent.setDescription(event.getDescription());
                    tbCoreEvent.setEventHour(event.getEventHour());
                    tbCoreEvent.setFocusNum(0);
                    tbCoreEvent.setAddressCity(event.getAddressCity());
                    tbCoreEvent.setAddress(event.getAddress());
                    tbCoreEvent.setInsurance(event.getInsurance());
                    tbCoreEvent.setInsuranceDescription(event.getInsuranceDescription());
                    tbCoreEvent.setVolunteerTraining(StringUtils.BooleanToInteger(event.getVolunteerTraining()));
                    tbCoreEvent.setVolunteerTrainingDescription(event.getVolunteerTrainingDesc());
                    tbCoreEvent.setLat(event.getLat());
                    tbCoreEvent.setLng(event.getLng());
                    tbCoreEvent.setRequiredVolunteerNumber(event.getRequiredVolunteerNum());
                    tbCoreEvent.setCurrentVolunteerNumber(0);
                    tbCoreEvent.setRequiredGroup(StringUtils.BooleanToInteger(event.getRequiredGroup()));
                    tbCoreEvent.setSkillsDescription(event.getSkillsDescription());
                    tbCoreEvent.setUserAccountId(null);
                    tbCoreEvent.setThumbPath("default_event_image.png");
                    tbCoreEvent.setReplyNum(0);
                    tbCoreEvent.setRatingUserNum(0);
                    tbCoreEvent.setTotalRatingScore(0D);
                    tbCoreEvent.setUrgent(event.getUrgent());
                    tbCoreEvent.setLeaveUid(UUID.randomUUID().toString());
                    tbCoreEvent.setRequireSignout(event.getRequireSignout());
                    tbCoreEvent.setShort(event.getShortTerm());
                    tbCoreEvent.setVolunteerType(event.getVolunteerType());
                    tbCoreEvent.setDonationSerial(event.getDonationSerial());
                    if (event.getDonationStartDate() != null && !event.getDonationStartDate().trim().equalsIgnoreCase("")) {
                        tbCoreEvent.setDonationStartDate(new Timestamp(DateUtils.toDate(event.getDonationStartDate()).getTime()));
                    } else {
                        tbCoreEvent.setDonationStartDate(new Timestamp(System.currentTimeMillis()));
                    }
                    if (event.getDonationEndDate() != null && !event.getDonationEndDate().trim().equalsIgnoreCase("")) {
                        tbCoreEvent.setDonationEndDate(new Timestamp(DateUtils.toDate(event.getDonationEndDate()).getTime()));
                    } else {
                        tbCoreEvent.setDonationEndDate(new Timestamp(System.currentTimeMillis()));
                    }
                    tbCoreEvent.setServiceType(event.getServiceType());
                    tbCoreEvent.setForeignThirdPartyId(event.getForeignThirdPartyId());
                    tbCoreEvent.setVolunteerEvent(!event.getSupplyEvent());
                    tbCoreEvent.setPromote(event.getPromote());
                    tbCoreEvent.setNote(event.getNote());

                    tbCoreEvent = eventRep.save(tbCoreEvent);

                    builder.put(ApiErrorConstant.ID, tbCoreEvent.getId());
                } catch (Exception ex) {
                    logger.error("An error occurred while add new event, index:{}, error:{}", i,
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
    public List<?> updateEvents(List<EventUpdateVO> events) {
        ApiBatchResult result = new ApiBatchResult();
        for (int i = 0; i < events.size(); i++) {
            tbCoreEvent tbCoreEvent = null;
            Optional<tbCoreEvent> tbEventOptional = eventRep.findById(events.get(i).getId());
            EventUpdateVO event = events.get(i);
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            builder.put(ApiErrorConstant.INDEX, i);
            Optional<tbCoreNpo> tbNpoOptional = null;
            if (!tbEventOptional.isPresent()) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.eventNotFound.name(), ERROR_BUNDLE.eventNotFound.getDesc()));
            } else {
                tbCoreEvent = tbEventOptional.get();
                tbNpoOptional = npoRep.findById(tbCoreEvent.getOwnerNPOId());
            }
            if (tbNpoOptional == null && !tbNpoOptional.isPresent()) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.npoNotFound.name(), ERROR_BUNDLE.npoNotFound.getDesc()));
            }

            if (event.getCooperationNpoIds() != null && event.getCooperationNpoIds().length > 0) {
                Arrays.stream(event.getCooperationNpoIds()).forEach(npoId -> {
                    if (!npoRep.existsById(Integer.valueOf(npoId))) {
                        errors.add(
                                new ApiErrorResponse(ERROR_BUNDLE.npoNotFound.name(), ERROR_BUNDLE.npoNotFound.getDesc()));
                    }
                });
            }

            if (CollectionUtils.isEmpty(errors)) {
                try {
                    if (event.getNpoId() != null) {
                        tbCoreEvent.setOwnerNPOId(event.getNpoId());
                    }
                    if (event.getTags() != null) {
                        tbCoreEvent.setTags(event.getTags());
                    }
                    if (event.getHappenDate() != null && !event.getHappenDate().trim().equalsIgnoreCase("")) {
                        tbCoreEvent.setHappenDate(new Timestamp(DateUtils.toDate(event.getHappenDate()).getTime()));
                    }
                    if (event.getCloseDate() != null && !event.getCloseDate().trim().equalsIgnoreCase("")) {
                        tbCoreEvent.setCloseDate(new Timestamp(DateUtils.toDate(event.getCloseDate()).getTime()));
                    }
                    if (event.getRegisterDeadlineDate() != null && !event.getRegisterDeadlineDate().trim().equalsIgnoreCase("")) {
                        tbCoreEvent.setRegisterDeadlineDate(new Timestamp(DateUtils.toDate(event.getRegisterDeadlineDate()).getTime()));
                    }
                    if (event.getSubject() != null) {
                        tbCoreEvent.setSubject(event.getSubject());
                    }
                    if (event.getDescription() != null) {
                        tbCoreEvent.setDescription(event.getDescription());
                    }
                    if (event.getEventHour() != null) {
                        tbCoreEvent.setEventHour(event.getEventHour());
                    }
                    if (event.getAddressCity() != null) {
                        tbCoreEvent.setAddressCity(event.getAddressCity());
                    }
                    if (event.getAddress() != null) {
                        tbCoreEvent.setAddress(event.getAddress());
                    }
                    if (event.getInsurance() != null) {
                        tbCoreEvent.setInsurance(event.getInsurance());
                    }
                    if (event.getInsuranceDescription() != null) {
                        tbCoreEvent.setInsuranceDescription(event.getInsuranceDescription());
                    }
                    if (event.getVolunteerTraining() != null) {
                        tbCoreEvent.setVolunteerTraining(StringUtils.BooleanToInteger(event.getVolunteerTraining()));
                    }
                    if (event.getVolunteerTrainingDesc() != null) {
                        tbCoreEvent.setVolunteerTrainingDescription(event.getVolunteerTrainingDesc());
                    }
                    if (event.getLat() != null) {
                        tbCoreEvent.setLat(event.getLat());
                    }
                    if (event.getLng() != null) {
                        tbCoreEvent.setLng(event.getLng());
                    }
                    if (event.getCurrentVolunteerNum() != null) {
                        tbCoreEvent.setCurrentVolunteerNumber(event.getCurrentVolunteerNum());
                    }
                    if (event.getRequiredVolunteerNum() != null) {
                        tbCoreEvent.setRequiredVolunteerNumber(event.getRequiredVolunteerNum());
                    }
                    if (event.getRequiredGroup() != null) {
                        tbCoreEvent.setRequiredGroup(StringUtils.BooleanToInteger(event.getRequiredGroup()));
                    }
                    if (event.getSkillsDescription() != null) {
                        tbCoreEvent.setSkillsDescription(event.getSkillsDescription());
                    }
                    if (event.getUrgent() != null) {
                        tbCoreEvent.setUrgent(event.getUrgent());
                    }
                    if (event.getRequireSignout() != null) {
                        tbCoreEvent.setRequireSignout(event.getRequireSignout());
                    }
                    if (event.getShortTerm() != null) {
                        tbCoreEvent.setShort(event.getShortTerm());
                    }
                    if (event.getVolunteerType() != null) {
                        tbCoreEvent.setVolunteerType(event.getVolunteerType());
                    }
                    if (event.getDonationSerial() != null) {
                        tbCoreEvent.setDonationSerial(event.getDonationSerial());
                    }
                    if (event.getDonationStartDate() != null && !event.getDonationStartDate().trim().equalsIgnoreCase("")) {
                        tbCoreEvent.setDonationStartDate(new Timestamp(DateUtils.toDate(event.getDonationStartDate()).getTime()));
                    }
                    if (event.getDonationEndDate() != null && !event.getDonationEndDate().trim().equalsIgnoreCase("")) {
                        tbCoreEvent.setDonationEndDate(new Timestamp(DateUtils.toDate(event.getDonationEndDate()).getTime()));
                    }
                    if (event.getServiceType() != null) {
                        tbCoreEvent.setServiceType(event.getServiceType());
                    }
                    if (event.getForeignThirdPartyId() != null) {
                        tbCoreEvent.setForeignThirdPartyId(event.getForeignThirdPartyId());
                    }
                    if (event.getSupplyEvent() != null) {
                        tbCoreEvent.setVolunteerEvent(!event.getSupplyEvent());
                    }
                    if (event.getPromote() != null) {
                        tbCoreEvent.setPromote(event.getPromote());
                    }
                    if (event.getNote() != null) {
                        tbCoreEvent.setNote(event.getNote());
                    }

                    tbCoreEvent = eventRep.save(tbCoreEvent);

                    if (event.getCooperationNpoIds() != null) {
                        eventCooperationNpoRep.deleteAllByEventId(tbCoreEvent.getId());
                        if (event.getCooperationNpoIds().length > 0) {
                            Integer eventId = tbCoreEvent.getId();
                            List<tbCoreEventCooperationNpo> eventCooperationNpos = new ArrayList<>();
                            Arrays.stream(event.getCooperationNpoIds()).forEach(npoId -> {
                                tbCoreEventCooperationNpo eventCooperationNpo = new tbCoreEventCooperationNpo(eventId, Integer.valueOf(npoId));
                                eventCooperationNpos.add(eventCooperationNpo);
                            });
                            eventCooperationNpoRep.saveAll(eventCooperationNpos);
                        }
                    }

                    builder.put(ApiErrorConstant.ID, tbCoreEvent.getId());
                } catch (Exception ex) {
                    logger.error("An error occurred while update event, index:{}, error:{}", i,
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
    public List<?> deleteEvents(List<String> eventIds) {
        ApiBatchResult result = new ApiBatchResult();

        for (int i = 0; i < eventIds.size(); i++) {
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            Integer eventId = Integer.valueOf(eventIds.get(i));
            builder.put(ApiErrorConstant.ID, eventId);
            Optional<tbCoreEvent> tbEvent = eventRep.findById(eventId);

            if (!tbEvent.isPresent()) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.eventNotFound.name(), ERROR_BUNDLE.eventNotFound.getDesc()));
            }
            if (CollectionUtils.isEmpty(errors)) {
                try {
                    eventRep.deleteById(eventId);
                } catch (Exception ex) {
                    logger.error("An error occurred while deleting event, id:{}, error:{}", eventId, ex.getMessage());
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
    public List<?> updateUserRegistered(List<EventRegisteredUpdateVO> eventRegisteredUpdateVOList) {
        ApiBatchResult result = new ApiBatchResult();
        for (int i = 0; i < eventRegisteredUpdateVOList.size(); i++) {
            tbCoreUserRegisteredEvent tbCoreUserRegisteredEvent = userRegisteredEventRep.findByUserIdAndRegisteredEventId(eventRegisteredUpdateVOList.get(i).getUserId(), eventRegisteredUpdateVOList.get(i).getEventId());
            EventRegisteredUpdateVO eventRegistered = eventRegisteredUpdateVOList.get(i);
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            builder.put(ApiErrorConstant.INDEX, i);
            builder.put(ApiErrorConstant.USERID, eventRegistered.getUserId());
            builder.put(ApiErrorConstant.EVENTID, eventRegistered.getEventId());
            Boolean isEnterprise = false;
            Boolean giveScore = false;
            Boolean minusScore = false;
            Optional<tbCoreEvent> tbCoreEventOptional = eventRep.findById(eventRegistered.getEventId());
            tbCoreEvent tbCoreEvent = null;
            Optional<tbCoreNpo> tbCoreNpoOptional = null;
            tbCoreNpo tbCoreNpo = null;
            tbCoreUserAccount tbCoreUserAccount = null;

            if (tbCoreUserRegisteredEvent == null) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.registeredNotFound.name(), ERROR_BUNDLE.registeredNotFound.getDesc()));
            } else {
                if (tbCoreEventOptional.isPresent()) {
                    tbCoreEvent = tbCoreEventOptional.get();
                    tbCoreNpoOptional = npoRep.findById(tbCoreEvent.getOwnerNPOId());
                    if (tbCoreNpoOptional.isPresent()) {
                        tbCoreNpo = tbCoreNpoOptional.get();
                        isEnterprise = tbCoreNpo.getEnterprise();
                    }
                    tbCoreUserAccount = userAccountRep.findByUserId(eventRegistered.getUserId());
                }

            }

            if (CollectionUtils.isEmpty(errors)) {
                try {
                    if (eventRegistered.getJoined() != null) {
                        if (!tbCoreEvent.getVolunteerEvent() && tbCoreUserRegisteredEvent.getIsJoined() == 0 && eventRegistered.getJoined()) {
                            giveScore = true;
                        }
                        if (!tbCoreEvent.getVolunteerEvent() && tbCoreUserRegisteredEvent.getIsJoined() == 1 && !eventRegistered.getJoined()) {
                            minusScore = true;
                        }

                        if (eventRegistered.getJoined()) {
                            tbCoreUserRegisteredEvent.setJoinTime(new Timestamp(System.currentTimeMillis()));
                        }
                        tbCoreUserRegisteredEvent.setIsJoined(StringUtils.BooleanToInteger(eventRegistered.getJoined()));

                    }
                    if (eventRegistered.getLeaved() != null) {
                        if (tbCoreEvent.getVolunteerEvent() && tbCoreUserRegisteredEvent.getIsJoined() == 1 && tbCoreUserRegisteredEvent.getIsLeaved() == 0 && eventRegistered.getLeaved()) {
                            giveScore = true;
                        }
                        if (tbCoreEvent.getVolunteerEvent() && tbCoreUserRegisteredEvent.getIsLeaved() == 1 && !eventRegistered.getLeaved()) {
                            minusScore = true;
                        }

                        if (eventRegistered.getLeaved()) {
                            tbCoreUserRegisteredEvent.setLeaveTime(new Timestamp(System.currentTimeMillis()));
                        }
                        tbCoreUserRegisteredEvent.setIsLeaved(StringUtils.BooleanToInteger(eventRegistered.getLeaved()));
                    }

                    userRegisteredEventRep.save(tbCoreUserRegisteredEvent);

                    if (giveScore) {
                        if (tbCoreNpo != null) {
                            Integer joinedUserNum = tbCoreNpo.getJoinedUserNum();
                            tbCoreNpo.setJoinedUserNum(++joinedUserNum);
                            npoRep.save(tbCoreNpo);
                        }

                        Integer userEventNum = tbCoreUserAccount.getEventNum() + 1;

                        Double eventHour = tbCoreEvent.getEventHour();
                        Double userEventHour = tbCoreUserAccount.getEventHour() + eventHour;
                        Double userEventEnterpriseHour = tbCoreUserAccount.getEventEnterpriseHour() + eventHour;
                        Double userEventGeneralHour = tbCoreUserAccount.getEventGeneralHour() + eventHour;
                        Integer userScore = (tbCoreUserAccount.getScore() < 100) ? tbCoreUserAccount.getScore() + 1 : tbCoreUserAccount.getScore();
                        tbCoreUserAccount.setEventHour(userEventHour);


                        if (isEnterprise) {
                            tbCoreUserAccount.setEventEnterpriseHour(userEventEnterpriseHour);
                        } else {
                            tbCoreUserAccount.setEventGeneralHour(userEventGeneralHour);
                        }

                        tbCoreUserAccount.setEventNum(userEventNum);
                        tbCoreUserAccount.setScore(userScore);
                        userAccountRep.save(tbCoreUserAccount);

                        saveScoreRecords(tbCoreEvent.getSubject(), tbCoreEvent.getId(), tbCoreUserAccount.getUserId(), tbCoreUserAccount.getScore(), "後台-參加" + tbCoreEvent.getSubject() + "+1");
                    }

                    if (minusScore) {
                        if (tbCoreNpo != null) {
                            Integer joinedUserNum = tbCoreNpo.getJoinedUserNum();
                            tbCoreNpo.setJoinedUserNum(--joinedUserNum);
                            npoRep.save(tbCoreNpo);
                        }

                        Integer userEventNum = (tbCoreUserAccount.getEventNum() > 0) ? tbCoreUserAccount.getEventNum() - 1 : 0;

                        Double eventHour = tbCoreEvent.getEventHour();
                        Double userEventHour = (tbCoreUserAccount.getEventHour() > 0) ? tbCoreUserAccount.getEventHour() - eventHour : 0;
                        Double userEventEnterpriseHour = (tbCoreUserAccount.getEventEnterpriseHour() > 0) ? tbCoreUserAccount.getEventEnterpriseHour() - eventHour : 0;
                        Double userEventGeneralHour = (tbCoreUserAccount.getEventGeneralHour() > 0) ? tbCoreUserAccount.getEventGeneralHour() - eventHour : 0;
                        Integer userScore = (tbCoreUserAccount.getScore() > 0) ? tbCoreUserAccount.getScore() - 1 : 0;
                        tbCoreUserAccount.setEventHour(userEventHour);

                        if (isEnterprise) {
                            tbCoreUserAccount.setEventEnterpriseHour(userEventEnterpriseHour);
                        } else {
                            tbCoreUserAccount.setEventGeneralHour(userEventGeneralHour);
                        }

                        tbCoreUserAccount.setEventNum(userEventNum);
                        tbCoreUserAccount.setScore(userScore);
                        userAccountRep.save(tbCoreUserAccount);

                        saveScoreRecords(tbCoreEvent.getSubject(), tbCoreEvent.getId(), tbCoreUserAccount.getUserId(), tbCoreUserAccount.getScore(), "後台-取消" + tbCoreEvent.getSubject() + "-1");
                    }


                } catch (Exception ex) {
                    logger.error("An error occurred while update UserRegistered, index:{}, error:{}", i,
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
    public List<?> addEventSkillGroups(List<EventSkillGroupAddVO> eventSkillGroups) {
        ApiBatchResult result = new ApiBatchResult();
        for (int i = 0; i < eventSkillGroups.size(); i++) {
            tbCoreSkillGroup tbCoreSkillGroup = new tbCoreSkillGroup();
            EventSkillGroupAddVO skillGroup = eventSkillGroups.get(i);
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            builder.put(ApiErrorConstant.INDEX, i);
            Optional<tbCoreEvent> tbEvent = eventRep.findById(skillGroup.getEventId());
            if (!tbEvent.isPresent()) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.eventNotFound.name(), ERROR_BUNDLE.eventNotFound.getDesc()));
            }

            if (CollectionUtils.isEmpty(errors)) {
                try {
                    tbCoreSkillGroup.setOwnerEventId(skillGroup.getEventId());
                    tbCoreSkillGroup.setName(skillGroup.getName());
                    tbCoreSkillGroup.setSkillsDescription(skillGroup.getSkillsDescription());
                    tbCoreSkillGroup.setVolunteerNumber(skillGroup.getVolunteerNumber());
                    tbCoreSkillGroup.setCurrentVolunteerNumber(0);

                    tbCoreSkillGroup = skillGroupRep.save(tbCoreSkillGroup);

                    builder.put(ApiErrorConstant.ID, tbCoreSkillGroup.getId());
                } catch (Exception ex) {
                    logger.error("An error occurred while add new EventSkillGroups, index:{}, error:{}", i,
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
    public List<?> updateEventSkillGroups(List<EventSkillGroupUpdateVO> eventSkillGroups) {
        ApiBatchResult result = new ApiBatchResult();
        for (int i = 0; i < eventSkillGroups.size(); i++) {
            tbCoreSkillGroup tbCoreSkillGroup = null;
            EventSkillGroupUpdateVO skillGroupUpdateVO = eventSkillGroups.get(i);
            Optional<tbCoreSkillGroup> tbSkillGroupOptional = skillGroupRep.findById(skillGroupUpdateVO.getId());
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            builder.put(ApiErrorConstant.INDEX, i);
            builder.put(ApiErrorConstant.ID, skillGroupUpdateVO.getId());

            if (!tbSkillGroupOptional.isPresent()) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.eventSkillGroupNotFound.name(), ERROR_BUNDLE.eventSkillGroupNotFound.getDesc()));
            } else {
                tbCoreSkillGroup = tbSkillGroupOptional.get();
                if (skillGroupUpdateVO.getVolunteerNumber() != null && skillGroupUpdateVO.getVolunteerNumber() < tbCoreSkillGroup.getCurrentVolunteerNumber()) {
                    errors.add(
                            new ApiErrorResponse(ERROR_BUNDLE.eventSkillGroupNotFound.name(), ERROR_BUNDLE.eventSkillGroupNotFound.getDesc()));
                }
            }

            if (CollectionUtils.isEmpty(errors)) {
                try {
                    if (skillGroupUpdateVO.getName() != null) {
                        tbCoreSkillGroup.setName(skillGroupUpdateVO.getName());
                    }
                    if (skillGroupUpdateVO.getSkillsDescription() != null) {
                        tbCoreSkillGroup.setSkillsDescription(skillGroupUpdateVO.getSkillsDescription());
                    }
                    if (skillGroupUpdateVO.getVolunteerNumber() != null) {
                        tbCoreSkillGroup.setVolunteerNumber(skillGroupUpdateVO.getVolunteerNumber());
                    }

                    skillGroupRep.save(tbCoreSkillGroup);
                } catch (Exception ex) {
                    logger.error("An error occurred while update EventSkillGroups, index:{}, error:{}", i,
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
    public List<?> deleteEventSkillGroups(List<String> eventSkillGroupIds) {
        ApiBatchResult result = new ApiBatchResult();

        for (int i = 0; i < eventSkillGroupIds.size(); i++) {
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            Integer skillGroupId = Integer.valueOf(eventSkillGroupIds.get(i));
            builder.put(ApiErrorConstant.ID, skillGroupId);
            Optional<tbCoreSkillGroup> tbSkillGroup = skillGroupRep.findById(skillGroupId);

            if (!tbSkillGroup.isPresent()) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.eventSkillGroupNotFound.name(), ERROR_BUNDLE.eventSkillGroupNotFound.getDesc()));
            }
            if (CollectionUtils.isEmpty(errors)) {
                try {
                    skillGroupRep.deleteById(skillGroupId);
                    List<tbCoreUserRegisteredEvent> tbCoreUserRegisteredEventList = userRegisteredEventRep.findAllByEventSkillGroupId(skillGroupId);
                    for (int j = 0; j < tbCoreUserRegisteredEventList.size(); j++) {
                        tbCoreUserRegisteredEvent registeredEvent = tbCoreUserRegisteredEventList.get(j);
                        registeredEvent.setEventSkillGroupId(null);
                        userRegisteredEventRep.save(registeredEvent);
                    }
                } catch (Exception ex) {
                    logger.error("An error occurred while deleting skillGroup, id:{}, error:{}", skillGroupId, ex.getMessage());
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
    public void updateEventImg(Integer eventId, String img, String type) {
        Optional<tbCoreEvent> tbEventOptional = eventRep.findById(eventId);
        if (!tbEventOptional.isPresent()) {
            new ApiErrorResponse(ERROR_BUNDLE.eventNotFound.name(), ERROR_BUNDLE.eventNotFound.getDesc());
        } else {
            tbCoreEvent tbCoreEvent = tbEventOptional.get();
            if (type.equalsIgnoreCase("image1")) {
                tbCoreEvent.setImageLink1(img);
                tbCoreEvent.setThumbPath(img);
            }
            if (type.equalsIgnoreCase("image2")) {
                tbCoreEvent.setImageLink2(img);
            }
            if (type.equalsIgnoreCase("image3")) {
                tbCoreEvent.setImageLink3(img);
            }
            if (type.equalsIgnoreCase("image4")) {
                tbCoreEvent.setImageLink4(img);
            }
            if (type.equalsIgnoreCase("image5")) {
                tbCoreEvent.setImageLink5(img);
            }

            eventRep.save(tbCoreEvent);
        }
    }

    @Transactional
    @Override
    public void updateEventResultFileImg(Integer eventId, String img, Integer displaySort) {
        Optional<tbCoreEvent> tbEventOptional = eventRep.findById(eventId);
        if (!tbEventOptional.isPresent()) {
            new ApiErrorResponse(ERROR_BUNDLE.eventNotFound.name(), ERROR_BUNDLE.eventNotFound.getDesc());
        } else {
            Integer sort = (displaySort == null) ? 0 : displaySort;
            tbCoreEvent tbCoreEvent = tbEventOptional.get();
            tbCoreEventResultImage tbCoreEventResultImage = new tbCoreEventResultImage();
            tbCoreEventResultImage.setEventId(tbCoreEvent.getId());
            tbCoreEventResultImage.setImage(img);
            tbCoreEventResultImage.setThumbPath(img);
            tbCoreEventResultImage.setDisplaySort(sort);

            eventResultImageRep.save(tbCoreEventResultImage);
        }
    }

    @Transactional
    @Override
    public void removeEventImg(Integer eventId, String type) {
        Optional<tbCoreEvent> tbEventOptional = eventRep.findById(eventId);
        if (!tbEventOptional.isPresent()) {
            new ApiErrorResponse(ERROR_BUNDLE.eventNotFound.name(), ERROR_BUNDLE.eventNotFound.getDesc());
        } else {
            tbCoreEvent tbCoreEvent = tbEventOptional.get();
            String imagePath = null;
            if (type.equalsIgnoreCase("image1")) {
                tbCoreEvent.setImageLink1("default_event_image.png");
                tbCoreEvent.setThumbPath("default_event_image.png");
                imagePath = tbCoreEvent.getImageLink1();
            }
            if (type.equalsIgnoreCase("image2")) {
                tbCoreEvent.setImageLink2("");
                imagePath = tbCoreEvent.getImageLink2();
            }
            if (type.equalsIgnoreCase("image3")) {
                tbCoreEvent.setImageLink3("");
                imagePath = tbCoreEvent.getImageLink3();
            }
            if (type.equalsIgnoreCase("image4")) {
                tbCoreEvent.setImageLink4("");
                imagePath = tbCoreEvent.getImageLink4();
            }
            if (type.equalsIgnoreCase("image5")) {
                tbCoreEvent.setImageLink5("");
                imagePath = tbCoreEvent.getImageLink5();
            }
            if (imagePath != null) {
                resourceService.imageRemove(imagePath);
            }


            eventRep.save(tbCoreEvent);
        }
    }

    @Transactional
    @Override
    public void removeEventResultFileImg(Integer eventReplyFileId) {
        Optional<tbCoreEventResultImage> tbEventResultImageOptional = eventResultImageRep.findById(eventReplyFileId);
        if (!tbEventResultImageOptional.isPresent()) {
            new ApiErrorResponse(ERROR_BUNDLE.eventResultFileNotFound.name(), ERROR_BUNDLE.eventResultFileNotFound.getDesc());
        } else {
            tbCoreEventResultImage tbCoreEventResultImage = tbEventResultImageOptional.get();
            resourceService.imageRemove(tbCoreEventResultImage.getImage());
            eventResultImageRep.deleteById(eventReplyFileId);
        }
    }

    private EventVO transfer(vwEvent event) {
        EventVO instance = new EventVO();
        instance.setId(event.getId());
        instance.setNpoId(event.getOwnerNPOId());
        instance.setImage1(strHTTPServerHost + event.getImageLink1());
        if (event.getImageLink2() != null && !event.getImageLink2().equalsIgnoreCase("")) {
            instance.setImage2(strHTTPServerHost + event.getImageLink2());
        }
        if (event.getImageLink3() != null && !event.getImageLink3().equalsIgnoreCase("")) {
            instance.setImage3(strHTTPServerHost + event.getImageLink3());
        }
        if (event.getImageLink4() != null && !event.getImageLink4().equalsIgnoreCase("")) {
            instance.setImage4(strHTTPServerHost + event.getImageLink4());
        }
        if (event.getImageLink5() != null && !event.getImageLink5().equalsIgnoreCase("")) {
            instance.setImage5(strHTTPServerHost + event.getImageLink5());
        }
        instance.setUid(event.getUid());
        instance.setTags(event.getTags());
        instance.setPubDate(event.getPubDate());
        instance.setHappenDate(event.getHappenDate());
        instance.setCloseDate(event.getCloseDate());
        instance.setRegisterDeadlineDate(event.getRegisterDeadlineDate());
        instance.setSubject(event.getSubject());
        instance.setDescription(event.getDescription());
        instance.setEventHour(event.getEventHour());
        instance.setAddressCity(event.getAddressCity());
        instance.setAddress(event.getAddress());
        instance.setInsurance(event.getInsurance());
        instance.setInsuranceDescription(event.getInsuranceDescription());
        instance.setVolunteerTraining(event.getVolunteerTraining());
        instance.setVolunteerTrainingDesc(event.getVolunteerTrainingDescription());
        instance.setLat(event.getLat());
        instance.setLng(event.getLng());
        instance.setRequiredVolunteerNum(event.getRequiredVolunteerNumber());
        instance.setCurrentVolunteerNum(event.getCurrentVolunteerNumber());
        instance.setRequiredGroup(event.getRequiredGroup());
        instance.setSkillsDescription(event.getSkillsDescription());
        instance.setUserId(event.getUserAccountId());
        if (event.getThumbPath() != null) {
            instance.setThumbPath(strHTTPServerHost + event.getThumbPath());
        }
        instance.setUrgent(event.getUrgent());
        instance.setRequireSignout(event.getRequireSignout());
        instance.setShort(event.getShort());
        instance.setVolunteerType(event.getVolunteerType());
        instance.setDonationSerial(event.getDonationSerial());
        instance.setDonationStartDate(event.getDonationStartDate());
        instance.setDonationEndDate(event.getDonationEndDate());
        instance.setServiceType(event.getServiceType());
        instance.setForeignThirdPartyId(event.getForeignThirdPartyId());
        instance.setNpoName(event.getNpoName());
        instance.setFull(event.getFull());
        instance.setVolunteerEvent(event.getVolunteerEvent());
        instance.setEnterprise(event.getEnterprise());
        instance.setPromote(event.getPromote());
        instance.setFocusNum(event.getFocusNum());
        instance.setNote(event.getNote());

        if (event.getTbCoreSkillGroupList() != null && event.getTbCoreSkillGroupList().size() > 0) {
            instance = transferSkillGroup(instance, event.getTbCoreSkillGroupList());
        }
        if (event.getTbCoreEventResultImageList() != null && event.getTbCoreEventResultImageList().size() > 0) {
            instance = transferResultImage(instance, event.getTbCoreEventResultImageList());
        }
        if (event.getVwEventReplyList() != null && event.getVwEventReplyList().size() > 0) {
            instance = transferReply(instance, event.getVwEventReplyList());
        }
        if (event.getTbCoreEventCooperationNpoList() != null && event.getTbCoreEventCooperationNpoList().size() > 0) {
            instance = transferCooperationNpos(instance, event.getTbCoreEventCooperationNpoList());
        }
        return instance;
    }

    private EventVO transferSkillGroup(EventVO eventVO, List<tbCoreSkillGroup> tbCoreSkillGroupList) {
        eventVO.setSkillGroups(tbCoreSkillGroupList.stream().map(skillGroup -> {
            return new EventSkillGroupVO(skillGroup.getId(), skillGroup.getName(), skillGroup.getSkillsDescription(), skillGroup.getVolunteerNumber(), skillGroup.getCurrentVolunteerNumber());
        }).collect(Collectors.toList()));
        return eventVO;
    }

    private EventVO transferResultImage(EventVO eventVO, List<tbCoreEventResultImage> tbCoreEventResultImageList) {
        eventVO.setResultImages(tbCoreEventResultImageList.stream().map(resultImage -> {
            return new EventResultImageVO(resultImage.getId(), strHTTPServerHost + resultImage.getImage(), strHTTPServerHost + resultImage.getThumbPath(), resultImage.getDisplaySort());
        }).collect(Collectors.toList()));
        return eventVO;
    }

    private EventVO transferReply(EventVO eventVO, List<vwEventReply> vwEventReplyList) {
        eventVO.setReplys(vwEventReplyList.stream().map(reply -> {
            return new EventReplyVO(reply.getId(), reply.getUserId(), reply.getUserName(), strHTTPServerHost + reply.getUserPhoto(), strHTTPServerHost + reply.getUserIcon(), reply.getMessage(), strHTTPServerHost + reply.getImage(), strHTTPServerHost + reply.getThumbPath(), reply.getReplyTime());
        }).collect(Collectors.toList()));
        return eventVO;
    }

    private EventVO transferCooperationNpos(EventVO eventVO, List<vwEventCooperationNpos> tbCoreEventCooperationNpoListList) {
        eventVO.setCooperationNpos(tbCoreEventCooperationNpoListList.stream().map(npos -> {
            return new EventCooperationNposVO(npos.getNpoId(), npos.getNpoName());
        }).collect(Collectors.toList()));
        return eventVO;
    }

    private EventRegisteredVO transferRegistered(vwUserRegisteredEvent userRegisteredEvent) {
        EventRegisteredVO instance = new EventRegisteredVO();
        instance.setUserId(userRegisteredEvent.getUserId());
        instance.setEventId(userRegisteredEvent.getEventID());
        instance.setUid(userRegisteredEvent.getUid());
        instance.setScore(userRegisteredEvent.getScore());
        instance.setName(userRegisteredEvent.getRegisteredName());
        instance.setPhone(userRegisteredEvent.getRegisteredPhone());
        instance.setEmail(userRegisteredEvent.getRegisteredEmail());
        instance.setSkills(userRegisteredEvent.getRegisteredSkills());
        instance.setBirthday(userRegisteredEvent.getRegisteredBirthday());
        instance.setGuardianName(userRegisteredEvent.getRegisteredGuardianName());
        instance.setGuardianPhone(userRegisteredEvent.getGuardianPhone());
        instance.setSecurityId(userRegisteredEvent.getSecurityId());
        instance.setNote(userRegisteredEvent.getNote());
        instance.setEmployeeSerialNumber(userRegisteredEvent.getEmployeeSerialNumber());
        instance.setEnterpriseSerialNumber(userRegisteredEvent.getEnterpriseSerialNumber());
        instance.setJoined(StringUtils.stringToBoolean(userRegisteredEvent.getJoined().toString()));
        instance.setLeaved(StringUtils.stringToBoolean(userRegisteredEvent.getLeaved().toString()));
        if (userRegisteredEvent.getJoinTime() != null && !StringUtils.isNullOrWhiteSpace(userRegisteredEvent.getJoinTime())) {
            instance.setJoinTime(new Timestamp(DateUtils.toDate(userRegisteredEvent.getJoinTime()).getTime()));
        }
        if (userRegisteredEvent.getLeaveTime() != null && !StringUtils.isNullOrWhiteSpace(userRegisteredEvent.getLeaveTime())) {
            instance.setLeaveTime(new Timestamp(DateUtils.toDate(userRegisteredEvent.getLeaveTime()).getTime()));
        }
        if (userRegisteredEvent.getRegisterDate() != null && !StringUtils.isNullOrWhiteSpace(userRegisteredEvent.getRegisterDate())) {
            instance.setRegisterDate(new Timestamp(DateUtils.toDate(userRegisteredEvent.getRegisterDate()).getTime()));
        }
        instance.setUserEnterpriseSerialNumber(userRegisteredEvent.getThirdEnterpriseSerialNumber());
        instance.setUserScore(userRegisteredEvent.getUserScore());
        instance.setVolunteerEvent(StringUtils.stringToBoolean(userRegisteredEvent.getVolunteerEvent().toString()));
        instance.setEnterprise(StringUtils.stringToBoolean(userRegisteredEvent.getEnterprise().toString()));


        return instance;
    }

    @Override
    public boolean exist(Integer id) {
        return eventRep.existsById(id);
    }

    @Override
    public boolean existReplyFile(Integer id) {
        return eventResultImageRep.existsById(id);
    }

    @Transactional
    @Override
    public List<?> focusEvent(List<EventFocusVO> eventIds) {
        ApiBatchResult result = new ApiBatchResult();
        for (int i = 0; i < eventIds.size(); i++) {
            tbCoreEvent tbCoreEvent = null;
            EventFocusVO eventFocusVO = eventIds.get(i);
            Optional<tbCoreEvent> tbCoreEventOptional = eventRep.findById(eventFocusVO.getId());

            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            builder.put(ApiErrorConstant.INDEX, i);
            builder.put(ApiErrorConstant.ID, eventFocusVO.getId());

            if (!tbCoreEventOptional.isPresent()) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.eventNotFound.name(), ERROR_BUNDLE.eventNotFound.getDesc()));
            } else {
                tbCoreEvent = tbCoreEventOptional.get();
                if (userFocusedEventRep.countByUserIdAndFocusedEventId(getUserId(), tbCoreEvent.getId()) > 0) {
                    errors.add(
                            new ApiErrorResponse(ERROR_BUNDLE.eventAlreadyFocused.name(), ERROR_BUNDLE.eventAlreadyFocused.getDesc()));
                }
            }

            if (CollectionUtils.isEmpty(errors)) {
                try {
                    Integer eventFocusNum = tbCoreEvent.getFocusNum();
                    tbCoreEvent.setFocusNum(++eventFocusNum);
                    eventRep.save(tbCoreEvent);

                    tbCoreUserFocusedEvent tbCoreUserFocusedEvent = new tbCoreUserFocusedEvent(getUserId(), tbCoreEvent.getId(), new Timestamp(System.currentTimeMillis()));
                    userFocusedEventRep.save(tbCoreUserFocusedEvent);

                } catch (Exception ex) {
                    logger.error("An error occurred while update focusEvent, index:{}, error:{}", i,
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
    public List<?> deleteFocusEvent(List<String> eventIds) {
        ApiBatchResult result = new ApiBatchResult();

        for (int i = 0; i < eventIds.size(); i++) {
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            Integer eventId = Integer.valueOf(eventIds.get(i));
            builder.put(ApiErrorConstant.ID, eventId);
            Optional<tbCoreEvent> tbCoreEventOptional = eventRep.findById(eventId);
            if (!tbCoreEventOptional.isPresent()) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.eventNotFound.name(), ERROR_BUNDLE.eventNotFound.getDesc()));
            }

            if (userFocusedEventRep.countByUserIdAndFocusedEventId(getUserId(), eventId) == 0) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.eventNotFound.name(), ERROR_BUNDLE.eventNotFound.getDesc()));
            }

            if (CollectionUtils.isEmpty(errors)) {
                try {
                    tbCoreEvent tbCoreEvent = tbCoreEventOptional.get();
                    Integer eventFocusNum = tbCoreEvent.getFocusNum();
                    tbCoreEvent.setFocusNum(--eventFocusNum);
                    eventRep.save(tbCoreEvent);

                    userFocusedEventRep.deleteByUserIdAndFocusedEventId(getUserId(), eventId);

                } catch (Exception ex) {
                    logger.error("An error occurred while unFocus event, id:{}, error:{}", eventId, ex.getMessage());
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
    public List<?> joinEvent(List<EventJoinVO> eventJoinVOS) {
        ApiBatchResult result = new ApiBatchResult();

        for (int i = 0; i < eventJoinVOS.size(); i++) {
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            EventJoinVO eventJoinVO = eventJoinVOS.get(i);
            Integer userIDD = getUserId();
            if (eventJoinVO.getUserId() != null) {
                userIDD = eventJoinVO.getUserId();
            }

            Boolean giveScore = false;
            builder.put(ApiErrorConstant.UID, eventJoinVO.getUid());
            vwUserRegisteredEvent joinEventCount = vwUserRegisteredEventRep.findByUserIdAndUid(userIDD, eventJoinVO.getUid());
            tbCoreEvent tbCoreEvent = eventRep.findByUid(eventJoinVO.getUid());
            tbCoreUserRegisteredEvent userRegisteredEvent = null;
            Optional<tbCoreNpo> tbCoreNpoOptional = null;
            tbCoreNpo tbCoreNpo = null;
            tbCoreUserAccount tbCoreUserAccount = null;
            if (joinEventCount != null && joinEventCount.getJoined()) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.eventAlreadyJoin.name(), ERROR_BUNDLE.eventAlreadyJoin.getDesc()));
            }
            if (tbCoreEvent == null) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.eventNotFound.name(), ERROR_BUNDLE.eventNotFound.getDesc()));
            } else {
                userRegisteredEvent = userRegisteredEventRep.findByUserIdAndRegisteredEventId(userIDD, tbCoreEvent.getId());
                if (userRegisteredEvent == null) {
                    errors.add(
                            new ApiErrorResponse(ERROR_BUNDLE.registeredNotFound.name(), ERROR_BUNDLE.registeredNotFound.getDesc()));
                }
                tbCoreNpoOptional = npoRep.findById(tbCoreEvent.getOwnerNPOId());
                if (!tbCoreNpoOptional.isPresent()) {
                    errors.add(
                            new ApiErrorResponse(ERROR_BUNDLE.npoNotFound.name(), ERROR_BUNDLE.npoNotFound.getDesc()));
                } else {
                    tbCoreNpo = tbCoreNpoOptional.get();
                }
            }
            if (userAccountRep.countByUserId(userIDD) == 0) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.userNotFound.name(), ERROR_BUNDLE.userNotFound.getDesc()));
            } else {
                tbCoreUserAccount = userAccountRep.findByUserId(userIDD);
            }

            if (CollectionUtils.isEmpty(errors)) {
                try {
                    if (!tbCoreEvent.getVolunteerEvent() && userRegisteredEvent.getIsJoined() == 0) {
                        giveScore = true;
                    }
                    userRegisteredEvent.setIsJoined(1);
                    userRegisteredEvent.setJoinTime(new Timestamp(System.currentTimeMillis()));

                    userRegisteredEventRep.save(userRegisteredEvent);


                    if (giveScore) {
                        if (tbCoreNpo != null) {
                            Integer joinedUserNum = tbCoreNpo.getJoinedUserNum();
                            tbCoreNpo.setJoinedUserNum(++joinedUserNum);
                            npoRep.save(tbCoreNpo);
                        }

                        Integer userEventNum = tbCoreUserAccount.getEventNum() + 1;
                        Integer userScore = (tbCoreUserAccount.getScore() < 100) ? tbCoreUserAccount.getScore() + 1 : tbCoreUserAccount.getScore();

                        tbCoreUserAccount.setEventNum(userEventNum);
                        tbCoreUserAccount.setScore(userScore);

                        userAccountRep.save(tbCoreUserAccount);

                        saveScoreRecords(tbCoreEvent.getSubject(), tbCoreEvent.getId(), userIDD, tbCoreUserAccount.getScore(), "參加" + tbCoreEvent.getSubject() + "+1");
                    }


                } catch (Exception ex) {
                    logger.error("An error occurred while join event, id:{}, error:{}", eventJoinVO.getUid(), ex.getMessage());
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
    public List<?> leaveEvent(List<EventJoinVO> eventJoinVOS) {
        ApiBatchResult result = new ApiBatchResult();

        for (int i = 0; i < eventJoinVOS.size(); i++) {
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            EventJoinVO eventJoinVO = eventJoinVOS.get(i);
            Integer userIDD = getUserId();
            Boolean isEnterprise = false;
            Boolean giveScore = false;
            if (eventJoinVO.getUserId() != null) {
                userIDD = eventJoinVO.getUserId();
            }
            builder.put(ApiErrorConstant.UID, eventJoinVO.getUid());
            vwUserRegisteredEvent joinEventCount = vwUserRegisteredEventRep.findByUserIdAndUid(userIDD, eventJoinVO.getUid());
            tbCoreEvent tbCoreEvent = eventRep.findByUid(eventJoinVO.getUid());
            tbCoreUserRegisteredEvent userRegisteredEvent = null;
            tbCoreUserAccount tbCoreUserAccount = null;
            Optional<tbCoreNpo> tbCoreNpoOptional = null;
            tbCoreNpo tbCoreNpo = null;
            if (joinEventCount != null && !joinEventCount.getJoined()) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.eventJoinNotFound.name(), ERROR_BUNDLE.eventJoinNotFound.getDesc()));
            }
            if (joinEventCount != null && joinEventCount.getLeaved()) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.eventAlreadyLeave.name(), ERROR_BUNDLE.eventAlreadyLeave.getDesc()));
            }
            if (tbCoreEvent == null) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.eventNotFound.name(), ERROR_BUNDLE.eventNotFound.getDesc()));
            } else {
                userRegisteredEvent = userRegisteredEventRep.findByUserIdAndRegisteredEventId(userIDD, tbCoreEvent.getId());
                if (userRegisteredEvent == null) {
                    errors.add(
                            new ApiErrorResponse(ERROR_BUNDLE.registeredNotFound.name(), ERROR_BUNDLE.registeredNotFound.getDesc()));
                }
                tbCoreNpoOptional = npoRep.findById(tbCoreEvent.getOwnerNPOId());
                if (!tbCoreNpoOptional.isPresent()) {
                    errors.add(
                            new ApiErrorResponse(ERROR_BUNDLE.npoNotFound.name(), ERROR_BUNDLE.npoNotFound.getDesc()));
                } else {
                    tbCoreNpo = tbCoreNpoOptional.get();
                    isEnterprise = tbCoreNpo.getEnterprise();
                }
            }
            if (userAccountRep.countByUserId(userIDD) == 0) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.userNotFound.name(), ERROR_BUNDLE.userNotFound.getDesc()));
            } else {
                tbCoreUserAccount = userAccountRep.findByUserId(userIDD);
            }

            if (CollectionUtils.isEmpty(errors)) {
                try {
                    if (tbCoreEvent.getVolunteerEvent() && userRegisteredEvent.getIsJoined() == 1 && userRegisteredEvent.getIsLeaved() == 0) {
                        giveScore = true;
                    }
                    userRegisteredEvent.setIsLeaved(1);
                    userRegisteredEvent.setLeaveTime(new Timestamp(System.currentTimeMillis()));

                    userRegisteredEventRep.save(userRegisteredEvent);

                    if (giveScore) {
                        if (tbCoreNpo != null) {
                            Integer joinedUserNum = tbCoreNpo.getJoinedUserNum();
                            tbCoreNpo.setJoinedUserNum(++joinedUserNum);
                            npoRep.save(tbCoreNpo);
                        }

                        Integer userEventNum = tbCoreUserAccount.getEventNum() + 1;

                        Double eventHour = tbCoreEvent.getEventHour();
                        Double userEventHour = tbCoreUserAccount.getEventHour() + eventHour;
                        Double userEventEnterpriseHour = tbCoreUserAccount.getEventEnterpriseHour() + eventHour;
                        Double userEventGeneralHour = tbCoreUserAccount.getEventGeneralHour() + eventHour;
                        Integer userScore = (tbCoreUserAccount.getScore() < 100) ? tbCoreUserAccount.getScore() + 1 : tbCoreUserAccount.getScore();
                        tbCoreUserAccount.setEventHour(userEventHour);


                        if (isEnterprise) {
                            tbCoreUserAccount.setEventEnterpriseHour(userEventEnterpriseHour);
                        } else {
                            tbCoreUserAccount.setEventGeneralHour(userEventGeneralHour);
                        }

                        tbCoreUserAccount.setEventNum(userEventNum);
                        tbCoreUserAccount.setScore(userScore);
                        userAccountRep.save(tbCoreUserAccount);

                        saveScoreRecords(tbCoreEvent.getSubject(), tbCoreEvent.getId(), userIDD, tbCoreUserAccount.getScore(), "參加" + tbCoreEvent.getSubject() + "+1");
                    }


                } catch (Exception ex) {
                    logger.error("An error occurred while join event, id:{}, error:{}", eventJoinVO.getUid(), ex.getMessage());
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
    public List<?> visitEvent(List<EventFocusVO> eventIds) {
        ApiBatchResult result = new ApiBatchResult();
        for (int i = 0; i < eventIds.size(); i++) {
            EventFocusVO eventVisitVO = eventIds.get(i);
            Optional<tbCoreEvent> tbCoreEventOptional = eventRep.findById(eventVisitVO.getId());

            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            builder.put(ApiErrorConstant.INDEX, i);
            builder.put(ApiErrorConstant.ID, eventVisitVO.getId());

            if (!tbCoreEventOptional.isPresent()) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.eventNotFound.name(), ERROR_BUNDLE.eventNotFound.getDesc()));
            }

            if (CollectionUtils.isEmpty(errors)) {
                try {
                    tbCoreEventVisithistory tbCoreEventVisithistory = eventVisithistoryRep.findFirstByEventIdAndUserIdOrderByVisitTimeDesc(eventVisitVO.getId(), getUserId());
                    if (tbCoreEventVisithistory == null) {
                        tbCoreEventVisithistory = new tbCoreEventVisithistory();
                        tbCoreEventVisithistory.setUserId(getUserId());
                        tbCoreEventVisithistory.setEventId(eventVisitVO.getId());
                    }
                    tbCoreEventVisithistory.setVisitTime(new Timestamp(System.currentTimeMillis()));

                    eventVisithistoryRep.save(tbCoreEventVisithistory);

                } catch (Exception ex) {
                    logger.error("An error occurred while update visitEvent, index:{}, error:{}", i,
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
    public List<?> registerEvent(List<EventRegisterVO> eventRegisterVOS) {
        ApiBatchResult result = new ApiBatchResult();
        Long now = System.currentTimeMillis();
        String registerDate = DateUtils.getRegisterDateString(now);
        for (int i = 0; i < eventRegisterVOS.size(); i++) {
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            EventRegisterVO eventRegisterVO = eventRegisterVOS.get(i);
            builder.put(ApiErrorConstant.INDEX, i);
            builder.put(ApiErrorConstant.ID, eventRegisterVO.getId());
            Integer eventId = eventRegisterVO.getId();

            Optional<tbCoreEvent> tbCoreEventOptional = eventRep.findById(eventId);
            Optional<tbCoreNpo> tbCoreNpoOptional = null;
            tbCoreEvent tbCoreEvent = null;
            tbCoreNpo tbCoreNpo = null;
            Boolean isEnterprise = false;
            tbCoreUserRegisteredEvent userRegisteredEvent = userRegisteredEventRep.findByUserIdAndRegisteredEventId(getUserId(), eventId);
            if (!tbCoreEventOptional.isPresent()) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.eventNotFound.name(), ERROR_BUNDLE.eventNotFound.getDesc()));
            } else {
                tbCoreEvent = tbCoreEventOptional.get();
                tbCoreNpoOptional = npoRep.findById(tbCoreEvent.getOwnerNPOId());
                if (System.currentTimeMillis() > tbCoreEvent.getRegisterDeadlineDate().getTime()) {
                    errors.add(
                            new ApiErrorResponse(ERROR_BUNDLE.eventExpired.name(), ERROR_BUNDLE.eventExpired.getDesc()));
                }
                if (tbCoreNpoOptional.isPresent()) {
                    tbCoreNpo = tbCoreNpoOptional.get();
                    if (tbCoreNpo.getEnterprise()) {
                        isEnterprise = true;
                    }
                }
                if (!tbCoreEvent.getVolunteerEvent()) {
                    isEnterprise = false;
                }
            }

            if (userRegisteredEvent != null) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.eventRegisterExists.name(), ERROR_BUNDLE.eventRegisterExists.getDesc()));
            }

            if (tbCoreEvent != null) {
                if (tbCoreEvent.getVolunteerEvent() && eventRegisterVO.getBirthday() == null) {
                    errors.add(
                            new ApiErrorResponse(ERROR_BUNDLE.birthdayNotFound.name(), ERROR_BUNDLE.birthdayNotFound.getDesc()));
                }
                if (tbCoreEvent.getInsurance() && eventRegisterVO.getSecurityId() == null) {
                    errors.add(
                            new ApiErrorResponse(ERROR_BUNDLE.securityIdNotFound.name(), ERROR_BUNDLE.securityIdNotFound.getDesc()));
                }
                if (isEnterprise) {
                    if (eventRegisterVO.getEmployeeSerialNumber() == null) {
                        errors.add(
                                new ApiErrorResponse(ERROR_BUNDLE.employeeSerialNumberNotFound.name(), ERROR_BUNDLE.employeeSerialNumberNotFound.getDesc()));
                    }
                    if (eventRegisterVO.getEnterpriseSerialNumber() == null) {
                        errors.add(
                                new ApiErrorResponse(ERROR_BUNDLE.enterpriseSerialNumberNotFound.name(), ERROR_BUNDLE.enterpriseSerialNumberNotFound.getDesc()));
                    }
                }
                if (tbCoreEvent.getRequiredVolunteerNumber() != 0 && tbCoreEvent.getCurrentVolunteerNumber() >= tbCoreEvent.getRequiredVolunteerNumber()) {
                    errors.add(
                            new ApiErrorResponse(ERROR_BUNDLE.volunteerFulled.name(), ERROR_BUNDLE.volunteerFulled.getDesc()));
                }
            }

            if (eventRegisterVO.getEventSkillGroupId() != null) {
                Optional<tbCoreSkillGroup> tbCoreSkillGroupOptional = skillGroupRep.findById(eventRegisterVO.getEventSkillGroupId());
                if (!tbCoreSkillGroupOptional.isPresent()) {
                    errors.add(
                            new ApiErrorResponse(ERROR_BUNDLE.eventSkillGroupNotFound.name(), ERROR_BUNDLE.eventSkillGroupNotFound.getDesc()));
                } else {
                    tbCoreSkillGroup tbCoreSkillGroup = tbCoreSkillGroupOptional.get();
                    if (tbCoreSkillGroup.getCurrentVolunteerNumber() >= tbCoreSkillGroup.getVolunteerNumber()) {
                        errors.add(
                                new ApiErrorResponse(ERROR_BUNDLE.volunteerFulled.name(), ERROR_BUNDLE.volunteerFulled.getDesc()));
                    }
                }
            }

            if (eventRegisterVO.getEventSkillGroupList() != null) {
                if (!eventRegisterVO.getEventSkillGroupList().contains(":")) {
                    errors.add(
                            new ApiErrorResponse(ERROR_BUNDLE.eventSkillGroupListFormatError.name(), ERROR_BUNDLE.eventSkillGroupListFormatError.getDesc()));
                }
                String[] skillGroupList = eventRegisterVO.getEventSkillGroupList().split(",");
                for (int j = 0; j < skillGroupList.length; j++) {
                    if (!skillGroupList[j].contains(":")) {
                        continue;
                    }

                    String[] idAndCount = skillGroupList[j].split(":");
                    Optional<tbCoreSkillGroup> tbCoreSkillGroupOptional = skillGroupRep.findById(Integer.valueOf(idAndCount[0]));
                    if (!tbCoreEventOptional.isPresent()) {
                        errors.add(
                                new ApiErrorResponse(ERROR_BUNDLE.eventSkillGroupNotFound.name(), ERROR_BUNDLE.eventSkillGroupNotFound.getDesc()));
                    } else {
                        tbCoreSkillGroup tbCoreSkillGroup = tbCoreSkillGroupOptional.get();
                        if (tbCoreSkillGroup.getCurrentVolunteerNumber() + Integer.valueOf(idAndCount[1]) > tbCoreSkillGroup.getVolunteerNumber()) {
                            errors.add(
                                    new ApiErrorResponse(ERROR_BUNDLE.volunteerFulled.name(), ERROR_BUNDLE.volunteerFulled.getDesc()));
                        }
                    }
                }
            }

            if (CollectionUtils.isEmpty(errors)) {
                try {
                    String noteSkillGroup = "";
                    Integer provide_volunteer_number = 1;
                    if (eventRegisterVO.getEventSkillGroupList() != null && !eventRegisterVO.getEventSkillGroupList().trim().equalsIgnoreCase("")) {
                        String[] eventSkillGroupList = eventRegisterVO.getEventSkillGroupList().split(",");
                        provide_volunteer_number = 0;
                        for (int j = 0; j < eventSkillGroupList.length; j++) {
                            String[] idAndCount = eventSkillGroupList[j].split(":");
                            Integer skillGroupId = Integer.valueOf(idAndCount[0]);
                            Integer skillGroupCount = Integer.valueOf(idAndCount[1]);

                            Optional<tbCoreSkillGroup> tbCoreSkillGroupOptional = skillGroupRep.findById(skillGroupId);
                            if (tbCoreSkillGroupOptional.isPresent()) {
                                tbCoreSkillGroup tbCoreSkillGroup = tbCoreSkillGroupOptional.get();
                                Integer eventSkillGroupCurrentNum = tbCoreSkillGroup.getCurrentVolunteerNumber();
                                eventSkillGroupCurrentNum += skillGroupCount;
                                provide_volunteer_number += skillGroupCount;
                                tbCoreSkillGroup.setCurrentVolunteerNumber(eventSkillGroupCurrentNum);
                                skillGroupRep.save(tbCoreSkillGroup);
                                if (j == 0) {
                                    noteSkillGroup += String.format("%s %d 個", tbCoreSkillGroup.getName(), skillGroupCount);
                                } else {
                                    noteSkillGroup += String.format("、%s %d 個", tbCoreSkillGroup.getName(), skillGroupCount);
                                }
                            }
                        }
                    }

                    if (eventRegisterVO.getEventSkillGroupId() != null) {
                        Optional<tbCoreSkillGroup> tbCoreSkillGroupOptional = skillGroupRep.findById(eventRegisterVO.getEventSkillGroupId());
                        if (tbCoreSkillGroupOptional.isPresent()) {
                            tbCoreSkillGroup tbCoreSkillGroup = tbCoreSkillGroupOptional.get();
                            Integer eventSkillGroupCurrentNum = tbCoreSkillGroup.getCurrentVolunteerNumber();
                            eventSkillGroupCurrentNum = eventSkillGroupCurrentNum + 1;
                            tbCoreSkillGroup.setCurrentVolunteerNumber(eventSkillGroupCurrentNum);
                            skillGroupRep.save(tbCoreSkillGroup);
                            eventRegisterVO.setEventSkillGroupList(String.format("%s:1", eventRegisterVO.getEventSkillGroupId()));
                        }
                    }


                    Integer eventCurrentNum = tbCoreEvent.getCurrentVolunteerNumber();
                    eventCurrentNum += provide_volunteer_number;
                    tbCoreEvent.setCurrentVolunteerNumber(eventCurrentNum);
                    eventRep.save(tbCoreEvent);

                    tbCoreUserRegisteredEvent tbCoreUserRegisteredEvent = new tbCoreUserRegisteredEvent();

                    tbCoreUserRegisteredEvent.setUserId(getUserId());
                    tbCoreUserRegisteredEvent.setRegisteredEventId(eventId);
                    tbCoreUserRegisteredEvent.setIsJoined(0);
                    tbCoreUserRegisteredEvent.setScore(0);
                    tbCoreUserRegisteredEvent.setName(eventRegisterVO.getName());
                    tbCoreUserRegisteredEvent.setPhone(eventRegisterVO.getPhone());
                    tbCoreUserRegisteredEvent.setEmail(eventRegisterVO.getEmail());
                    tbCoreUserRegisteredEvent.setSkills((eventRegisterVO.getSkills() == null) ? "" : eventRegisterVO.getSkills());
                    tbCoreUserRegisteredEvent.setBirthday(eventRegisterVO.getBirthday());
                    tbCoreUserRegisteredEvent.setGuardianName((eventRegisterVO.getGuardianName() == null) ? "" : eventRegisterVO.getGuardianName());
                    tbCoreUserRegisteredEvent.setGuardianPhone((eventRegisterVO.getGuardianPhone() == null) ? "" : eventRegisterVO.getGuardianPhone());
                    tbCoreUserRegisteredEvent.setEventSkillGroupId(eventRegisterVO.getEventSkillGroupId());
                    tbCoreUserRegisteredEvent.setSecurityId((eventRegisterVO.getSecurityId() == null ? "" : eventRegisterVO.getSecurityId()));
                    tbCoreUserRegisteredEvent.setJoinTime(new Timestamp(now));
                    tbCoreUserRegisteredEvent.setLeaveTime(new Timestamp(now));
                    tbCoreUserRegisteredEvent.setIsLeaved(0);
                    tbCoreUserRegisteredEvent.setNote((eventRegisterVO.getNote() == null) ? noteSkillGroup : eventRegisterVO.getNote());
                    tbCoreUserRegisteredEvent.setRegisterDate(registerDate);
                    tbCoreUserRegisteredEvent.setEmployeeSerialNumber((eventRegisterVO.getEmployeeSerialNumber() == null) ? "" : eventRegisterVO.getEmployeeSerialNumber());
                    tbCoreUserRegisteredEvent.setEnterpriseSerialNumber((eventRegisterVO.getEnterpriseSerialNumber() == null) ? "" : eventRegisterVO.getEnterpriseSerialNumber());
                    tbCoreUserRegisteredEvent.setEventSkillGroupList((eventRegisterVO.getEventSkillGroupList() == null) ? "" : eventRegisterVO.getEventSkillGroupList());

                    userRegisteredEventRep.save(tbCoreUserRegisteredEvent);


                    tbCoreNpoOptional = npoRep.findById(tbCoreEvent.getOwnerNPOId());
                    tbCoreNpo = tbCoreNpoOptional.get();
                    String serverHostBackend = serverHost + "/backend";
                    String serverEventUrl = serverHost + "/event/" + tbCoreEvent.getId();

                    if (tbCoreEvent.getVolunteerEvent()) {
                        //send_volunteer_unregister_mail
                        String npoContent = mailUtils.getVolunteerRegisterMailTemplateNpo(tbCoreUserRegisteredEvent.getName(), tbCoreEvent.getSubject(), serverHostBackend);
                        mailUtils.sendMail(tbCoreNpo.getContactEmail(), String.format("微樂志工通知-[%s] 新增報名", tbCoreEvent.getSubject()), npoContent);

                        String memberContent = mailUtils.getVolunteerRegisterMailTemplateMember(tbCoreUserRegisteredEvent.getName(), tbCoreEvent.getSubject(), serverEventUrl, tbCoreEvent.getVolunteerTrainingDescription());
                        mailUtils.sendMail(tbCoreUserRegisteredEvent.getEmail(), "微樂志工平台 完成報名通知信", memberContent);
                    } else {
                        //send_item_unregister_mail
                        String npoContent = mailUtils.getItemRegisterMailTemplateNpo(tbCoreEvent.getSubject(), tbCoreUserRegisteredEvent.getName(), tbCoreUserRegisteredEvent.getPhone(), tbCoreUserRegisteredEvent.getEmail(), tbCoreUserRegisteredEvent.getNote(), serverHostBackend);
                        mailUtils.sendMail(tbCoreNpo.getContactEmail(), "微樂志工平台捐贈通知信", npoContent);

                        String memberContent = mailUtils.getItemRegisterMailTemplateMember(tbCoreUserRegisteredEvent.getName(), tbCoreNpo.getName(), tbCoreEvent.getSubject(), serverEventUrl, tbCoreEvent.getSkillsDescription(), tbCoreNpo.getContactEmail(), tbCoreNpo.getContactPhone(), tbCoreNpo.getContactAddress(), (tbCoreNpo.getContactWebsite() == null) ? "" : tbCoreNpo.getContactWebsite(), (tbCoreEvent.getNote() == null) ? "" : tbCoreEvent.getNote());
                        mailUtils.sendMail(tbCoreUserRegisteredEvent.getEmail(), "微樂志工平台 捐贈通知信", memberContent);
                    }


                } catch (Exception ex) {
                    logger.error("An error occurred while registerEvent event, id:{}, error:{}", eventId, ex.getMessage());
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
    public List<?> unregisterEvents(List<String> eventIds) {
        ApiBatchResult result = new ApiBatchResult();

        for (int i = 0; i < eventIds.size(); i++) {
            ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
            List<Object> errors = new ArrayList<Object>();
            Integer eventId = Integer.valueOf(eventIds.get(i));
            builder.put(ApiErrorConstant.ID, eventId);
            Optional<tbCoreEvent> tbCoreEventOptional = eventRep.findById(eventId);
            Optional<tbCoreNpo> tbCoreNpoOptional = null;
            tbCoreUserRegisteredEvent userRegisteredEvent = userRegisteredEventRep.findByUserIdAndRegisteredEventId(getUserId(), eventId);
            if (!tbCoreEventOptional.isPresent()) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.eventNotFound.name(), ERROR_BUNDLE.eventNotFound.getDesc()));
            } else {
                if (System.currentTimeMillis() > tbCoreEventOptional.get().getRegisterDeadlineDate().getTime()) {
                    errors.add(
                            new ApiErrorResponse(ERROR_BUNDLE.eventExpired.name(), ERROR_BUNDLE.eventExpired.getDesc()));
                }
            }

            if (userRegisteredEvent == null) {
                errors.add(
                        new ApiErrorResponse(ERROR_BUNDLE.eventRegisterNotFound.name(), ERROR_BUNDLE.eventRegisterNotFound.getDesc()));
            }


            if (CollectionUtils.isEmpty(errors)) {
                try {
                    Integer provide_volunteer_number = 1;
                    if (userRegisteredEvent.getEventSkillGroupList() != null && !userRegisteredEvent.getEventSkillGroupList().trim().equalsIgnoreCase("")) {
                        provide_volunteer_number = 0;
                        String[] eventSkillGroupList = userRegisteredEvent.getEventSkillGroupList().split(",");
                        for (int j = 0; j < eventSkillGroupList.length; j++) {
                            String[] idAndCount = eventSkillGroupList[j].split(":");
                            Integer skillGroupId = Integer.valueOf(idAndCount[0]);
                            Integer skillGroupCount = Integer.valueOf(idAndCount[1]);

                            Optional<tbCoreSkillGroup> tbCoreSkillGroupOptional = skillGroupRep.findById(skillGroupId);
                            if (tbCoreSkillGroupOptional.isPresent()) {
                                tbCoreSkillGroup tbCoreSkillGroup = tbCoreSkillGroupOptional.get();
                                Integer eventSkillGroupCurrentNum = tbCoreSkillGroup.getCurrentVolunteerNumber();
                                eventSkillGroupCurrentNum -= skillGroupCount;
                                provide_volunteer_number += skillGroupCount;
                                tbCoreSkillGroup.setCurrentVolunteerNumber(eventSkillGroupCurrentNum);
                                skillGroupRep.save(tbCoreSkillGroup);
                            }
                        }
                    }

                    tbCoreEvent tbCoreEvent = tbCoreEventOptional.get();
                    Integer eventCurrentNum = tbCoreEvent.getCurrentVolunteerNumber();
                    eventCurrentNum -= provide_volunteer_number;
                    tbCoreEvent.setCurrentVolunteerNumber(eventCurrentNum);
                    eventRep.save(tbCoreEvent);

                    tbCoreNpoOptional = npoRep.findById(tbCoreEvent.getOwnerNPOId());
                    tbCoreNpo tbCoreNpo = tbCoreNpoOptional.get();
                    String serverHostBackend = serverHost + "/backend";
                    String serverEventUrl = serverHost + "/event/" + tbCoreEvent.getId();
                    if (tbCoreEvent.getVolunteerEvent()) {
                        //send_volunteer_unregister_mail
                        String content = mailUtils.getUnRegisterEventEmailTemplate(userRegisteredEvent.getName(), tbCoreEvent.getSubject(), serverHostBackend);
                        mailUtils.sendMail(tbCoreNpo.getContactEmail(), String.format("微樂志工通知-[%s] 取消報名", tbCoreEvent.getSubject()), content);
                    } else {
                        //send_item_unregister_mail
                        String npoContent = mailUtils.getUnRegisterSupplyEventNpoEmailTemplate(userRegisteredEvent.getName(), tbCoreEvent.getSubject(), userRegisteredEvent.getNote(), userRegisteredEvent.getPhone(), userRegisteredEvent.getEmail(), serverHostBackend);
                        mailUtils.sendMail(tbCoreNpo.getContactEmail(), "微樂志工通知 取消捐贈通知", npoContent);

                        String memberContent = mailUtils.getUnRegisterSupplyEventMemberEmailTemplate(userRegisteredEvent.getName(), tbCoreNpo.getName(), tbCoreEvent.getSubject(), serverEventUrl, userRegisteredEvent.getNote(), tbCoreNpo.getContactEmail(), tbCoreNpo.getContactPhone(), tbCoreNpo.getContactAddress(), tbCoreNpo.getContactWebsite());
                        mailUtils.sendMail(userRegisteredEvent.getEmail(), "微樂志工平台 取消捐贈通知", memberContent);
                    }


                    userRegisteredEventRep.deleteByUserIdAndRegisteredEventId(getUserId(), eventId);

                } catch (Exception ex) {
                    logger.error("An error occurred while unregisterEvents event, id:{}, error:{}", eventId, ex.getMessage());
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
    public JSONObject findAllTags() {
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        Set<String> tagSet = new HashSet<>();
        List<tbCoreEvent> tbCoreEvents = eventRep.findAllByTagsIsNot("");
        for (int i = 0; i < tbCoreEvents.size(); i++) {
            String tags = tbCoreEvents.get(i).getTags();
            String[] tagList = tags.split(",");
            for (int j = 0; j < tagList.length; j++) {
                String tag = tagList[j];
                if (tagSet.contains(tag)) {
                    continue;
                }

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", tag);
                jsonArray.add(jsonObject);
                tagSet.add(tag);
            }
        }
        result.put("tags", jsonArray);
        return result;
    }

    @Transactional
    @Override
    public Boolean deleteVisitEvent() {
        Set<String> set = new HashSet<>();
        Iterable<tbCoreEventVisithistory> tbCoreEventVisithistories = eventVisithistoryRep.findAll();
        List<tbCoreEventVisithistory> tbCoreEventVisithistoryList = new ArrayList<>();
        List<tbCoreEventVisithistory> tbCoreEventVisithistoryDeleteList = new ArrayList<>();
        tbCoreEventVisithistories.forEach(tbCoreEventVisithistoryList::add);
        tbCoreEventVisithistoryList = tbCoreEventVisithistoryList.stream().sorted(Comparator.comparing(tbCoreEventVisithistory::getVisitTime).reversed()).collect(Collectors.toList());

        for (int i = 0; i < tbCoreEventVisithistoryList.size(); i++) {
            String key = tbCoreEventVisithistoryList.get(i).getUserId() + ":" + tbCoreEventVisithistoryList.get(i).getEventId();
            if (set.contains(key)) {
                tbCoreEventVisithistoryDeleteList.add(tbCoreEventVisithistoryList.get(i));
            } else {
                set.add(key);
            }

            if (i % 100 == 0) {
                eventVisithistoryRep.deleteAll(tbCoreEventVisithistoryDeleteList);
                tbCoreEventVisithistoryDeleteList = new ArrayList<>();
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        eventVisithistoryRep.deleteAll(tbCoreEventVisithistoryDeleteList);

        logger.info("Origin size {}", tbCoreEventVisithistoryList.size());
        logger.info("Delete size {}", tbCoreEventVisithistoryDeleteList.size());
        logger.info("Set size {}", set.size());
        return null;
    }

    @Override
    public List<?> getEventMenu() {
        Iterable<vwEventMenu> events = vwEventMenuRep.findAll();
        List<JSONObject> vwEventMenus = new ArrayList<>();

        for (vwEventMenu event : events) {
            com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
            jsonObject.put("id", event.getId());
            jsonObject.put("subject", event.getSubject());
            vwEventMenus.add(jsonObject);
        }

        return vwEventMenus;
    }

    private Boolean saveScoreRecords(String eventName, Integer eventId, Integer userId, Integer score, String comment) {
        Boolean saveResult = false;
        Timestamp now = new Timestamp(System.currentTimeMillis());
        tbCoreScoreRecords tbCoreScoreRecords = new tbCoreScoreRecords();
        tbCoreScoreRecords.setEventName(eventName);
        tbCoreScoreRecords.setEventId(eventId);
        tbCoreScoreRecords.setUserId(userId);
        tbCoreScoreRecords.setScore(score);
        tbCoreScoreRecords.setComment(comment);
        tbCoreScoreRecords.setAddDate(now);

        scoreRecordsRep.save(tbCoreScoreRecords);
        return saveResult;
    }
}