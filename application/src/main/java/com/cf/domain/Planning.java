package com.cf.domain;

import com.cf.controller.dto.GenericDto;
import com.cf.controller.dto.PlanningDto;
import com.cf.utils.DateUtils;

import javax.persistence.*;
import java.sql.Date;
import java.text.ParseException;


@Entity
@Table(name = "PLANNINGS")
public class Planning extends BaseTimeRegistration {

    @Column(nullable = false)
    protected Date date;
    Boolean corrected;
    CorrectionCause correctionCause;

    public Planning(){
    }
    public Planning(PlanningDto dto) throws ParseException {
        this.setFieldsFromDto(dto);
    }
    public Planning(WorkPlanning workPlan){
        super(workPlan);
    }

    protected void setFieldsFromDto(GenericDto dto) throws ParseException {
        super.setFieldsFromDto(dto);
        PlanningDto planDto = (PlanningDto)dto;
        this.date = DateUtils.parseDate(planDto.getDate());
        this.corrected = planDto.isCorrected()==null?Boolean.FALSE:planDto.isCorrected();
        this.correctionCause = planDto.getCorrectionCause();
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean isCorrected() {
        return corrected;
    }
    public void setCorrected(Boolean corrected) {
        this.corrected = corrected;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "correction_cause_id")
    public CorrectionCause getCorrectionCause() {
        return correctionCause;
    }
    public void setCorrectionCause(CorrectionCause correctionCause) {
        this.correctionCause = correctionCause;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Planning)) return false;
        if (!super.equals(o)) return false;

        Planning planning = (Planning) o;

        if (corrected != null ? !corrected.equals(planning.corrected) : planning.corrected != null) return false;
        if (correctionCause != planning.correctionCause) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (corrected != null ? corrected.hashCode() : 0);
        result = 31 * result + (correctionCause != null ? correctionCause.hashCode() : 0);
        return result;
    }
}