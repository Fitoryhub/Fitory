package com.Fitory.fitory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "board")
public class Board {

    @Column(name = "id")
    private String uid;
    private String nickname;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pnum;
    private String ptitle;
   @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pdate;
   private String pbody;
   private String pcategory;
   @Column(columnDefinition = "INT DEFAULT 0")
   private int plook;
   @Column(columnDefinition = "INT DEFAULT 0")
   private int plike;

    @PrePersist
    public void prePersist() {
        if (pdate == null) {
            pdate = LocalDate.now();  // 날짜만 포함
        }
    }
}