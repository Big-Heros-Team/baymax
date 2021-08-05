package com.example.bayMax.Domain;

import javax.persistence.*;

@Entity
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String body;


    @ManyToOne
    @JoinColumn(name = "blog_user")
    Users user;

    public Blog(String body) {
        this.body = body;
    }

    public Blog() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
