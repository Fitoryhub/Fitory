package com.Fitory.fitory.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RepliesDTO {
    public RepliesDTO(Integer pnum,Integer rnum, Integer cnum, String uid, String nickname,
                      String rbody, LocalDate rdate, int rlikes, boolean check) {
        this.pnum = pnum;
        this.rnum = rnum;
        this.cnum = cnum;
        this.uid = uid;
        this.nickname = nickname;
        this.rbody = rbody;
        this.rdate = rdate;
        this.rlikes = rlikes;
        this.check = check;
    }

    private Integer pnum;

    private Integer rnum;

    private Integer cnum;

    private String uid;

    private String nickname;

    private String rbody;


    private LocalDate rdate;

    private int rlikes;

    private boolean check = false;

}
