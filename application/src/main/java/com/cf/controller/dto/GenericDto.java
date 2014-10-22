package com.cf.controller.dto;

import com.google.gson.annotations.Expose;
import com.cf.domain.core.GenericEntity;
import java.text.ParseException;

public abstract class GenericDto {

    /*@Expose(serialize = false, deserialize = false)
    private GenericEntity entity;*/
    @Expose
    Long id;
    @Expose
    String errorMessage;

    public GenericDto() {
    }
    public GenericDto(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public GenericDto(GenericEntity entity) {
        this.setFieldsFromEntity(entity);
    }

    public void setFieldsFromEntity(GenericEntity entity){
        this.id = entity.getId();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public GenericEntity getEntityFromDto() throws ParseException {
        return new GenericEntity();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenericDto)) return false;

        GenericDto that = (GenericDto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
