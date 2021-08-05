package com.example.bayMax.Domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


public class RequestsPK implements Serializable {

    private Long patient;
    private Long doctor;


}
