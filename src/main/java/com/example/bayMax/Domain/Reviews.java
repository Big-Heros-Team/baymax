package com.example.bayMax.Domain;

import javax.persistence.*;

@Entity
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String body;

    @ManyToOne
    @JoinColumn(name ="user_reviews_id" )
    Users user;

    public Reviews() {
    }

    public Reviews(String body) {
        this.body = body;
    }

    public long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }
}
