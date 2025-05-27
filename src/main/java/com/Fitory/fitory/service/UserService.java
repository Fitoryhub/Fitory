package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.User;
import com.Fitory.fitory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public interface UserService {

    public void save(User user);

    Optional<User> findById(String id);

}
