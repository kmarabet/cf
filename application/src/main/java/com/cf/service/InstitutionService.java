package com.cf.service;

import com.cf.domain.Institution;


public interface InstitutionService {

    public long count();
    public boolean exists(Long id);

    //CREATE-UPDATE
    public Institution save(Institution entity);
    public Iterable<Institution> save(Iterable<Institution> entities);

    //READ
    public Institution findByUserId(Long id);
    public Iterable<Institution> findAll(Iterable<Long> ids);
    public Iterable<Institution> findAll();

    //DELETE
    public void delete(Long id);
    public void delete(Institution entity);
    public void deleteAll();
    public void delete(Iterable<? extends Institution> entities);

}
