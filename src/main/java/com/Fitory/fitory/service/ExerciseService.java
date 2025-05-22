package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.Exercises;

import java.util.List;

public interface ExerciseService {
    List<Exercises> getMatchingExercises(int met, String requiresEquipment, String isAnaerobic);
}
