package com.cf.domain;


import javax.persistence.Column;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Date;


/*@Entity
@Table(name = "EVENTS")*/
public class Event /*extends GenericEntity*/ {

    String title;
    String content;
    String type;
    //@Column(name="date", columnDefinition="DATE NOT NULL")
    Date date;
    @Column(name="time_from")
    Time timeFrom;
    @Column(name="time_to")
    Timestamp timeTo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(Time timeFrom) {
        this.timeFrom = timeFrom;
    }

    public Timestamp getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(Timestamp timeTo) {
        this.timeTo = timeTo;
    }
}
