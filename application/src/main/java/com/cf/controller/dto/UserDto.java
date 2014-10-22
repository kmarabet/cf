package com.cf.controller.dto;

import com.google.gson.annotations.SerializedName;
import com.cf.domain.User;
import com.cf.domain.core.GenericEntity;

public class UserDto extends GenericDto {

    @SerializedName("user")
    private String username;
    //private String pin;
    @SerializedName("job_type")
    private Long usertypeId;
    @SerializedName("employee_type")
    private Long employeeTypeId;
    //private Gender gender;
    @SerializedName("color")
    private Integer color;
    @SerializedName("ou_time")
    private Integer overUnderTime;
    private Integer normTargetTime;
    private Float reportedTime;

    public Float getReportedTime() {
        return reportedTime;
    }

    public void setReportedTime(Float reportedTime) {
        this.reportedTime = reportedTime;
    }

    public Integer getNormTargetTime() {
        return normTargetTime;
    }

    public void setNormTargetTime(Integer normTargetTime) {
        this.normTargetTime = normTargetTime;
    }

    //private String employeeType;
    private String institutionName;

    public UserDto() {
    }
    public UserDto(User entity) {
        setFieldsFromEntity(entity);
    }

    @Override
    public void setFieldsFromEntity(GenericEntity entity){
        super.setFieldsFromEntity(entity);
        User user = (User)entity;
        this.username = user.getFirstName() + " " + user.getLastName();
        //this.pin = user.getPin();
        this.usertypeId = user.getUsertypeId();
        //TODO stub
        this.employeeTypeId = user.getEmployeeTypeId();
        //this.gender = user.getGender();
        this.color = user.getColor();
        this.overUnderTime = user.getOverUnderTime();
        this.normTargetTime = user.getNormTargetTime();
        //this.employeeType = user.getEmployeeTypeTable().getEmployeeTypeName().name();
        //this.institutionName = user.getInstitution().getName();
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

   /* public String getPin() {
        return pin;
    }
    public void setPin(String pin) {
        this.pin = pin;
    }*/

    public Long getUsertypeId() {
        return usertypeId;
    }
    public void setUsertypeId(Long usertypeId) {
        this.usertypeId = usertypeId;
    }

    public Integer getColor() {
        return color;
    }
    public void setColor(Integer color) {
        this.color = color;
    }

    public Integer getOverUnderTime() {
        return overUnderTime;
    }
    public void setOverUnderTime(Integer overUnderTime) {
        this.overUnderTime = overUnderTime;
    }

    public String getInstitutionName() {
        return institutionName;
    }
    public void setInstitutionName(String institutionName) {
        institutionName = institutionName;
    }

    public GenericEntity getEntityFromDto(){
        return new User(this);
    }

    public Long getEmployeeTypeId() {
        return employeeTypeId;
    }

    public void setEmployeeTypeId(Long employeeTypeId) {
        this.employeeTypeId = employeeTypeId;
    }
}
