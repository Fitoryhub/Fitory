package com.Fitory.fitory.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseDTO {

    private Integer exercise_id;
    private String name;
    private String intensity;
    private int met_rank;
    private String is_anaerobic;
    private String requires_equipment;


}
