package com.Fitory.fitory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    private String uid;

    private String name;
    private String password;
    private String email;
    private String gender;
    private String nickname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    private int height;
    private int weight;


}
