package com.example.bayMax.Domain;

//package com.example.apiPharmacy;

import com.example.bayMax.Domain.Users;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "drugs")
public class Drug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long drugId;

    @Column(columnDefinition="TEXT")
    private String drugName;

    @ManyToMany(mappedBy = "drugs")
    private Set<Users> users = new HashSet<>();

    /**
     * Constructors
     */
    public Drug() {
    }

    public Drug(String drugName) {
        this.drugName = drugName;
    }

    /**
     * Getters & Setters
     */
    public Long getDrugId() {
        return drugId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public Set<Users> getUsers() {
        return users;
    }

}
