package com.Fitory.fitory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "routines")
public class ExerciseRoutine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int routineid;
    private String routine;
    private int calorie;

    @Column(name="userid")
    private String userid;
    private int time;
}