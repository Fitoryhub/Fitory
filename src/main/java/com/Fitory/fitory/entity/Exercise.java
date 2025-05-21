package com.Fitory.fitory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name="exercises")
@Getter
@Setter
public class Exercise {

    @Id
    private Integer exercise_id;

    @Column(name = "name")
    private String e_name;

    private String intensity;

    @Column(name = "met_rank")
    private int metrank;
    private String is_anaerobic;
    private String requires_equipment;
}
