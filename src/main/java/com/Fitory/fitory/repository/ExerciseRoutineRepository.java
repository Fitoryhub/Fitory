package com.Fitory.fitory.repository;

import com.Fitory.fitory.entity.ExerciseRoutine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExerciseRoutineRepository extends JpaRepository<ExerciseRoutine, Integer> {
    @Override
    <S extends ExerciseRoutine> S save(S entity);

    List<ExerciseRoutine> findByUserid(String userId);

    int deleteByroutineid(int routineid);

    ExerciseRoutine findFirstByUseridAndRoutine(String userid, String routine);
}
