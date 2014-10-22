package com.cf.repository;

import com.cf.domain.Institution;
import com.cf.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface InstitutionRepository extends CrudRepository<Institution, Long> {

    @Query("select u from User u where u.institutionId =?1")
    public List<User> findUsers(Long institutionId);

}
