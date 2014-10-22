package com.cf.domain;


import com.cf.domain.core.GenericEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CORRECTION_CAUSE")
public class CorrectionCause extends GenericEntity{

    private String name;
    private Boolean active;

    public CorrectionCause(){}

    public CorrectionCause(Long id, String name) {
        setId(id);
        this.active = true;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
