package com.Fitory.fitory.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExerciseRoutineDTO {
    private int routine_id;
    private String user_id;
    private List<String> routine_name;
    private int total_calorie;
    private int total_time;
}