package com.cf.domain;

import com.cf.controller.dto.WorkPlanningDto;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.text.ParseException;


@Entity
@Table(name = "WORK_PLANNINGS"/*, uniqueConstraints = {@UniqueConstraint(columnNames = {"userId", "day", "week"}) }*/ )
public class WorkPlanning extends BaseTimeRegistration {

    private Integer day;
    private Integer week;

    public WorkPlanning(){
    }
    public WorkPlanning(WorkPlanningDto dto) throws ParseException {
        super.setFieldsFromDto(dto);
        this.day = dto.getDay();
        this.week = dto.getWeek();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkPlanning)) return false;
        if (!super.equals(o)) return false;

        WorkPlanning that = (WorkPlanning) o;

        if (day != null ? !day.equals(that.day) : that.day != null) return false;
        if (week != null ? !week.equals(that.week) : that.week != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (day != null ? day.hashCode() : 0);
        result = 31 * result + (week != null ? week.hashCode() : 0);
        return result;
    }
}
