package com.cf.controller.dto;

import com.cf.domain.WorkPlanning;
import com.cf.domain.core.GenericEntity;
import java.text.ParseException;

public class WorkPlanningDto extends BaseTimeRegistrationDto {

    private Integer day;
    private Integer week;

    public WorkPlanningDto() {
    }
    public WorkPlanningDto(WorkPlanning planning) {
        this.setFieldsFromEntity(planning);
    }

    @Override
    public void setFieldsFromEntity(GenericEntity entity){
        super.setFieldsFromEntity(entity);
        WorkPlanning workPlan = (WorkPlanning)entity;
        this.day = workPlan.getDay();
        this.week = workPlan.getWeek();
    }

    public GenericEntity getEntityFromDto() throws ParseException{
        return new WorkPlanning(this);
    }

    public Integer getDay() {
        return day;
    }
    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getWeek() {
        return week;
    }
    public void setWeek(Integer week) {
        this.week = week;
    }


}
