package com.fih.ishareing.controller.base;

import com.fih.ishareing.common.ApiConstants.PAGE_SIZE_LIMIT;
import com.fih.ishareing.controller.base.error.exception.BaseErrors;
import com.fih.ishareing.errorHandling.exceptions.ParameterException;
import com.fih.ishareing.repository.pocservice.entity.interfaces.BaseEntity;
import com.fih.ishareing.repository.pocservice.entity.interfaces.TimestampLogEntity;
import com.fih.ishareing.repository.pocservice.entity.interfaces.UserLogEntity;
import com.fih.ishareing.service.auth.AuthenticationUtils;
import com.fih.ishareing.utils.SpecificationUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author gregkckuo
 * @since 2017/07/25
 */
public abstract class BaseController {

    @Autowired(required = true)
    protected HttpServletRequest request;

    @Autowired
    protected SpecificationUtil specUtil;

    protected static final int FAKE_USER_ID = 1;
    protected static final int FAKE_ROLE_ID = 1;


    /**
     * 新增資料時,自動填入CreatedTime, CreatedUserCode, ModifiedTime, ModifiedUserCode
     *
     * @param userCode get user id by accessToken first.
     * @param entity   must be JPA entity extends AbstractCommonLogEntity, AbstractCommonLogWithoutIdEntity, AbstractDateLogEntity or AbstractUserLogEntity
     */
    protected <T extends BaseEntity> void fillSaveWithDateAndUser(String userCode, T entity) {
        if (entity instanceof TimestampLogEntity) {
            ((TimestampLogEntity) entity).setCreatedTime(new Timestamp(System.currentTimeMillis()));
            ((TimestampLogEntity) entity).setModifiedTime(new Timestamp(System.currentTimeMillis()));
        }
        if (entity instanceof UserLogEntity) {
            ((UserLogEntity) entity).setCreatedUserCode(userCode);
            ((UserLogEntity) entity).setModifiedUserCode(userCode);
        }
    }

    /**
     * 更新資料時,自動填入CreatedTime, CreatedUserCode, ModifiedTime, ModifiedUserCode
     *
     * @param userCode get user id by accessToken first.
     * @param entity   must be JPA entity extends AbstractCommonLogEntity, AbstractCommonLogWithoutIdEntity, AbstractDateLogEntity or AbstractUserLogEntity
     */
    protected <T extends BaseEntity> void fillUpdateWithDateAndUser(String userCode, T entity) {
        if (entity instanceof TimestampLogEntity) {
            ((TimestampLogEntity) entity).setModifiedTime(new Timestamp(System.currentTimeMillis()));
        }
        if (entity instanceof UserLogEntity) {
            ((UserLogEntity) entity).setModifiedUserCode(userCode);
        }
    }

    public String getCurrentAccessToken() {
        String accessToken = request.getHeader("X-Access-Token");
        return accessToken;
    }

    protected String getApplicationCode() {
        return AuthenticationUtils.getApplicationCode();
    }

    protected Pageable wrapPageable(Map<String, String> map, Pageable pageable, PAGE_SIZE_LIMIT limit) {
        int pageNumber = 0;
        int pageSize = limit.getSize();
        switch (limit) {
            case JSON:
                pageSize = 65535;
                break;
            case EXCEL:
                break;
        }
        Sort sort = null;
        if (null != pageable) {
            if (!PAGE_SIZE_LIMIT.JSON.equals(limit) && pageable.getPageSize() > limit.getSize()) {
                throw new ParameterException("Page size (" + pageable.getPageSize() + ") over the limit : " + limit.getSize() + ". You can add size=<integer number> in query string to change default value.");
            }
            if (null != pageable.getSort()) {
                List<Order> orders = StreamSupport.stream(pageable.getSort().spliterator(), false)
                        .map(o -> {
                            String column = o.getProperty();
                            String value = map.get(column);
                            if (null == value) {
                                throw new ParameterException("Sort column not found : " + column + ", Accepted Columns in [" + map.keySet().stream().collect(Collectors.joining(",")) + "]");
                            }
                            Order order = new Order(o.getDirection(), value);
                            return order;
                        }).collect(Collectors.toList());
                sort = Sort.by(orders);
            }
            pageNumber = pageable.getPageNumber();
            pageSize = pageable.getPageSize();
        }
        return PageRequest.of(pageNumber, pageSize, sort);
    }

    protected Map<String, String> appendCurrentUserConditionMap(Map<String, String> searchColumnMap) {
        Map<String, String> newSearchColumnMap = new HashMap<String, String>(searchColumnMap);
        newSearchColumnMap.put("user.id", "event.users.id");
        return newSearchColumnMap;
    }

    protected <ID> String getStringIdsFromBaseErrors(List<? extends BaseErrors<ID>> result) {
        String ids = "";
        if (CollectionUtils.isNotEmpty(result)) {
            ids = result.stream()
                    .filter(e -> CollectionUtils.isEmpty(e.getErrors()))
                    .map(e -> {
                        return e.getId().toString();
                    })
                    .collect(Collectors.joining(","));

            Iterator<? extends BaseErrors<ID>> it = result.iterator();
            while (it.hasNext()) {
                BaseErrors<ID> next = it.next();
                if (CollectionUtils.isEmpty(next.getErrors())) {
                    it.remove();
                }
            }
        }
        return ids;
    }

    protected String defaultObjectWithoutActiveSearch(String search, String applicationCode) {
        search = "applicationCode eq " + applicationCode + " and " + search;

        return search;
    }
}
