package com.cf.service;

import com.cf.domain.CorrectionCause;


public interface CorrectionCauseService {

    public CorrectionCause find(Long id);

    public Iterable<CorrectionCause> findAll();

    public Iterable<CorrectionCause> findAllActive();

    public CorrectionCause save(CorrectionCause entity);

    public void delete(Long id);

    public void delete(CorrectionCause entity);
}
