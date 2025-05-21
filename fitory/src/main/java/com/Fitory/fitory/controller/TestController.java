package com.Fitory.fitory.controller;

import com.Fitory.fitory.dto.UserDTO;
import com.Fitory.fitory.entity.User;
import com.Fitory.fitory.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TestController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/user")
    public String demoController(@ModelAttribute UserDTO body) {
        User user = new User();
        user.update_user(body.getId(), body.getPassword(), body.getEmail(), body.getName());
        userRepository.save(user);
        System.out.println("저장");

        return "login";
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        List<User> userList = userRepository.findAll();
        model.addAttribute("alllist", userList); // 💡 "alllist" 이름 일치!
        return "list"; // → templates/userTable.html
    }


}
