package com.Fitory.fitory.dto;


import com.Fitory.fitory.entity.UserHealthProfile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserHealthInfoDTO {
    private String uid;
    private String bmr;
    private String rdi;
    private String targetWeight;
    private String bloodPressure;
    private String diabetes;
    private String smoking;
    private String bodyFat;
    private String muscle;
    private String activityLevel;
    private String medicalHistory;
    private String allergy;

    public void update_user(UserHealthProfile user) {
        this.uid = user.getUid();
        this.bmr = user.getBmr();
        this.rdi = user.getRdi();
        this.targetWeight = user.getTargetWeight();
        this.bloodPressure = user.getBloodPressure();
        this.diabetes = user.getDiabetes();
        this.smoking = user.getSmoking();
        this.bodyFat = user.getBodyFat();
        this.muscle = user.getMuscle();
        this.activityLevel = user.getActivityLevel();
        this.medicalHistory = user.getMedicalHistory();
        this.allergy = user.getAllergy();
    }
}
