package com.cf.domain;

import com.cf.controller.dto.EmployeeTypeDto;
import com.cf.domain.core.GenericEntity;
import com.cf.utils.ValidationDtoUtils;
import org.apache.commons.collections4.ListUtils;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Entity
@Table(name = "EMPLOYEE_TYPE")
public class EmployeeType extends GenericEntity {

    String name;
    Integer weeklyWorkHours;
    //Integer normTime;//??
    List<WorkException> workExceptions = new ArrayList<>();

    public EmployeeType() {}

    public EmployeeType(EmployeeTypeDto dto) {
        super.setId(dto.getId());
        this.name = dto.getName();
        this.weeklyWorkHours = dto.getWeeklyWorkHours();
        if (dto.getWorkExceptions() != null)
            this.workExceptions = ValidationDtoUtils.getEntityListFromDtoList(Arrays.asList(dto.getWorkExceptions()), null);
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

    @OneToMany(mappedBy = "employeeType", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<WorkException> getWorkExceptions() {
        return workExceptions;
    }
    public void setWorkExceptions(List<WorkException> workExceptions) {
        this.workExceptions = workExceptions;
    }
    public void addWorkException(WorkException we){
        we.setEmployeeType(this);
        workExceptions.add(we);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeType)) return false;

        EmployeeType that = (EmployeeType) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (weeklyWorkHours != null ? !weeklyWorkHours.equals(that.weeklyWorkHours) : that.weeklyWorkHours != null)
            return false;
        if (workExceptions != null ? !ListUtils.isEqualList(workExceptions, that.workExceptions) : that.workExceptions != null)
            return false;

        return true;
    }
    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (weeklyWorkHours != null ? weeklyWorkHours.hashCode() : 0);
        result = 31 * result + (workExceptions != null ? workExceptions.hashCode() : 0);
        return result;
    }
}
