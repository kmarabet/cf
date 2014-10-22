package com.cf.controller.dto;

import com.cf.domain.core.GenericEntity;
import com.cf.domain.WorkException;
import com.cf.utils.DateUtils;

import java.text.ParseException;

public class WorkExceptionDto extends GenericDto {

    String name;
    String startTime;
    String endTime;

    public WorkExceptionDto() {
    }
    public WorkExceptionDto(WorkException entity) {
        setFieldsFromEntity(entity);
    }

    @Override
    public void setFieldsFromEntity(GenericEntity entity){
        super.setFieldsFromEntity(entity);
        WorkException workExcep = (WorkException)entity;
        this.name = workExcep.getName();
        this.startTime = DateUtils.formatDateTime(workExcep.getStartTime());
        this.endTime = DateUtils.formatDateTime(workExcep.getEndTime());
    }

    @Override
    public WorkException getEntityFromDto() throws ParseException{
        return new WorkException(this);
    }

    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

}
