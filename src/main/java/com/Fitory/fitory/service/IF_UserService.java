package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.User;
import com.Fitory.fitory.repository.IF_userRepository;

import java.util.Optional;

public interface IF_UserService  {
    Optional<User> findById(String id);
}
