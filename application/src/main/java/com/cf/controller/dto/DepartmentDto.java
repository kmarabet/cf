package com.cf.controller.dto;

import com.cf.domain.Department;
import com.cf.domain.Institution;
import com.cf.domain.core.GenericEntity;

public class DepartmentDto extends GenericDto {
    private String name;
    private Institution institution;

    public DepartmentDto() {
    }
    public DepartmentDto(Department entity) {
        setFieldsFromEntity(entity);
    }

    @Override
    public void setFieldsFromEntity(GenericEntity entity){
        super.setFieldsFromEntity(entity);
        Department dep = (Department)entity;
        this.institution = dep.getInstitution();
        this.name = dep.getName();
    }

    public Institution getInstitution() {
        return institution;
    }
    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
