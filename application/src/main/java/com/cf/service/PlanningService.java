package com.cf.service;

import com.cf.domain.Planning;
import com.cf.domain.WorkPlanning;

import java.sql.Date;
import java.util.List;

public interface PlanningService {

    public long count();
    public boolean exists(Long id);

    //CREATE
    public Planning save(Planning entity);
    public Iterable<Planning> save(Iterable<Planning> entities);

    //UPDATE
    public boolean update(Planning entity);
    public Iterable<Planning> updateAll(Iterable<Planning> entities);
    public Iterable<Planning> applyWorkPlannings(final Long userId, final List<WorkPlanning> workPlannings, final Date startDate, final Date endDate, Integer occurrences);
    public Iterable<Planning> applyWorkPlanningsFromDate(final Long userId, final List<WorkPlanning> workPlannings, final Date fromDate);
    public void cleanWorkPlanningsFromDate(final Long userId, final Date fromDate);

    //READ
    public Planning findById(Long id);
    public Iterable<Planning> findAll(Iterable<Long> ids);
    public Iterable<Planning> findAll();
    public List<Planning> findPlanningsByUserId(Long userId);
    public Date findPlanningsByUserIdWithMaxDate(final Long userId);
    public List<Planning> findPlanningsByUserId(Long userId, Date startDate, Date endDate);
    public List<Planning> findPlanningsByUserId(Long userId, Date fromDate);
    public int countPlanningsByUserId(Long userId, Date fromDate);
    public List<Planning> findPlanningsByUsername(String username);
    public List<Planning> findPlanningsByUsername(String username, Date startDate, Date endDate);
    public List<Planning> findPlanningsOfUserInstitution(String username);
    public List<Planning> findPlanningsOfUserInstitution(String username, Date startDate, Date endDate);
    public List<Planning> findPlanningsOfManagerSubordinates(Long managerId);
    public List<Planning> findPlanningsOfManagerSubordinates(Long managerId, Date startDate, Date endDate);
    public List<Planning> findPlanningsOfManagerSubordinates(String managerName);
    public List<Planning> findPlanningsOfManagerSubordinates(String managerName, Date startDate, Date endDate);

    //DELETE
    public void delete(Long id);
    public void delete(Planning entity);
    public void deleteAll();
    public void delete(Iterable<? extends Planning> entities);
    public void deleteByUserId(Long userId);
    public void deleteFromDateByUserId(final Long userId, final Date fromDate);

    List<Planning> findCorrectionsByUserId(Long userId, Date startDate, Date endDate);

}
