package com.Fitory.fitory.entity;

import com.Fitory.fitory.dto.UserHealthInfoDTO;
import jakarta.persistence.Column;
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
    private String bmr;
    private String rdi;
    private String targetWeight;
    private String bloodPressure;
    private String diabetes;
    private String smoking;
    private String bodyFat;
    private String muscle;

    @Column(name="activityLevel")
    private String activityLevel;

    private String medicalHistory;
    private String allergy;

    public void update_profile (UserHealthInfoDTO udto){
        this.uid = udto.getUid();
        this.bmr = udto.getBmr();
        this.rdi = udto.getRdi();
        this.targetWeight = udto.getTargetWeight();
        this.bloodPressure = udto.getBloodPressure();
        this.diabetes = udto.getDiabetes();
        this.smoking = udto.getSmoking();
        this.bodyFat = udto.getBodyFat();
        this.muscle = udto.getMuscle();
        this.activityLevel = udto.getActivityLevel();
        this.medicalHistory = udto.getMedicalHistory();
        this.allergy = udto.getAllergy();
    }
}
