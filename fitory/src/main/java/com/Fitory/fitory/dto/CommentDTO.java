package com.Fitory.fitory.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CommentDTO {
    public CommentDTO(String uid,Integer pnum ,Integer cnum,String nickname,String cbody,LocalDate cdate,int clike
            ,boolean liked ) {
        this.uid = uid;
        this.cnum = cnum;
        this.nickname = nickname;
        this.cbody = cbody;
        this.cdate = cdate;
        this.liked = liked;
        this.clike = clike;

    }
    private String uid;
    private Integer pnum;
    private Integer cnum;
    private String nickname;
    private String cbody;
    private LocalDate cdate;
    private int clike;
    private boolean liked=false;


}