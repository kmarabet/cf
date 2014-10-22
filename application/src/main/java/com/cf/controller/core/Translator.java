package com.cf.controller.core;

import java.util.ArrayList;
import java.util.List;


public abstract class Translator<E, D> {

    public List<D> translateToDtoList(Iterable<E> entityList) {
        List<D> dtoList = new ArrayList<>();
        for (E curItem : entityList) {
            dtoList.add(translateToDto(curItem));
        }
        return dtoList;
    }

    public abstract D translateToDto(E entity);

    public List<E> translateToDomainList(Iterable<D> dtoList) {
        List<E> entityList = new ArrayList<>();
        for (D curItem : dtoList) {
            entityList.add(translateToDomain(curItem));
        }
        return entityList;
    }

    public abstract E translateToDomain(D dto);

}
