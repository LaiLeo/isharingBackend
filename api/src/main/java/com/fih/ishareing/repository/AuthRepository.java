package com.fih.ishareing.repository;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import com.fih.ishareing.repository.entity.AuthToken;
import com.fih.ishareing.repository.pocservice.entity.User;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends BaseRepository<AuthToken, Integer> {
	
	
	AuthToken findByUserId(Integer userid);
	
//    @EntityGraph(value = "user.graph", type = EntityGraphType.FETCH)
//    Optional<User> findByCodeAndActiveTrueAndEnableTrue(UUID code);
//
//    
//    @EntityGraph(value = "user.graph", type = EntityGraphType.FETCH)
//    Optional<User> findByCodeAndActiveTrue(UUID code);
//
//    @EntityGraph(value = "user.graph", type = EntityGraphType.FETCH)
//    Optional<User> findByAccountAndActiveTrueAndEnableTrue(String account);
//
//    boolean existsByAccountAndActiveTrueAndEnableTrue(String account);
//
//    boolean existsByApplicationCodeAndActiveTrueAndAdminUserTrue(String code);
//
//    @EntityGraph(value = "user.graph", type = EntityGraphType.FETCH)
//    Optional<User> findByEmailAndActiveTrueAndEnableTrue(String email);
//
//    @Modifying
//    @Transactional
//    @Query(nativeQuery = true, value = "UPDATE public.users SET password=:password, force_reset_password=false, last_update_password_time=now() WHERE id=:id")
//    int modifyPassword(@Param("id") int id, @Param("password") String password);
//
//    @Modifying
//    @Transactional
//    @Query(nativeQuery = true, value = "UPDATE public.users SET validated=true, modified_time=now() WHERE id=:id")
//    int markEmailValidated(@Param("id") int id);
//
//    @Modifying
//    @Transactional
//    @Query(nativeQuery = true, value = "DELETE FROM public.user_roles WHERE user_id=:userId")
//    int deleteUserRoleLink(@Param("userId") int userId);
}