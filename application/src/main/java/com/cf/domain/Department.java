package com.cf.domain;

import com.cf.controller.dto.DepartmentDto;
import com.cf.domain.core.GenericEntity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "DEPARTMENTS")
public class Department extends GenericEntity {
    private String name;
    private Institution institution;
    //private List<Child> children = new ArrayList<>();

    public Department() {
    }
    public Department(DepartmentDto dto) {
        this.institution = dto.getInstitution();
        this.name = dto.getName();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_id")
    public Institution getInstitution() {
        return institution;
    }
    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    /*@OneToMany(mappedBy = "depart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Child> getChildren() {
        return children;
    }
    public void setChildren(List<Child> children) {
        this.children = children;
    }
    public void addChild(Child child){
        child.setDepart(this);
        children.add(child);
    }*/
}
