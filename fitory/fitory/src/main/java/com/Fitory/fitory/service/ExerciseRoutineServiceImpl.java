package com.Fitory.fitory.service;

import com.Fitory.fitory.dto.IdEname;
import com.Fitory.fitory.entity.ExerciseRoutine;
import com.Fitory.fitory.repository.ExerciseRoutineRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return exerciseRoutineRepository.findByUserid(id);
    }

    @Override
    @Transactional
    public boolean deleteByroutineid(int routineid) {
        int deletecnt = exerciseRoutineRepository.deleteByroutineid(routineid);
        if (deletecnt > 0) {
            return true;
        }
        return false;
    }

    @Override
    public ExerciseRoutine findByidename(IdEname idename) {
        System.out.println(idename.getEname());
        System.out.println(idename.getId());

        String userid = idename.getId();
        String routine = idename.getEname();
        return exerciseRoutineRepository.findFirstByUseridAndRoutine(userid, routine);
    }

    @Override
    public ExerciseRoutine todaysexercise(String id, String ename) {
        return exerciseRoutineRepository.todayExercise(id, ename);
    }


}
