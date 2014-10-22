package com.cf.domain;

import com.cf.controller.dto.BaseTimeRegistrationDto;
import com.cf.controller.dto.GenericDto;
import com.cf.domain.core.GenericEntity;
import com.cf.utils.DateUtils;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.sql.Time;
import java.text.ParseException;


//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
public abstract class BaseTimeRegistration extends GenericEntity {

    @Column(name="time_in", nullable = false)
    protected Time timeIn;
    @Column(name="time_out", nullable = false)
    protected Time timeOut;
    @Column(name = "user_id", nullable = false)
    protected Long userId;

    protected BaseTimeRegistration() {
    }
    protected BaseTimeRegistration(BaseTimeRegistration reg) {
        //super.setId(reg.getId());
        this.timeIn = reg.timeIn;
        this.timeOut = reg.timeOut;
        this.userId = reg.userId;
    }

    public Time getTimeIn() {
        return timeIn;
    }
    public void setTimeIn(Time timeIn) {
        this.timeIn = timeIn;
    }

    public Time getTimeOut() {
        return timeOut;
    }
    public void setTimeOut(Time timeOut) {
        this.timeOut = timeOut;
    }

    /*@ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }*/

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    protected void setFieldsFromDto(GenericDto dto) throws ParseException {
        super.setId(dto.getId());
        BaseTimeRegistrationDto registrationDto = (BaseTimeRegistrationDto)dto;
        this.timeIn = DateUtils.parseTime(registrationDto.getTimeIn());
        this.timeOut = DateUtils.parseTime(registrationDto.getTimeOut());
        this.userId = registrationDto.getUserId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Registration)) return false;

        BaseTimeRegistration that = (BaseTimeRegistration) o;

        if (!timeIn.equals(that.timeIn)) return false;
        if (!timeOut.equals(that.timeOut)) return false;
        if (!userId.equals(that.userId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + timeIn.hashCode();
        result = 31 * result + timeOut.hashCode();
        return result;
    }

    @Transient
    public boolean isTimeInOutValid(){
        if (timeIn.after(timeOut)){
            return false;
        }
        return true;
    }

}
