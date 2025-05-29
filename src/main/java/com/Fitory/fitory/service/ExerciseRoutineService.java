package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.ExerciseRoutine;

import java.util.List;
import java.util.Optional;

public interface ExerciseRoutineService {
   public boolean save(ExerciseRoutine routine);

   public List<ExerciseRoutine> findByUserID(String id);

   public boolean deleteByroutineid(int routineid);
}
