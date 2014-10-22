package com.cf.domain;

import java.util.Date;


/*@Entity
@Table(name = "LOG_HISTORY")*/
public class LogHistory /*extends GenericEntity */{

    Date arrived;
    Date departure;
    boolean allDay;
    boolean morningEntry;
    //Child child;
    Department department;

}
