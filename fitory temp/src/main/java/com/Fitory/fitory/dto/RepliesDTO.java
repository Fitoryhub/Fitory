package com.Fitory.fitory.dto;

import lombok.Getter;
import lombok.Setter;

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
