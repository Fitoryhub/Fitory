package com.Fitory.fitory.entity;

import jakarta.persistence.*;

@Entity(name="routines")
public class ExerciseRoutine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="routine_id")
    private Integer routineId;
    @Column(name="user_id")
    private String userId;
    @Column(name="routine_name")
    private String routineName;
    @Column(name="total_calorie")
    private int totalCalorie;
}
