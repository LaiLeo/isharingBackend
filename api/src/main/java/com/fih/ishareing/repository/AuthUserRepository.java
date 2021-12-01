package com.fih.ishareing.repository;

import com.fih.ishareing.repository.entity.tbAuthUser;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthUserRepository extends BaseRepository<tbAuthUser, Integer> {
	
	tbAuthUser findByid(Integer id);
	
	tbAuthUser findByUsername(String username);
	
	tbAuthUser findByUsernameAndIsActive(String username, Integer isactive);
	
	Optional<tbAuthUser> findByUsernameAndPassword(String username, String password);
	
    boolean existsByUsernameAndIsActive(String account, Integer isactive);
    
    boolean existsByIdAndIsActive(Integer id, Integer isactive);

    @Modifying
	@Transactional
	@Query(nativeQuery = true, value = "DELETE FROM auth_user WHERE id=?1 ")
	int deleteAllByID(Integer id);

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