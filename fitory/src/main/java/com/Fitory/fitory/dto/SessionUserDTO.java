package com.Fitory.fitory.dto;

public class SessionUserDTO {
    private String id;
    private String nickname;

    // 생성자
    public SessionUserDTO(String id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    // getter, setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
}