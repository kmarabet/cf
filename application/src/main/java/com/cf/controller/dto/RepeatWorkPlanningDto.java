package com.cf.controller.dto;

public class RepeatWorkPlanningDto {

    private String startsDate;
    private String value;
    private EndDateType endType;
    private Integer occurrences;
    private String endDate;

    public String getStartsDate() {
        return startsDate;
    }
    public void setStartsDate(String startsDate) {
        this.startsDate = startsDate;
    }

    public EndDateType getEndType() {
        return endType;
    }
    public void setEndType(EndDateType endType) {
        this.endType = endType;
    }

    public Integer getOccurrences() {
        return occurrences;
    }
    public void setOccurrences(Integer occurrences) {
        this.occurrences = occurrences;
    }

    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
