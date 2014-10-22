package com.cf.domain;

import com.cf.controller.dto.WorkExceptionDto;
import com.cf.domain.core.GenericEntity;
import com.cf.utils.DateUtils;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.text.ParseException;
import java.util.Date;


@Entity
@Table(name = "WORK_EXCEPTION")
public class WorkException extends GenericEntity {

    String name;
    Date startTime;
    Date endTime;
    //Long employeeTypeId;
    EmployeeType employeeType;

    public WorkException(){}

    public WorkException(WorkExceptionDto dto) throws ParseException {
        super.setId(dto.getId());
        this.name = dto.getName();
        this.startTime = DateUtils.parseDateTime(dto.getStartTime());
        this.endTime = DateUtils.parseDateTime(dto.getEndTime());
    }

    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "employeeType_id")
    public EmployeeType getEmployeeType() {
        return employeeType;
    }
    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    @Transient
    public boolean isTimeInOutValid(){
        if (endTime.after(startTime)){
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkException)) return false;

        WorkException that = (WorkException) o;

        //if (employeeType != null ? !employeeType.equals(that.employeeType) : that.employeeType != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;

        return true;
    }
    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        //result = 31 * result + (employeeType != null ? employeeType.hashCode() : 0);
        return result;
    }
}
