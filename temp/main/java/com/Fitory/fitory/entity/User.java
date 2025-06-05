package com.Fitory.fitory.entity;

import com.Fitory.fitory.dto.UserDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    private String id;

    private String nickname;

    private String name;

    private String password;

    private String email;

    private String gender;

    private String birth;

    private String height;

    private String weight;

    public void update_user(UserDTO udto) {
        this.id = udto.getId();
        this.password = udto.getPassword();
        this.nickname = udto.getNickname();
        this.email = udto.getEmail();
        this.name = udto.getName();
        this.birth = udto.getBirth();
        this.gender = udto.getGender();
        this.height = udto.getHeight_cm();
        this.weight = udto.getWeight_kg();
    }


}
