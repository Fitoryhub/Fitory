package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.User;
import com.Fitory.fitory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements IF_LoginService{

    @Autowired
    UserRepository userRepository;

    public User searchid(String id) {

        return userRepository.findByUid(id);

    }
}
