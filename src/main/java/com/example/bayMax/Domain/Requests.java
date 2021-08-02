package com.example.bayMax.Domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Requests")
@IdClass(RequestsPK.class)
public class Requests implements Serializable {

    @Id
@ManyToOne
@JoinColumn(name = "PATIENT_ID")
   private Users patient;

@Id
@ManyToOne
@JoinColumn(name = "DOCTOR_ID")
   private Users doctor;

    @Column(name = "isAccepted")
    private Boolean isAccepted;

    public Requests() {
    }

    public void setPatient(Users patient) {
        this.patient = patient;
    }

    public void setDoctor(Users doctor) {
        this.doctor = doctor;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public Users getPatient() {
        return patient;
    }

    public Users getDoctor() {
        return doctor;
    }

    public boolean isAccepted() {
        return isAccepted;
    }


}
