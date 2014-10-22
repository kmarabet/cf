package com.cf.service;

import com.cf.domain.Institution;
import com.cf.repository.InstitutionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.inject.Inject;


@Service
public class InstitutionServiceImpl implements InstitutionService {

    @Inject
    private InstitutionRepository institutionRepository;

    @Override
    public long count() {
        return institutionRepository.count();
    }
    @Override
    public boolean exists(Long aLong) {
        return institutionRepository.exists(aLong);
    }

    //CREATE-UPDATE
    @Override
    public Institution save(Institution entity) {
        return institutionRepository.save(entity);
    }
    @Override
    public Iterable<Institution> save(Iterable<Institution> entities) {
        return institutionRepository.save(entities);
    }

    //READ
    @Override
    public Institution findByUserId(Long id) {
        return institutionRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Institution> findAll(Iterable<Long> longs) {
        return institutionRepository.findAll(longs);
    }
    @Override
    @Transactional(readOnly = true)
    public Iterable<Institution> findAll() {
        return institutionRepository.findAll();
    }

    //DELETE
    @Override
    public void delete(Long id) {
        institutionRepository.delete(id);
    }
    @Override
    public void delete(Institution entity) {
        institutionRepository.delete(entity);
    }
    @Override
    public void deleteAll() {
        institutionRepository.deleteAll();
    }
    @Override
    public void delete(Iterable<? extends Institution> entities) {
        institutionRepository.delete(entities);
    }

}
