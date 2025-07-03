package com.Fitory.fitory.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardDTO {

    public BoardDTO(String uid, String nickname, Integer pnum, String ptitle,
                    LocalDateTime pdate, String pbody, String pcategory, int plook, int plike,boolean liked) {

        this.uid = uid;
        this.nickname = nickname;
        this.pnum = pnum;
        this.ptitle = ptitle;
        this.pbody = pbody;
        this.pdate = pdate;
        this.pcategory = pcategory;
        this.plook = plook;
        this.plike = plike;
        this.liked = liked;
    }

    private String uid;
    private String nickname;
    private Integer pnum;
    private String ptitle;
    private LocalDateTime pdate;
    private String pbody;
    private String pcategory;
    private int plook;
    private int plike;
    private  boolean liked;

}
