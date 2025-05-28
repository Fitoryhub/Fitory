package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.ExerciseRoutine;
import com.Fitory.fitory.repository.ExerciseRoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseRoutineServiceImpl implements ExerciseRoutineService {

    @Autowired
    ExerciseRoutineRepository exerciseRoutineRepository;

    @Override
    public boolean save(ExerciseRoutine routine) {
        try{
            exerciseRoutineRepository.save(routine);
            return true;
        } catch (Exception e) {

        return false;
        }
    }

    @Override
    public List<ExerciseRoutine> findByUserID(String id) {

        return exerciseRoutineRepository.findByUserId(id);
    }
}
