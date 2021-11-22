package com.fih.ishareing.service.user;

import java.util.List;

import com.fih.ishareing.service.user.model.UserAddVO;
import com.fih.ishareing.repository.pocservice.entity.User;
import com.fih.ishareing.service.user.model.UserUpdateVO;
import com.fih.ishareing.service.user.model.UserVO;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface UserService {
    long count(Specification<User> spec);

    boolean exist(String account);

    List<UserVO> findUsers(Specification<User> spec, Pageable pageable);

    List<?> addUsers(List<UserAddVO> users);

    List<?> updateUsers(List<UserUpdateVO> users);

    List<?> deleteUsers(List<String> accounts);
}