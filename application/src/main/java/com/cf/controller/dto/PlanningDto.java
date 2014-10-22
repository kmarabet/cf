package com.cf.controller.dto;

import com.google.gson.annotations.Expose;
import com.cf.domain.CorrectionCause;
import com.cf.domain.Planning;
import com.cf.domain.core.GenericEntity;
import com.cf.utils.DateUtils;

import java.text.ParseException;

public class PlanningDto extends BaseTimeRegistrationDto {

    @Expose
    protected String date;
    Boolean corrected;
    CorrectionCause correctionCause;

    public PlanningDto() {
    }
    public PlanningDto(Planning planning) {
        this.setFieldsFromEntity(planning);
    }

    @Override
    public void setFieldsFromEntity(GenericEntity entity){
        super.setFieldsFromEntity(entity);
        Planning planning = (Planning)entity;
        this.date = DateUtils.formatDate(planning.getDate());
        this.corrected = planning.isCorrected();
        this.correctionCause = planning.getCorrectionCause();
    }

    public GenericEntity getEntityFromDto() throws ParseException{
        return new Planning(this);
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public Boolean isCorrected() {
        return corrected;
    }
    public void setCorrected(Boolean corrected) {
        this.corrected = corrected;
    }

    public CorrectionCause getCorrectionCause() {
        return correctionCause;
    }
    public void setCorrectionCause(CorrectionCause correctionCause) {
        this.correctionCause = correctionCause;
    }
}
