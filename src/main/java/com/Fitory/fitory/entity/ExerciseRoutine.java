package com.Fitory.fitory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "exercise_routine")
public class ExerciseRoutine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "routine_id")
    private int id;


    @Column(name = "routine_name")
    private String routineName;

    @Column(name = "total_calorie")
    private int totalCalorie;


    @Column(name = "user_id")
    private String userId;

    @Column(name = "total_time")
    private int totalTime;

}