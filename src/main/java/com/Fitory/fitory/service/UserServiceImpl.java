package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.User;
import com.Fitory.fitory.repository.IF_userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;



@Service
public class UserServiceImpl implements IF_UserService{

    @Autowired
    IF_userRepository userRepo;

    @Override
    public Optional<User> findById(String id) {
        return userRepo.findById(id);
    }
}
