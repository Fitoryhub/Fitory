package com.Fitory.fitory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Exercises {

    @Id
    private Integer exercise_id;

    @Column(name = "name")
    private String e_name;

    private String intensity;

    @Column(name = "met_rank")
    private int metrank;
    @Column(name ="is_anaerobic")
    private String isAnaerobic;
    @Column(name ="requires_equipment")
    private String requiresEquipment;
}
