package com.example.JPAproject.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "USER")
public class USER {
    @Id
    @NotBlank
    private String username;

    @NotBlank
    @Column(columnDefinition = "name")
    private String name;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
