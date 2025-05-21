package com.Fitory.fitory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity(name ="users")
public class User {
    @Id
    private String user_id;

    @Column(name="username")
    private String user_name;

    @Column(name ="password")
    private String user_password;

    private String email;
    private String gender;
    @Column(name ="birth_date")
    private LocalDate birth;
    private int height_cm;
    private int weight_kg;
}
