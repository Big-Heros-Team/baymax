package com.example.bayMax.Domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Date;
import java.time.YearMonth;
import java.util.*;

@Entity
@Table(name="users")
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private Date dateOfBirth;
    private String location;
    private String bloodType;
    private Long nationalId;

    private int old;

    @Column(unique = true)
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    private Set<Roles> roles=new HashSet<>();


    @OneToMany(mappedBy = "users",cascade = CascadeType.ALL )
    private List<Record> records= new ArrayList<>();

    public List<Record> getRecords() {
        return records;
    }

    public Users(){
    }

    @OneToMany(mappedBy = "user")
    List<Reviews> reviews;


    @OneToMany(mappedBy = "patient")
    Set<Requests> patientRequests =new HashSet<>();

    @OneToMany(mappedBy = "doctor")
    Set<Requests> doctorRequests =new HashSet<>();



    public Users(String firstname, String lastname, Date dateOfBirth, String location, String bloodType, Long nationalId, String username, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.location = location;
        this.bloodType = bloodType;
        this.nationalId = nationalId;
        this.username = username;
        this.password = password;
        this.old= calculateAge(dateOfBirth);
    }
    public int calculateAge(Date dateOfBirth){
        int year = YearMonth.now().getYear();

        int age= year - dateOfBirth.getYear();

        return age;
    }

    public Long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFullName(){
        return firstname+" "+lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public Long getNationalId() {
        return nationalId;
    }

    public void setNationalId(Long nationalId) {
        this.nationalId = nationalId;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Roles> roles = this.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Roles role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return authorities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void addRole(Roles role ) {
        this.roles.add(role);
    }

    public List<Reviews> getReviews() {
        return reviews;
    }

    public void addReview(Reviews review) {
        this.reviews.add(review);
    }

    public Set<Requests> getPatientRequests() {
        return patientRequests;
    }

    public Set<Requests> getDoctorRequests() {
        return doctorRequests;
    }
}
