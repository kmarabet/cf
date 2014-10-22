package com.cf.service;

import com.cf.domain.User;
import com.cf.domain.WorkPlanning;
import com.cf.repository.UserRepository;
import com.cf.repository.WorkPlanningRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.inject.Inject;
import java.util.List;

@Service
public class WorkPlanningServiceImpl implements WorkPlanningService {
    @Inject
    private WorkPlanningRepository workPlanningRepository;
    @Inject
    private UserRepository userRepository;

    @Override
    public long count() {
        return workPlanningRepository.count();
    }
    @Override
    public boolean exists(Long aLong) {
        return workPlanningRepository.exists(aLong);
    }

    //CREATE
    @Override
    public WorkPlanning save(WorkPlanning entity) {
        return workPlanningRepository.save(entity);
    }
    @Override
    public Iterable<WorkPlanning> save(Iterable<WorkPlanning> entities) {
        return workPlanningRepository.save(entities);
    }

    //UPDATE
    @Override
    @Transactional
    public Iterable<WorkPlanning> updateAll(Iterable<WorkPlanning> entities) {
        //workPlanningRepository.deleteAll();
        return workPlanningRepository.save(entities);
    }
    @Transactional
    @Override
    public boolean update(WorkPlanning entity) {
        if (!entity.isNew() && this.exists(entity.getId())){
            workPlanningRepository.save(entity);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public Iterable<WorkPlanning> updateByUserId(Long userId, Iterable<WorkPlanning> entities){

        for (final WorkPlanning workPlan : entities) {
            workPlan.setUserId(userId);
            /*workPlanListForSave.add(workPlan);
            if (workPlan.isNew()) { //CREATE
                this.save(workPlan);
                workPlanListForSave.add(workPlan);
            } else {  //UPDATE
                boolean updated = this.update(workPlan);
            }*/
        }
        //todo implement more efficient update with deleting only missing entities and saving(updating) the others
        //in order to prevent id auto generation
        this.deleteByUserId(userId);
        this.save(entities);
        return entities;
    }

    @Transactional
    @Override
    public Iterable<WorkPlanning> updateByUsername(String userName, Iterable<WorkPlanning> entities){

        User user = userRepository.findByUsername(userName);

        return this.updateByUserId(user.getId(), entities);
    }

    //READ
    @Override
    public WorkPlanning findById(Long id) {
        return workPlanningRepository.findOne(id);
    }
    @Override
    @Transactional(readOnly = true)
    public Iterable<WorkPlanning> findAll(Iterable<Long> longs) {
        return workPlanningRepository.findAll(longs);
    }
    @Override
    @Transactional(readOnly = true)
    public Iterable<WorkPlanning> findAll() {
        return workPlanningRepository.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public List<WorkPlanning> findByUserId(Long userId) {
        return workPlanningRepository.findByUserId(userId);
    }
    @Override
    @Transactional(readOnly = true)
    public List<WorkPlanning> findByUsername(String username) {
        return workPlanningRepository.findByUsername(username);
    }
    @Override
    @Transactional(readOnly = true)
    public List<WorkPlanning> findOfManagerSubordinatesByManagerId(Long managerId) {
        return workPlanningRepository.findOfManagerSubordinatesByManagerId(managerId);
    }

    //DELETE
    @Override
    public void delete(Long id) {
        workPlanningRepository.delete(id);
    }
    @Override
    public void delete(WorkPlanning entity) {
        workPlanningRepository.delete(entity);
    }
    @Override
    public void deleteAll() {
        workPlanningRepository.deleteAll();
    }
    @Override
    public void delete(Iterable<? extends WorkPlanning> entities) {
        workPlanningRepository.delete(entities);
    }
    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        workPlanningRepository.deleteByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteByUsername(String userName){
        User user = userRepository.findByUsername(userName);
        this.deleteByUserId(user.getId());
    }

}
