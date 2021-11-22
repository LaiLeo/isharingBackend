package com.fih.ishareing.service;

import com.fih.ishareing.common.ApiConstants;
import com.fih.ishareing.controller.base.error.exception.BaseErrors;
import com.fih.ishareing.controller.base.error.exception.ExceptionResponseVO;
import com.fih.ishareing.controller.base.error.exception.ParameterException;
import com.fih.ishareing.controller.base.vo.JsonBaseVO;
import com.fih.ishareing.controller.base.vo.result.IdErrors;
import com.fih.ishareing.controller.base.vo.result.IndexErrors;
import com.fih.ishareing.controller.base.vo.sync.AbsSync;
import com.fih.ishareing.errorHandling.BaseException;
import com.fih.ishareing.errorHandling.exceptions.TooManyResourcesException;
import com.fih.ishareing.repository.BaseRepository;
import com.fih.ishareing.repository.pocservice.entity.interfaces.BaseEntity;
import com.fih.ishareing.repository.pocservice.entity.interfaces.TimestampLogEntity;
import com.fih.ishareing.repository.pocservice.entity.interfaces.UserLogEntity;
import com.fih.ishareing.service.auth.model.UserAuthenticationVO;
import com.fih.ishareing.utils.FsnApiUtils;
import com.fih.ishareing.utils.Hasher;
import com.fih.ishareing.validators.CodeValidator;
import com.fih.ishareing.validators.IntegerAndCommaValidator;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.validation.Errors;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractService extends AbstractAuthenticationService {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractService.class);

    @Autowired
    private IntegerAndCommaValidator intCommaValidator;

    @Autowired
    private CodeValidator codeValidator;

    @Autowired
    protected Environment env;

    @Autowired
    protected FsnApiUtils fsnApiUtils;

    protected String urlCoreApiUrl="xxxxxx";
    @Autowired
    protected Hasher hasher;
    /**
     * 新增資料時,自動填入CreatedTime, CreatedUserCode, ModifiedTime, ModifiedUserCode
     *
     * @param userCode get user id by accessToken first.
     * @param entity must be JPA entity extends AbstractCommonLogEntity, AbstractCommonLogWithoutIdEntity, AbstractDateLogEntity or AbstractUserLogEntity
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
     * 包裝成Json物件,內含count和results
     *
     * @param spec     查詢條件
     * @param hasCount 是否包含count資訊
     * @param result   實際data結果(已包含分頁)
     * @param rep      有繼承BaseRepository的Repository
     * @return JsonBaseVO T代表result中的VO型別
     */
    protected <T> JsonBaseVO<T> wrapResult(Specification<?> spec, boolean hasCount, List<T> result, BaseRepository rep) {
        JsonBaseVO<T> wrapResult = new JsonBaseVO<T>();
        if (hasCount) {
            wrapResult.setCount(rep.count(spec));
        }
        wrapResult.setResults(result);
        return wrapResult;
    }

    protected <T> JsonBaseVO<T> wrapResult(boolean hasCount, long count, List<T> result) {
        JsonBaseVO<T> wrapResult = new JsonBaseVO<T>();
        if (hasCount) {
            wrapResult.setCount(count);
        }
        wrapResult.setResults(result);
        return wrapResult;
    }


    protected <T> List<T> getExcelResult(Specification<?> spec, Pageable pageable, BaseRepository rep) {
        long count = rep.count(spec);
        if (count > ApiConstants.PAGE_SIZE_LIMIT.EXCEL.getSize()) {
            throw new TooManyResourcesException("Return number : " + count + "(limit is : " + ApiConstants.PAGE_SIZE_LIMIT.EXCEL.getSize() + "). Please modify search conditions to reduce number.");
        }
        return rep.findAll(spec, pageable).getContent();
    }

    /**
     * 更新資料時,自動填入CreatedTime, CreatedUserCode, ModifiedTime, ModifiedUserCode
     *
     * @param userCode get user id by accessToken first.
     * @param entity must be JPA entity extends AbstractCommonLogEntity, AbstractCommonLogWithoutIdEntity, AbstractDateLogEntity or AbstractUserLogEntity
     */
    protected <T extends BaseEntity> void fillUpdateWithDateAndUser(String userCode, T entity) {
        if (entity instanceof TimestampLogEntity) {
            ((TimestampLogEntity) entity).setModifiedTime(new Timestamp(System.currentTimeMillis()));
        }
        if (entity instanceof UserLogEntity) {
            ((UserLogEntity) entity).setModifiedUserCode(userCode);
        }
    }

    protected UserAuthenticationVO checkAccessToken(String accessToken) {
        UserAuthenticationVO auth = new UserAuthenticationVO();
        auth.setApplicationCode(getApplicationCode());
        auth.setUserCode(getUserAccount());
        return auth;
    }

    protected <T> void genErrorsWithExceptions(BaseErrors<T> be, List<BaseException> exceptions) {
        if (Optional.of(exceptions).isPresent()) {
            be.setErrors(exceptions.stream().map(e -> {
                ExceptionResponseVO erVo = new ExceptionResponseVO(e);
                return erVo;
            }).collect(Collectors.toList()));
        }
    }

    protected <T> void genIdErrors(List<IdErrors> result, T id, Map<ApiConstants.RESOURCE_TYPE, Set<String>> resMap) {
        genIdErrors(result, id, null, resMap);
    }

    protected <T> void genIdErrors(List<IdErrors> result, T id, List<BaseException> exceptions, Map<ApiConstants.RESOURCE_TYPE, Set<String>> resMap) {
        IdErrors ce = new IdErrors();
        ce.setId(id);

        if (ce instanceof AbsSync) {
            AbsSync sync = (AbsSync) ce;
            sync.setAdditonalSyncResMap(resMap);
        }

        if (null != exceptions) {
            genErrorsWithExceptions(ce, exceptions);
        }
        result.add(ce);
    }

    protected Set<Integer> getDeleteIds(String ids) {
        String[] idArray = getIdArray(ids);
        Set<Integer> idSet = Arrays.stream(idArray).map(id -> {
            return Integer.valueOf(id);
        }).collect(Collectors.toSet());
        return idSet;
    }

    protected Set<String> getDeleteCodes(String codes) {
        String[] codeArray = codes.split(",");
        Set<String> codeSet = Arrays.stream(codeArray).collect(Collectors.toSet());
        return codeSet;
    }

    protected Set<Long> getDeleteLongIds(String ids) {
        String[] idArray = getIdArray(ids);

        Set<Long> idSet = Arrays.stream(idArray).map(id -> {
            return Long.valueOf(id);
        }).collect(Collectors.toSet());
        return idSet;
    }

    private String[] getIdArray(String ids) {
        if (!intCommaValidator.validate(ids)) {
            throw new ParameterException("Ids : " + ids);
        }
        String[] idArray = ids.split(",");
        if (null == idArray || idArray.length == 0) {
            throw new ParameterException("Ids is required.");
        }
        return idArray;
    }

    protected BaseException checkCode(String code) {
        BaseException exception = null;
        if (!codeValidator.validate(code)) {
            exception = new ParameterException("Code Data Format Error");
        }
        return exception;
    }

    protected boolean hasAnyErrors(List<BaseException> exceptions, Errors errors) {
        return !CollectionUtils.isEmpty(exceptions) || (errors != null && !errors.getAllErrors().isEmpty());
    }

    protected String getContextPaht() {
        return env.getProperty("server.location") + "/";
    }

    protected String getApiStoragePath() {
        return env.getProperty(ApiConstants.ENV_FILE_DIR_LOCATION);
    }

    protected String getApiDomainPath() {
        return env.getProperty("server.location");
    }

    protected <ID extends Serializable> void genIndexErrors(List<IndexErrors<ID>> result, Integer index, List<BaseException> exceptions, Map<ApiConstants.RESOURCE_TYPE, Set<String>> resMap) {
        genIndexErrors(result, index, exceptions, null, resMap);
    }

    protected <ID extends Serializable> void genIndexErrors(List<IndexErrors<ID>> result, Integer index, List<BaseException> exceptions, ID id, Map<ApiConstants.RESOURCE_TYPE, Set<String>> resMap) {
        IndexErrors<ID> ie = new IndexErrors<ID>();
        ie.setIndex(index);
        if (ie instanceof AbsSync) {
            AbsSync sync = (AbsSync) ie;
            sync.setAdditonalSyncResMap(resMap);
        }

        if (null != id) {
            ie.setId(id);
        }
        if (null != exceptions) {
            genErrorsWithExceptions(ie, exceptions);
        }
        result.add(ie);

    }
}
