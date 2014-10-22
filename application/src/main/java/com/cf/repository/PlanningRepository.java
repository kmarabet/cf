package com.cf.repository;

import com.cf.domain.Planning;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.sql.Date;
import java.util.List;

public interface PlanningRepository extends CrudRepository<Planning, Long> {

    static String SELECT_BY_USER_ID = "select pl from Planning pl where pl.userId=?1";
    static String COUNT_BY_USER_ID = "select count(pl) from Planning pl where pl.userId=?1";
    static String SELECT_BY_USER_NAME = "select pl from Planning pl, User u where pl.userId = u.id and u.username=?1";
    static String SELECT_BY_MANAGER_ID = "select pl from Planning pl, User u where pl.userId = u.id and pl.userId in (select id from User where managerId=?1)";
    static String SELECT_OF_USER_INSTITUTION_BY_USER_NAME = "select pl from Planning pl where " +
            "pl.userId in (select id from User where institutionId = (select institutionId from User where username =?1))";

    //static String DATE_RANGE = " and pl.date>=?2 and pl.date<=?3";
    static String DATE_RANGE = " and pl.date between ?2 and ?3";
    static String FROM_DATE = " and pl.date >= ?2";
    static String CORRECTED = " and pl.corrected = 1";
    static String NOT_CORRECTED = " and pl.corrected is null or pl.corrected <> 1";
    static String SELECT_FROM_DATE_BY_USER_ID = "select pl from Planning pl where pl.userId=?1 and pl.date >=?2";

    static String DELETE_BY_USER_ID = "delete from Planning pl where pl.userId=?1";
    static String DELETE_FROM_DATE_BY_USER_ID = "delete from Planning pl where pl.userId=?1 and pl.date >=?2";
    static String SELECT_MAX_DATE_BY_USER_ID = "select max(pl.date) from Planning pl where pl.userId=?1";
    static String SELECT_MIN_DATE_BY_USER_ID = "select min(pl.date) from Planning pl where pl.userId=?1";

    static String SELECT_CORRECTIONS_BY_USER_ID = "select pl from Planning pl where pl.userId=?1";

    @Query(SELECT_BY_USER_ID)
    public List<Planning> findByUserId(Long userId);
    @Query(SELECT_BY_USER_ID + DATE_RANGE)
    public List<Planning> findByUserId(Long userId, Date startDate, Date endDate);
    @Query(SELECT_BY_USER_ID + FROM_DATE)
    public List<Planning> findByUserId(Long userId, Date fromDate);
    @Query(COUNT_BY_USER_ID + NOT_CORRECTED + FROM_DATE)
    public int countByUserId(Long userId, Date fromDate);

    @Query(SELECT_MAX_DATE_BY_USER_ID)
    public Date findMaxDateOfUser(Long userId);
    @Query(SELECT_MIN_DATE_BY_USER_ID)
    public Date findMinDateOfUser(Long userId);

    @Query(SELECT_BY_USER_NAME)
    public List<Planning> findByUsername(String username);
    @Query(SELECT_BY_USER_NAME + DATE_RANGE)
    public List<Planning> findByUsername(String username, Date startDate, Date endDate);

    @Query(SELECT_OF_USER_INSTITUTION_BY_USER_NAME)
    public List<Planning> findOfUserInstitution(String username);
    @Query(SELECT_OF_USER_INSTITUTION_BY_USER_NAME + DATE_RANGE)
    public List<Planning> findOfUserInstitution(String username, Date startDate, Date endDate);

    @Query(SELECT_BY_MANAGER_ID)
    public List<Planning> findOfManagerSubordinatesByManagerId(Long managerId);
    @Query(SELECT_BY_MANAGER_ID + DATE_RANGE)
    public List<Planning> findOfManagerSubordinatesByManagerId(Long managerId, Date startDate, Date endDate);

    @Query(SELECT_FROM_DATE_BY_USER_ID)
    public List<Planning> findFromDateByUserId(Long userId, Date fromDate);

    @Modifying
    @Query(DELETE_BY_USER_ID)
    public void deleteByUserId(Long userId);

    @Modifying
    @Query(DELETE_FROM_DATE_BY_USER_ID)
    public void deleteFromDateByUserId(Long userId, Date fromDate);

    @Query(SELECT_CORRECTIONS_BY_USER_ID + CORRECTED + DATE_RANGE)
    public List<Planning> findCorrectionsByUserId(Long userId, Date startDate, Date endDate);

}
