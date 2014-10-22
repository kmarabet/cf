package com.cf.service;

import com.cf.domain.Registration;

import java.sql.Date;
import java.util.List;

public interface RegistrationService {

    public long count();
    public boolean exists(Long aLong);

    //CREATE
    public Registration save(Registration entity);
    public Iterable<Registration> save(Iterable<Registration> entities);

    //UPDATE
    public Iterable<Registration> updateAll(Iterable<Registration> entities);

    //READ
    public Registration findById(Long id);
    public Iterable<Registration> findAll(Iterable<Long> longs);
    public Iterable<Registration> findAll();
    public List<Registration> findRegistrationsByUserId(Long userId);
    public List<Registration> findRegistrationsByUserId(Long userId, Date startDate, Date endDate);
    public List<Registration> findRegistrationsByUsername(String username);
    public List<Registration> findRegistrationsByUsername(String username, Date startDate, Date endDate);
    public List<Registration> findRegistrationsOfUserInstitution(String username);
    public List<Registration> findRegistrationsOfUserInstitution(String username, Date startDate, Date endDate);
    public List<Registration> findRegistrationsOfUserInstitution(Long userId);
    public List<Registration> findRegistrationsOfUserInstitution(Long userId, Date startDate, Date endDate);
    public List<Registration> findRegistrationsOfManagerSubordinates(Long managerId);
    public List<Registration> findRegistrationsOfManagerSubordinates(Long managerId, Date startDate, Date endDate);
    public List<Registration> findRegistrationsOfManagerSubordinates(String managerName);
    public List<Registration> findRegistrationsOfManagerSubordinates(String managerName, Date startDate, Date endDate);

    //DELETE
    public void delete(Long id);
    public void deleteAll();
    public void delete(Iterable<? extends Registration> entities);
    public void delete(Registration entity);

}
