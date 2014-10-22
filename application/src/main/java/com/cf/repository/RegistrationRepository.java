package com.cf.repository;

import com.cf.domain.Registration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;


public interface RegistrationRepository extends CrudRepository<Registration, Long> {

    static String SELECT_BY_USER_ID = "select reg from Registration reg where reg.userId=?1";
    static String SELECT_BY_USER_NAME = "select reg from Registration reg, User u where reg.userId = u.id and u.username=?1";//with joined tables
    //static String SELECT_BY_USER_NAME = "select reg from Registration reg where reg.userId = (select id from User where username=?1)";// with nested request

    static String SELECT_OF_USER_INSTITUTION_BY_USER_NAME = "select reg from Registration reg where " +
            "reg.userId in (select id from User where institutionId = (select institutionId from User where username =?1))";
    static String SELECT_OF_USER_INSTITUTION_BY_USER_ID = "select reg from Registration reg where " +
            "reg.userId in (select id from User where institutionId = (select institutionId from User where id =?1))";

    static String SELECT_BY_MANAGER_ID = "select reg from Registration reg, User u where reg.userId = u.id and reg.userId in (select id from User where managerId=?1)";

    //static String DATE_RANGE = " and pl.date>=?2 and pl.date<=?3";
    static String DATE_RANGE = " and reg.date between ?2 and ?3";

    @Query(SELECT_BY_USER_ID)
    public List<Registration> findRegistrationsByUserId(Long userId);
    @Query(SELECT_BY_USER_ID + DATE_RANGE)
    public List<Registration> findRegistrationsByUserId(Long userId, Date startDate, Date endDate);

    @Query(SELECT_BY_USER_NAME)
    public List<Registration> findRegistrationsByUsername(String username);
    @Query(SELECT_BY_USER_NAME + DATE_RANGE)
    public List<Registration> findRegistrationsByUsername(String username, Date startDate, Date endDate);

    @Query(SELECT_OF_USER_INSTITUTION_BY_USER_NAME)
    public List<Registration> findRegistrationsOfUserInstitution(String username);
    @Query(SELECT_OF_USER_INSTITUTION_BY_USER_NAME + DATE_RANGE)
    public List<Registration> findRegistrationsOfUserInstitution(String username, Date startDate, Date endDate);

    @Query(SELECT_OF_USER_INSTITUTION_BY_USER_ID)
    public List<Registration> findRegistrationsOfUserInstitution(Long userId);
    @Query(SELECT_OF_USER_INSTITUTION_BY_USER_ID + DATE_RANGE)
    public List<Registration> findRegistrationsOfUserInstitution(Long userId, Date startDate, Date endDate);

    @Query(SELECT_BY_MANAGER_ID)
    public List<Registration> findOfManagerSubordinatesByManagerId(Long managerId);
    @Query(SELECT_BY_MANAGER_ID + DATE_RANGE)
    public List<Registration> findOfManagerSubordinatesByManagerId(Long managerId, Date startDate, Date endDate);

}
