package com.example.bayMax.Domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

@Entity
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private String drugs;
    private String medicalDiagosis;
    private Date visitDate;

    @ManyToOne
    @JoinColumn(name="user_record")
    private Users users;


    public Record() {
    }


    public Record(String drugs, String medicalDiagosis,  Users users) {

        this.drugs = drugs;
        this.medicalDiagosis = medicalDiagosis;
        this.visitDate = new Date(System.currentTimeMillis());
        this.users = users;
    }

    public String getMedicalDiagosis() {
        return medicalDiagosis;
    }

    public Long getId() {
        return id;
    }

    public String getDrugs() {
        return drugs;
    }

    public void setDrugs(String newDrug) {
        this.drugs=newDrug;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Date getVisitDate() {
        return visitDate;
    }


}
