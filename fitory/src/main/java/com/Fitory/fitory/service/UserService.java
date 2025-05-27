package com.Fitory.fitory.service;

import com.Fitory.fitory.dto.UserDTO;

public interface UserService {
    public void usersave(UserDTO udto);

    public boolean idchk(String id);

    public UserDTO login(String id);
}
