package com.Fitory.fitory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rlikes")
public class Rlikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer num;
    @Column(name = "id")
    private String uid;

    private Integer cnum;

    private Integer pnum;

    private Integer rnum;

}