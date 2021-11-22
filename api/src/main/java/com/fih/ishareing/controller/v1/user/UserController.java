package com.fih.ishareing.controller.v1.user;

import com.fih.ishareing.common.ApiConstants.PAGE_SIZE_LIMIT;
import com.fih.ishareing.constant.Constant;
import com.fih.ishareing.controller.base.BaseController;
import com.fih.ishareing.controller.base.vo.JsonBaseVO;
import com.fih.ishareing.repository.criteria.QueryConditionUtils;
import com.fih.ishareing.repository.criteria.SearchCriteria;
import com.fih.ishareing.repository.pocservice.entity.User;
import com.fih.ishareing.service.auth.AuthenticationUtils;
import com.fih.ishareing.service.user.UserService;
import com.fih.ishareing.service.user.model.SimpleUserVO;
import com.fih.ishareing.service.user.model.UserAddVO;
import com.fih.ishareing.service.user.model.UserUpdateVO;
import com.fih.ishareing.service.user.model.UserVO;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    private Map<String, String> userSortable = new HashMap<String, String>();
    private Map<String, String> userSearchable = new HashMap<String, String>();

    @PostConstruct
    private void init() {
        this.userSortable = QueryConditionUtils.getSortColumn(UserVO.class);
        this.userSearchable = QueryConditionUtils.getSearchColumn(UserVO.class);
    }

    @GetMapping()
    public JsonBaseVO<UserVO> findUsers(@RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "inlinecount", required = false, defaultValue = "true") boolean hasCount,
            Pageable pageable) {

        JsonBaseVO<UserVO> result = new JsonBaseVO<>();
        ImmutableList.Builder<SearchCriteria> builder = ImmutableList.builder();
        builder.add(new SearchCriteria("active", "eq", "true"));
        if (AuthenticationUtils.isSu()) {
            builder.add(new SearchCriteria("adminUser", "eq", "true"));
        } else {
            builder.add(new SearchCriteria("applicationCode", "eq", getApplicationCode()));
        }

        Specification<User> spec = specUtil.getSpecification(this.userSearchable, search, User.class, builder.build());
        List<UserVO> users = userService.findUsers(spec, wrapPageable(userSortable, pageable, PAGE_SIZE_LIMIT.JSON));
        result.setResults(users);
        if (hasCount) {
            result.setCount(userService.count(spec));
        }

        return result;
    }

    @GetMapping(value = "/menu")
    public JsonBaseVO<SimpleUserVO> findSimpleUsers(@RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "inlinecount", required = false, defaultValue = "true") boolean hasCount,
            Pageable pageable) {

        ImmutableList.Builder<SearchCriteria> builder = ImmutableList.builder();
        builder.add(new SearchCriteria("active", "eq", "true"));
        if (AuthenticationUtils.isSu()) {
            builder.add(new SearchCriteria("adminUser", "eq", "true"));
        } else {
            builder.add(new SearchCriteria("applicationCode", "eq", getApplicationCode()));
        }

        JsonBaseVO<SimpleUserVO> result = new JsonBaseVO<>();
        Specification<User> spec = specUtil.getSpecification(this.userSearchable, search, User.class, builder.build());
        List<SimpleUserVO> users = userService
                .findUsers(spec, wrapPageable(userSortable, pageable, PAGE_SIZE_LIMIT.JSON)).stream().map(m -> {
                    SimpleUserVO user = new SimpleUserVO();
                    user.setId(m.getId());
                    user.setCode(m.getCode());
                    user.setAccount(m.getAccount());
                    user.setActive(m.getActive());
                    user.setEnable(m.getEnable());
                    user.setName(m.getName());
                    user.setValidated(m.getValidated());
                    return user;
                }).collect(Collectors.toList());
        result.setResults(users);
        if (hasCount) {
            result.setCount(userService.count(spec));
        }

        return result;
    }

    @PostMapping()
    public List<?> addUsers(@RequestBody List<UserAddVO> users) {
        return userService.addUsers(users);
    }

    @PatchMapping()
    public List<?> updateUsers(@RequestBody List<UserUpdateVO> users) {
        return userService.updateUsers(users);
    }

    @DeleteMapping("/{code}/active")
    public List<?> addUsers(@PathVariable("code") String code) {
        return userService.deleteUsers(Constant.commaSpitter.splitToList(code));
    }

}
