package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.Exercises;

import java.util.List;

public interface ExerciseService {
    List<Exercises> getMatchingExercises(int met, String requiresEquipment, String isAnaerobic);
    List<Exercises> getMatchingExercises_v2(int met, String isAnaerobic);
    List<Exercises> getMatchingExercises_v3(int met, String requiresEquipment);
    List<Exercises> getMatchingExercises_v4(int met);
    List<String> findByOxygen(String s);
    List<String> findbyBodyweight(String s);
}
