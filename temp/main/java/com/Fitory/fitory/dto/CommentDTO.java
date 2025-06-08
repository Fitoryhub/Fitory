package com.Fitory.fitory.dto;

import java.time.LocalDate;


public class CommentDTO {
    public Integer getCnum() {
        return cnum;
    }

    public void setCnum(Integer cnum) {
        this.cnum = cnum;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getClike() {
        return clike;
    }

    public void setClike(int clike) {
        this.clike = clike;
    }

    public LocalDate getCdate() {
        return cdate;
    }

    public void setCdate(LocalDate cdate) {
        this.cdate = cdate;
    }

    public String getCbody() {
        return cbody;
    }

    public void setCbody(String cbody) {
        this.cbody = cbody;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public Integer getPnum() {
        return pnum;
    }

    public void setPnum(Integer pnum) {
        this.pnum = pnum;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private Integer cnum;
    private String nickname;
    private String cbody;
    private LocalDate cdate;
    private int clike;
    private boolean liked=false;
    private Integer pnum;
    private String uid;

}