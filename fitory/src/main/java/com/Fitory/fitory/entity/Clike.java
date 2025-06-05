package com.Fitory.fitory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "clike")
public class Clike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer num;
    private Integer pnum;
    @Column(name = "id")
    private String uid;
    private Integer cnum;
}
