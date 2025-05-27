package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.ExerciseRoutine;
import com.Fitory.fitory.repository.ExerciseRoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
