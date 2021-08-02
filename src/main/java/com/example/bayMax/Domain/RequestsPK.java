package com.example.bayMax.Domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RequestsPK implements Serializable {
    @Column (name="PATIENT_ID")
    private Long patient_id;

    @Column(name = "DOCTOR_ID")
    private Long doctor_id;

}
