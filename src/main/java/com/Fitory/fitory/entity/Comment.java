package com.Fitory.fitory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cnum;

    private String pnum;
    private String cbody;
    @Column(columnDefinition = "INT DEFAULT 0")
    private int clike;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date cdate;
    private String nickname;
    private String id;
}
