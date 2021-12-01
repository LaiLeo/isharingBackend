package com.fih.ishareing.service.event;

import com.alibaba.fastjson.JSONObject;
import com.fih.ishareing.repository.entity.vwEvent;
import com.fih.ishareing.repository.entity.vwUserRegisteredEvent;
import com.fih.ishareing.service.event.model.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface EventService {
    long count(Specification<vwEvent> spec);
    long countRegistered(Specification<vwUserRegisteredEvent> spec);
    boolean exist(Integer id);
    boolean existReplyFile(Integer id);

    List<EventVO> findEvents(Specification<vwEvent> spec, Pageable pageable);

    List<?> addEvents(List<EventAddVO> events);

    List<?> updateEvents(List<EventUpdateVO> events);

    List<?> deleteEvents(List<String> eventIds);

    List<EventRegisteredVO> findUserRegistered(Specification<vwUserRegisteredEvent> spec, Pageable pageable);

    List<?> updateUserRegistered(List<EventRegisteredUpdateVO> eventRegisteredUpdateVOList);

    List<?> addEventSkillGroups(List<EventSkillGroupAddVO> eventSkillGroups);

    List<?> updateEventSkillGroups(List<EventSkillGroupUpdateVO> eventSkillGroups);

    List<?> deleteEventSkillGroups(List<String> eventSkillGroupIds);

    void updateEventImg(Integer eventId, String img, String type);
    void updateEventResultFileImg(Integer eventId, String img, Integer displaySort);

    void removeEventImg(Integer eventId, String type);
    void removeEventResultFileImg(Integer eventReplyFileId);

    List<?> focusEvent(List<EventFocusVO> eventIds);

    List<?> deleteFocusEvent(List<String> eventIds);

    List<?> joinEvent(List<EventJoinVO> eventJoinVOS);

    List<?> leaveEvent(List<EventJoinVO> eventJoinVOS);

    List<?> visitEvent(List<EventFocusVO> eventIds);

    List<?> registerEvent(List<EventRegisterVO> eventRegisterVOS);

    List<?> unregisterEvents(List<String> eventIds);

    JSONObject findAllTags();

    Boolean deleteVisitEvent();

    List<?> getEventMenu();
}