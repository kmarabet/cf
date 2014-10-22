package com.cf.repository;

import com.cf.domain.Institution;
import com.cf.domain.Registration;
import com.cf.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    public User findByUsername(String username);

    @Query("select reg from Registration reg where reg.userId =?1")
    public List<Registration> findRegistrations(Long userId);

    //@Query("select i from Institution i inner join i.users us where us.id =?1")
    @Query("select i from Institution i, User us where i.id = us.institutionId and us.username =?1")
    public Institution findInstitution(String username);

    //@Query("select u from User u where u.usertypeId = 1")
    //@Query("select u from User u where u.usertypeName = 'Administrator'")
    @Query("select u from User u where u.managerId = null")
    public List<User> findManagers();

    @Query("select u from User u where u.managerId = ?1")
    public List<User> findManagersSubordinates(Long managerId);

    @Query("select u from User u where u.managerId in (select u.id from User u where u.username =?1)")
    public List<User> findManagersSubordinates(String managerUsername);

    @Query("select u from User u where u.managerId <> null")
    public List<User> findAllSubordinates();

    @Query("select u from User u where u.institutionId = (select u.institutionId from User u where u.id =?1)" +
            " and u.id <> ?1 and u.managerId is not null")
    public List<User> findUserColleagues(Long userId);

    @Query("select u from User u where u.institutionId = (select u.institutionId from User u where u.username =?1)" +
            " and u.username <> ?1 and u.managerId is not null")
    public List<User> findUserColleagues(String username);

}
