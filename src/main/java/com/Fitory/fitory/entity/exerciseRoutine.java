package com.Fitory.fitory.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class exerciseRoutine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer routine_id;
    private String user_id;
    private String routine_name;
    private String total_calorie;
}
