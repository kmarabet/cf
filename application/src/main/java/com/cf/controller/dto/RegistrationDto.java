package com.cf.controller.dto;

import com.google.gson.annotations.Expose;
import com.cf.domain.Registration;
import com.cf.domain.core.GenericEntity;
import com.cf.utils.DateUtils;

import javax.persistence.Transient;
import java.text.ParseException;


public class RegistrationDto extends BaseTimeRegistrationDto {

    @Expose
    protected String date;

    @Transient
    private boolean corrected = false;
    @Transient
    private boolean planned = false;

    public RegistrationDto() {
    }
    public RegistrationDto(Registration registration) {
        this.setFieldsFromEntity(registration);
        this.date = DateUtils.formatDate(registration.getDate());
    }

    @Override
    public void setFieldsFromEntity(GenericEntity entity){
        super.setFieldsFromEntity(entity);
        Registration registration = (Registration)entity;
        this.date = DateUtils.formatDate(registration.getDate());
    }

    public GenericEntity getEntityFromDto() throws ParseException{
        return new Registration(this);
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegistrationDto)) return false;
        if (!super.equals(o)) return false;

        RegistrationDto that = (RegistrationDto) o;

        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (timeIn != null ? !timeIn.equals(that.timeIn) : that.timeIn != null) return false;
        if (timeOut != null ? !timeOut.equals(that.timeOut) : that.timeOut != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (timeIn != null ? timeIn.hashCode() : 0);
        result = 31 * result + (timeOut != null ? timeOut.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }
}
