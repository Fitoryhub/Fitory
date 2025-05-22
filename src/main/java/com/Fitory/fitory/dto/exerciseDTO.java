package com.Fitory.fitory.dto;

import com.Fitory.fitory.entity.Exercise;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class exerciseDTO {

    private Integer exercise_id;
    private String name;
    private String intensity;
    private int met_rank;
    private String is_anaerobic;
    private String requires_equipment;

    public exerciseDTO(Exercise e) {
    }
}
