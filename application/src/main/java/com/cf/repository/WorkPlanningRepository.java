package com.cf.repository;

import com.cf.domain.WorkPlanning;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WorkPlanningRepository extends CrudRepository<WorkPlanning, Long> {

    static String SELECT_BY_USER_ID = "select pl from WorkPlanning pl where pl.userId=?1";
    static String SELECT_BY_USER_NAME = "select pl from WorkPlanning pl, User u where pl.userId = u.id and u.username=?1";
    static String SELECT_BY_MANAGER_ID = "select pl from WorkPlanning pl, User u where pl.userId = u.id and pl.userId in (select u2.id from User u2 where u2.managerId=?1)";
    static String DELETE_BY_USER_ID = "delete from WorkPlanning pl where pl.userId=?1";

    @Query(SELECT_BY_USER_ID)
    public List<WorkPlanning> findByUserId(Long userId);

    @Query(SELECT_BY_USER_NAME)
    public List<WorkPlanning> findByUsername(String username);

    @Query(SELECT_BY_MANAGER_ID)
    public List<WorkPlanning> findOfManagerSubordinatesByManagerId(Long managerId);

    @Modifying
    @Transactional
    @Query(DELETE_BY_USER_ID)
    public void deleteByUserId(Long userId);

}
