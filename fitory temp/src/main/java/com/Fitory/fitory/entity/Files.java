package com.Fitory.fitory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "file")
public class Files {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fnum;

    private Integer pnum;

    private String filename;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fdate;

    @PrePersist
    public void prePersist() {
        if (fdate == null) {
            fdate = LocalDate.now();  // 날짜만 포함
        }
    }
}


