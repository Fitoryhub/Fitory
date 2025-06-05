package com.Fitory.fitory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cnum;
    private Integer pnum;
    private String cbody;
    @Column(columnDefinition = "INT DEFAULT 0")
    private int clike;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate cdate;
    private String nickname;
    @Column(name = "id")
    private String uid;

    @PrePersist
    public void prePersist() {
        if (cdate == null) {
            cdate = LocalDate.now();  // 날짜만 포함
        }
    }
}
