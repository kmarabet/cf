package com.cf.repository;


import com.cf.domain.EmployeeType;
import com.cf.domain.WorkException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface EmployeeTypeRepository extends CrudRepository<EmployeeType, Long> {

    @Query("select we from EmployeeType et, WorkException we where et.id = we.employeeType.id and et.id=?1")
    public List<WorkException> findWorkExceptionsByEmployeeTypeId(final Long employeeTypeId);

}
