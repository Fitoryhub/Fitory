package com.Fitory.fitory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name="exercises")
@Getter
@Setter
public class Exercises {

    @Id
    private Integer exerciseid;
    private String ename;
    private String intensity;
    private int metrank;
    private String oxygen;
    private String bodyweight;
}
