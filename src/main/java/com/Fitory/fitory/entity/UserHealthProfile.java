package com.Fitory.fitory.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "health_Profile")
public class UserHealthProfile {
    @Id
    private String uid;
    private String targetWeight;
    private String bloodPressure;
    private String diabetes;
    private String smoking;
    private String bodyFat;
    private String muscle;
    private String activityLevel;
    private String medicalHistory;
    private String allergy;

}
