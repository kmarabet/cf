package com.cf.domain;

import java.sql.Date;
import java.text.ParseException;
import com.cf.controller.dto.GenericDto;
import com.cf.controller.dto.RegistrationDto;
import com.cf.utils.DateUtils;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "REGISTRATIONS")
public class Registration extends BaseTimeRegistration {

    @Column(nullable = false)
    protected Date date;

    public Registration(){
    }
    public Registration(RegistrationDto dto) throws ParseException{
        super.setFieldsFromDto(dto);
    }

    protected void setFieldsFromDto(GenericDto dto) throws ParseException {
        super.setId(dto.getId());
        RegistrationDto registrationDto = (RegistrationDto)dto;
        this.date = DateUtils.parseDate(registrationDto.getDate());
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

}
