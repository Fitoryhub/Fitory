package com.Fitory.fitory.service;

import com.Fitory.fitory.dto.UserDTO;
import com.Fitory.fitory.entity.User;

import java.util.Optional;

public interface UserService {
    public void usersave(UserDTO udto);

    public boolean idchk(String id);

    public boolean nicknamechk(String nickname);

    public UserDTO login(String id);

    public UserDTO userInfo(String id);

    public Optional<User> findById(String id);

    public void deleteUser(String id);
}
