package com.cf.service;

import com.cf.domain.CorrectionCause;
import com.cf.repository.CorrectionCauseRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;


@Service
public class CorrectionCauseServiceImpl implements CorrectionCauseService {
    @Inject
    private CorrectionCauseRepository repository;

    @Override
    public CorrectionCause find(Long id) {
        return repository.findOne(id);
    }

    @Override
    public Iterable<CorrectionCause> findAll() {
        return repository.findAll();
    }

    @Override
    public Iterable<CorrectionCause> findAllActive() {
        return repository.findAllActive();
    }

    @Override
    public CorrectionCause save(CorrectionCause entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        CorrectionCause cause = find(id);
        cause.setActive(false);
        save(cause);
    }

    @Override
    public void delete(CorrectionCause entity) {
        entity.setActive(false);
        save(entity);
    }
}
