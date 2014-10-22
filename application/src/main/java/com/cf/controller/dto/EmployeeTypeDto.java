package com.cf.controller.dto;

import com.cf.domain.EmployeeType;
import com.cf.domain.core.GenericEntity;
import com.cf.utils.ValidationDtoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

public class EmployeeTypeDto extends GenericDto {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeTypeDto.class);

    String name;
    Integer weeklyWorkHours;
    //Integer normTime;//??
    WorkExceptionDto [] workExceptions;

    public EmployeeTypeDto() {
    }

    public EmployeeTypeDto(EmployeeType entity) {
        setFieldsFromEntity(entity);
    }

    @Override
    public void setFieldsFromEntity(GenericEntity entity){
        super.setFieldsFromEntity(entity);
        EmployeeType e = (EmployeeType)entity;
        this.name = e.getName();
        this.weeklyWorkHours = e.getWeeklyWorkHours();
        if (e.getWorkExceptions() != null && !e.getWorkExceptions().isEmpty()){
            List<WorkExceptionDto> workExceptionDtos = ValidationDtoUtils.getDtoListFromEntityList(e.getWorkExceptions(), WorkExceptionDto.class, logger);
            this.workExceptions = workExceptionDtos.toArray(new WorkExceptionDto []{});
        }
    }

    @Override
    public EmployeeType getEntityFromDto() throws ParseException {
        return new EmployeeType(this);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeeklyWorkHours() {
        return weeklyWorkHours;
    }
    public void setWeeklyWorkHours(Integer weeklyWorkHours) {
        this.weeklyWorkHours = weeklyWorkHours;
    }

    public WorkExceptionDto[] getWorkExceptions() {
        return workExceptions;
    }
    public void setWorkExceptions(WorkExceptionDto[] workExceptions) {
        this.workExceptions = workExceptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeTypeDto)) return false;
        if (!super.equals(o)) return false;

        EmployeeTypeDto that = (EmployeeTypeDto) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (weeklyWorkHours != null ? !weeklyWorkHours.equals(that.weeklyWorkHours) : that.weeklyWorkHours != null)
            return false;
        if (!Arrays.equals(workExceptions, that.workExceptions)) return false;

        return true;
    }
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (weeklyWorkHours != null ? weeklyWorkHours.hashCode() : 0);
        result = 31 * result + (workExceptions != null ? Arrays.hashCode(workExceptions) : 0);
        return result;
    }
}
