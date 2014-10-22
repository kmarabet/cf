package com.cf.controller.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.cf.domain.BaseTimeRegistration;
import com.cf.domain.core.GenericEntity;
import com.cf.utils.DateUtils;
import java.text.ParseException;

public abstract class BaseTimeRegistrationDto extends GenericDto {
    /*@Expose(serialize = false, deserialize = false)
    private Registration registration;*/
    @Expose
    @SerializedName("time_in")
    protected String timeIn;
    @Expose
    @SerializedName("time_out")
    protected String timeOut;
    @Expose
    @SerializedName("user_id")
    protected Long userId;

    @Override
    public void setFieldsFromEntity(GenericEntity entity){
        super.setFieldsFromEntity(entity);
        BaseTimeRegistration registration = (BaseTimeRegistration)entity;
        this.timeIn = DateUtils.formatTime(registration.getTimeIn());
        this.timeOut = DateUtils.formatTime(registration.getTimeOut());
        this.userId = registration.getUserId();
    }

    public GenericEntity getEntityFromDto() throws ParseException {
        return new GenericEntity();
    }

    public String getTimeIn() {
        return timeIn;
    }
    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }
    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
