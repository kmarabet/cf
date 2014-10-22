package com.cf.service;

import com.cf.domain.WorkPlanning;

import java.util.List;

public interface WorkPlanningService {

    public long count();
    public boolean exists(Long aLong);

    //CREATE
    public WorkPlanning save(WorkPlanning entity);
    public Iterable<WorkPlanning> save(Iterable<WorkPlanning> entities);

    //UPDATE
    public Iterable<WorkPlanning> updateAll(Iterable<WorkPlanning> entities);
    public boolean update(WorkPlanning entity);
    public Iterable<WorkPlanning> updateByUserId(Long userId, Iterable<WorkPlanning> entities);
    public Iterable<WorkPlanning> updateByUsername(String userName, Iterable<WorkPlanning> entities);

    //READ
    public WorkPlanning findById(Long id);
    public Iterable<WorkPlanning> findAll(Iterable<Long> longs);
    public Iterable<WorkPlanning> findAll();
    public List<WorkPlanning> findByUserId(Long userId);
    public List<WorkPlanning> findByUsername(String username);
    public List<WorkPlanning> findOfManagerSubordinatesByManagerId(Long managerId);

    //DELETE
    public void delete(Long id);
    public void deleteAll();
    public void delete(Iterable<? extends WorkPlanning> entities);
    public void delete(WorkPlanning entity);
    public void deleteByUserId(Long userId);
    public void deleteByUsername(String userName);

}
