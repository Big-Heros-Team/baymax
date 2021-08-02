package com.example.bayMax.Domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Requests implements Serializable {

    @EmbeddedId
    private RequestsPK id;

@ManyToOne
@MapsId("patient_id")
    @JoinColumn(name = "PATIENT_ID")
   private Users patient;


@ManyToOne
@MapsId("doctor_id")
    @JoinColumn(name = "DOCTOR_ID")
   private Users doctor;

boolean isAccepted;

    public Requests() {
    }

    public Requests(Users patient, Users doctor, boolean isAccepted) {
        this.patient = patient;
        this.doctor = doctor;
        this.isAccepted = isAccepted;
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

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }
}
