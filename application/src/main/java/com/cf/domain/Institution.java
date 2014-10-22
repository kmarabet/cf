package com.cf.domain;

import com.cf.domain.core.GenericEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "INSTITUTIONS")
public class Institution extends GenericEntity {
    private String name;
    private String url;
    private String types;
    @Lob
    private String preference;

    private List<Department> departments = new ArrayList<>();
    //private List<User> users = new ArrayList<>();

    public Institution() {
    }

    public Institution(Long id) {
        super.setId(id);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getTypes() {
        return types;
    }
    public void setTypes(String types) {
        this.types = types;
    }

    public String getPreference() {
        return preference;
    }
    public void setPreference(String preference) {
        this.preference = preference;
    }

    @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL)
    public List<Department> getDepartments() {
        return departments;
    }
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public void addDepartment(Department dep){
        dep.setInstitution(this);
        departments.add(dep);
    }

    /*@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USERS_INSTITUTIONS",
            joinColumns = @JoinColumn(name = "institution_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))*/
   /* @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL)
    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }
    public void addUser(User user){
        users.add(user);
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Institution)) return false;

        Institution that = (Institution) o;

        if (!name.equals(that.name)) return false;
        if (!types.equals(that.types)) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + types.hashCode();
        return result;
    }
}
