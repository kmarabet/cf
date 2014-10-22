package com.cf.service;

import com.cf.domain.Registration;
import com.cf.domain.User;

import java.util.List;

public interface UserService {

    public boolean exists(Long id);
    public long count();

    //CREATE
    public User save(User user);
    public Iterable<User> save(Iterable<User> users);

    //READ
    public User findById(Long id);
    public User findByUsername(String username);
    public List<User> findUserColleagues(Long userId);
    public List<User> findUserColleagues(String username);
    public List<Registration> findRegistrations(Long userId);
    public Iterable<User> findAll();
    public Iterable<User> findAll(Iterable<Long> longs);
    public Iterable<User> findManagersSubodinates(Long managerId);
    public Iterable<User> findManagersSubodinates(String managerUsername);

    //UPDATE
    public Iterable<User> updateAll(Iterable<User> entities);

    //DELETE
    public void delete(Long id);
    public void delete(User user);
    public void delete(Iterable<User> users);
    public void deleteAll();

}
