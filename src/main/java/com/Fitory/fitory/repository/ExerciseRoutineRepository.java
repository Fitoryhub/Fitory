package com.Fitory.fitory.repository;

import com.Fitory.fitory.entity.ExerciseRoutine;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRoutineRepository extends JpaRepository<ExerciseRoutine, Integer> {
    @Override
    <S extends ExerciseRoutine> S save(S entity);
}
