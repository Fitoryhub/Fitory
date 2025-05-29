package com.Fitory.fitory.entity;

import com.Fitory.fitory.dto.UserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
