package com.cf.service;

import com.cf.domain.Registration;
import com.cf.repository.RegistrationRepository;
import com.cf.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.sql.Date;
import java.util.List;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Inject
    private RegistrationRepository registrationRepository;
    @Inject
    UserRepository userRepository;

    @Override
    public long count() {
        return registrationRepository.count();
    }
    @Override
    public boolean exists(Long aLong) {
        return registrationRepository.exists(aLong);
    }

    //CREATE
    @Override
    public Registration save(Registration entity) {
        return registrationRepository.save(entity);
    }
    @Override
    public Iterable<Registration> save(Iterable<Registration> entities) {
        return registrationRepository.save(entities);
    }

    //UPDATE
    @Override
    @Transactional
    public Iterable<Registration> updateAll(Iterable<Registration> entities) {
        //registrationRepository.deleteAll();
        return registrationRepository.save(entities);
    }

    //READ
    @Override
    public Registration findById(Long id) {
        return registrationRepository.findOne(id);
    }
    @Override
    @Transactional(readOnly = true)
    public Iterable<Registration> findAll(Iterable<Long> longs) {
        return registrationRepository.findAll(longs);
    }
    @Override
    @Transactional(readOnly = true)
    public Iterable<Registration> findAll() {
        return registrationRepository.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public List<Registration> findRegistrationsByUserId(Long userId) {
        return registrationRepository.findRegistrationsByUserId(userId);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Registration> findRegistrationsByUserId(Long userId, Date startDate, Date endDate) {
        return registrationRepository.findRegistrationsByUserId(userId, startDate, endDate);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Registration> findRegistrationsByUsername(String username) {
        return registrationRepository.findRegistrationsByUsername(username);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Registration> findRegistrationsByUsername(String username, Date startDate, Date endDate){
        return registrationRepository.findRegistrationsByUsername(username, startDate, endDate);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Registration> findRegistrationsOfUserInstitution(String username){
        return registrationRepository.findRegistrationsOfUserInstitution(username);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Registration> findRegistrationsOfUserInstitution(String username, Date startDate, Date endDate){
        return registrationRepository.findRegistrationsOfUserInstitution(username, startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Registration> findRegistrationsOfUserInstitution(Long userId){
        return registrationRepository.findRegistrationsOfUserInstitution(userId);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Registration> findRegistrationsOfUserInstitution(Long userId, Date startDate, Date endDate){
        return registrationRepository.findRegistrationsOfUserInstitution(userId, startDate, endDate);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Registration> findRegistrationsOfManagerSubordinates(Long managerId){
        return registrationRepository.findOfManagerSubordinatesByManagerId(managerId);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Registration> findRegistrationsOfManagerSubordinates(Long managerId, Date startDate, Date endDate){
        return registrationRepository.findOfManagerSubordinatesByManagerId(managerId, startDate, endDate);

    }
    @Override
    @Transactional(readOnly = true)
    public List<Registration> findRegistrationsOfManagerSubordinates(String managerName){
        Long managerId = userRepository.findByUsername(managerName).getId();
        return registrationRepository.findOfManagerSubordinatesByManagerId(managerId);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Registration> findRegistrationsOfManagerSubordinates(String managerName, Date startDate, Date endDate){
        Long managerId = userRepository.findByUsername(managerName).getId();
        return registrationRepository.findOfManagerSubordinatesByManagerId(managerId, startDate, endDate);

    }

    //DELETE
    @Override
    public void delete(Long id) {
        registrationRepository.delete(id);
    }
    @Override
    public void delete(Registration entity) {
        registrationRepository.delete(entity);
    }
    @Override
    public void deleteAll() {
        registrationRepository.deleteAll();
    }
    @Override
    public void delete(Iterable<? extends Registration> entities) {
        registrationRepository.delete(entities);
    }

}
