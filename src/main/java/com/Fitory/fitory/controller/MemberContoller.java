package com.Fitory.fitory.controller;

import com.Fitory.fitory.entity.User;
import com.Fitory.fitory.repository.UserRepository;
import com.Fitory.fitory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberContoller {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String main() {
        return "main";
    }

    @GetMapping("signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @PostMapping("/user")
    public String demoController(@ModelAttribute User user) {
        System.out.println(user.getBirth());

    userService.save(user);
        System.out.println("저장");
        return "main";
    }

}
