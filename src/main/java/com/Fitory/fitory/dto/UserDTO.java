package com.Fitory.fitory.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String user_id;
    private String nickname;
    private String user_name;
    private String user_password;
    private String email;
    private String gender;
    private String birth;
    private int height_cm;
    private int weight_kg;

}
