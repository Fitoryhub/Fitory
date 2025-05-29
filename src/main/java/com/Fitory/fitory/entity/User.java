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
    private String id;

    private String nickname;
    private String name;
    private String password;
    private String email;
    private String gender;
    private LocalDate birth;
    private int height;
    private int weight;
}
