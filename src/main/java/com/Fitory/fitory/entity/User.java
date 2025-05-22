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
    private String userName;

    @Column(name ="password")
    private String userPassword;

    private String email;
    private String gender;
    @Column(name ="birth_date")
    private LocalDate birth;

    @Column(name="height_cm")
    private int heightCm;
    @Column(name="weight_kg")
    private int weightKg;
}
