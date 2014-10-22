package com.cf.service;

import com.cf.domain.EmployeeType;
import com.cf.domain.WorkException;
import com.cf.repository.EmployeeTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.inject.Inject;
import java.util.List;

@Service
public class EmployeeTypeServiceImpl implements EmployeeTypeService{

    @Inject
    private EmployeeTypeRepository employeeTypeRepository;

    @Override
    public boolean exists(Long id) {
        return employeeTypeRepository.exists(id);
    }
    @Override
    public long count() {
        return employeeTypeRepository.count();
    }
    //CREATE
    @Override
    public EmployeeType save(EmployeeType employeeType) {
        return employeeTypeRepository.save(employeeType);
    }
    @Override
    @Transactional
    public Iterable<EmployeeType> save(Iterable<EmployeeType> employeeTypes) {
        return employeeTypeRepository.save(employeeTypes);
    }
    //READ
    @Override
    public EmployeeType findById(Long id) {
        return employeeTypeRepository.findOne(id);
    }
    @Override
    @Transactional(readOnly = true)
    public List<WorkException> findWorkExceptionsByEmployeeTypeId(final Long employeeTypeId){
        return employeeTypeRepository.findWorkExceptionsByEmployeeTypeId(employeeTypeId);
    }
    @Override
    @Transactional(readOnly = true)
    public Iterable<EmployeeType> findAll() {
        return employeeTypeRepository.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public Iterable<EmployeeType> findAll(Iterable<Long> longs) {
        return employeeTypeRepository.findAll(longs);
    }

    //UPDATE
    @Override
    public boolean update(EmployeeType entity) {
        if (!entity.isNew() && this.exists(entity.getId())){
            employeeTypeRepository.save(entity);
            return true;
        }
        return false;
    }
    @Override
    @Transactional
    public Iterable<EmployeeType> updateAll(Iterable<EmployeeType> entities) {
        employeeTypeRepository.deleteAll();
        return employeeTypeRepository.save(entities);
    }
    //DELETE
    @Override
    public void delete(Long id) {
        employeeTypeRepository.delete(id);
    }
    @Override
    public void delete(EmployeeType employeeType) {
        employeeTypeRepository.delete(employeeType);
    }
    @Override
    @Transactional
    public void delete(Iterable<EmployeeType> employeeTypes) {
        employeeTypeRepository.delete(employeeTypes);
    }
    @Override
    @Transactional
    public void deleteAll() {
        employeeTypeRepository.deleteAll();
    }
}
