package com.cf.repository;

import com.cf.domain.CorrectionCause;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CorrectionCauseRepository extends CrudRepository<CorrectionCause, Long> {

    @Query("select cc from CorrectionCause cc where cc.active=1")
    List<CorrectionCause> findAllActive();
}
