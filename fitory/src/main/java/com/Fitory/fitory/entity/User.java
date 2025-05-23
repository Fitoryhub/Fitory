package com.Fitory.fitory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="User_data")
public class User {
    @Id
    @Column(name="id")
    private String id;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    @Column(name="name")
    private String name;

    public void update_user(String id,String password, String email, String name){
        this.id = id;
        this.password = password;
        this.email = email;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
