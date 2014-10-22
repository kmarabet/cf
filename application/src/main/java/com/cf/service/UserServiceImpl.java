package com.cf.service;

import com.cf.domain.Registration;
import com.cf.domain.User;
import com.cf.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserRepository userRepository;

    @Override
    public boolean exists(Long id) {
        return userRepository.exists(id);
    }
    @Override
    public long count() {
        return userRepository.count();
    }
    //CREATE
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
    @Override
    @Transactional
    public Iterable<User> save(Iterable<User> users) {
        return userRepository.save(users);
    }
    //READ
    @Override
    public User findById(Long id) {
        return userRepository.findOne(id);
    }
    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    @Override
    @Transactional(readOnly = true)
    public List<User> findUserColleagues(Long userId){
        return userRepository.findUserColleagues(userId);
    }
    @Override
    @Transactional(readOnly = true)
    public List<User> findUserColleagues(String username){
        return userRepository.findUserColleagues(username);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Registration> findRegistrations(Long userId) {
        return userRepository.findRegistrations(userId);
    }
    @Override
    @Transactional(readOnly = true)
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public Iterable<User> findAll(Iterable<Long> longs) {
        return userRepository.findAll(longs);
    }
    @Override
    @Transactional(readOnly = true)
    public Iterable<User> findManagersSubodinates(Long managerId) {
        return userRepository.findManagersSubordinates(managerId);
    }
    @Override
    @Transactional(readOnly = true)
    public Iterable<User> findManagersSubodinates(String managerUsername) {
        return userRepository.findManagersSubordinates(managerUsername);
    }

    //UPDATE
    @Override
    @Transactional
    public Iterable<User> updateAll(Iterable<User> entities) {
        userRepository.deleteAll();
        return userRepository.save(entities);
    }
    //DELETE
    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }
    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }
    @Override
    @Transactional
    public void delete(Iterable<User> users) {
        userRepository.delete(users);
    }
    @Override
    @Transactional
    public void deleteAll() {
        userRepository.deleteAll();
    }

}
