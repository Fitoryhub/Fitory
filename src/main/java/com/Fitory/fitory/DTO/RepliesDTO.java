package com.Fitory.fitory.DTO;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class RepliesDTO {


    private Integer rnum;

    private Integer cnum;
//
    private String uid;
//
    private String nickname;
//
    private String rbody;
//
    private Integer Pnum;
//
    private LocalDate rdate;
//
    private int rlikes;
//
    private boolean check =false;


}
