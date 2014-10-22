package com.cf.service;

import com.cf.domain.EmployeeType;
import com.cf.domain.WorkException;
import java.util.List;

public interface EmployeeTypeService {

    public boolean exists(Long id);
    public long count();

    //CREATE
    public EmployeeType save(EmployeeType employeeType);
    public Iterable<EmployeeType> save(Iterable<EmployeeType> employeeTypes);

    //READ
    public EmployeeType findById(Long id);
    public List<WorkException> findWorkExceptionsByEmployeeTypeId(Long employeeTypeId);
    public Iterable<EmployeeType> findAll();
    public Iterable<EmployeeType> findAll(Iterable<Long> longs);

    //UPDATE
    public boolean update(EmployeeType entity);
    public Iterable<EmployeeType> updateAll(Iterable<EmployeeType> entities);

    //DELETE
    public void delete(Long id);
    public void delete(EmployeeType employeeType);
    public void delete(Iterable<EmployeeType> employeeTypes);
    public void deleteAll();

}
