package com.Fitory.fitory.service;

import com.Fitory.fitory.dto.UserDTO;
import com.Fitory.fitory.entity.User;
import com.Fitory.fitory.repository.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public void usersave(UserDTO udto) {
        User user = new User();
        user.update_user(udto);
        userRepository.save(user);
        System.out.println("저장");
    }

    @Override
    public boolean idchk(String id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean nicknamechk(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Override
    public UserDTO userInfo(String id) {
        Optional<User> optionalUser2 = userRepository.findById(id);
        UserDTO udto = new UserDTO();
        if (optionalUser2.isPresent()) {
            User user = optionalUser2.get();
            udto.update_user(user);
            return udto;
        } else {
            return null;
        }
    }

    @Override
    public UserDTO login(String id) {
        Optional<User> optionalUser = userRepository.findById(id);
        UserDTO udto = new UserDTO();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            udto.update_user(user);
            return udto;
        } else {
            return null;
        }
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }
}
