package com.cf.domain;

import com.cf.controller.dto.UserDto;
import com.cf.domain.core.GenericEntity;

import javax.persistence.*;


@Entity
@Table(name = "USERS", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}) } )
public class User extends GenericEntity {

    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="user_name", unique = true, nullable = false)
    private String username;
    private String pin;
    private String email;
    @Column(name="usertype_id",nullable = false)
    private Long usertypeId;
    @Column(name="usertype_name")
    private String usertypeName;
    private Gender gender;
    @Column(name="manager_id")
    private Long managerId;

    private Integer color;
    private Integer overUnderTime;

    private Integer normTargetTime;

    @Column(name="employeeType_id")
    private Long employeeTypeId;

    @Column(name="institution_id")
    private Long institutionId;
    //private Institution institution;
    //private List<Institution> institutions = new ArrayList<>();

    public User() {
    }
    public User(UserDto dto) {
        super.setId(dto.getId());
        this.username = dto.getUsername();
        //this.pin = dto.getPin();
        this.usertypeId = dto.getUsertypeId();
        //this.gender = dto.getGender();
        this.color = dto.getColor();
        this.overUnderTime = dto.getOverUnderTime();
        this.employeeTypeId = dto.getEmployeeTypeId();
        this.normTargetTime = dto.getNormTargetTime();
        //this.employeeTypeName = dto.getEmployeeTypeName();
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPin() {
        return pin;
    }
    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUsertypeId() {
        return usertypeId;
    }
    public void setUsertypeId(Long usertypeId) {
        this.usertypeId = usertypeId;
    }

    public String getUsertypeName() {
        return usertypeName;
    }
    public void setUsertypeName(String usertypeName) {
        this.usertypeName = usertypeName;
    }

    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Long getManagerId() {
        return managerId;
    }
    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public Integer getColor() {
        return color;
    }
    public void setColor(Integer color) {
        this.color = color;
    }

    public Integer getOverUnderTime() {
        return overUnderTime;
    }
    public void setOverUnderTime(Integer overUnderTime) {
        this.overUnderTime = overUnderTime;
    }

    public Long getEmployeeTypeId() {
        return employeeTypeId;
    }

    public void setEmployeeTypeId(Long employeeTypeId) {
        this.employeeTypeId = employeeTypeId;
    }

    public Integer getNormTargetTime() {
        return normTargetTime;
    }

    public void setNormTargetTime(Integer normTargetTime) {
        this.normTargetTime = normTargetTime;
    }

    /*@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USERS_INSTITUTIONS",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "institution_id"))
    public List<Institution> getInstitutions() {
        return institutions;
    }
    public void setInstitutions(List<Institution> institutions) {
        this.institutions = institutions;
    }
    public void addInstitution(Institution institution){
        institutions.add(institution);
    }*/

    /* @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Registration> getRegistrations() {
        return registrations;
    }
    public void setRegistrations(List<Registration> registrations) {
        this.registrations = registrations;
    }
    public void addRegistration(Registration reg){
        reg.setUser(this);
        this.registrations.add(reg);
    }*/

    /*@ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_id")
    public Institution getInstitution() {
        return institution;
    }
    public void setInstitution(Institution institution) {
        this.institution = institution;
    }*/

    public Long getInstitutionId() {
        return institutionId;
    }
    public void setInstitutionId(Long institutionId) {
        this.institutionId = institutionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (gender != user.gender) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (!pin.equals(user.pin)) return false;
        if (!username.equals(user.username)) return false;
        if (!usertypeId.equals(user.usertypeId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + username.hashCode();
        result = 31 * result + pin.hashCode();
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + usertypeId.hashCode();
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        return result;
    }

    @Transient
    public boolean isManager(){
        if (managerId == null){
            return true;
        }
        return true;
    }

}
