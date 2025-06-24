package com.Fitory.fitory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Getter
@Setter
@Entity
@Table(name = "replies")
public class Replies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rnum;

    private Integer cnum;

    @Column(name = "id")
    private String uid;

    private String nickname;

    private String rbody;


    private Integer pnum;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate rdate;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int rlikes;

    @PrePersist
    public void prePersist() {
        if (rdate == null) {
            rdate = LocalDate.now();  // 날짜만 포함
        }
    }


}
