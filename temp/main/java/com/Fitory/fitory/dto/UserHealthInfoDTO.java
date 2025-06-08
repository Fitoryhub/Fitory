package com.Fitory.fitory.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserHealthInfoDTO {
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
