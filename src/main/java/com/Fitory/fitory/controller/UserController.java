package com.Fitory.fitory.controller;

import com.Fitory.fitory.entity.User;
import com.Fitory.fitory.repository.IF_userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    IF_userRepository userRepo;

    @GetMapping("/joinForm")
    public String join() {
        return "joinForm";
    }

    @PostMapping("/user_join")
    public String user_join(@ModelAttribute User user) {
        userRepo.save(user);
        return "redirect:/";
    }

    @GetMapping("/loginForm")
    public String login() {
        return "loginForm";
    }

    @GetMapping("/login")
    public String login(@RequestParam String user_id, String password) {
        Optional<User> user = userRepo.findById(user_id);
        if (user.isPresent()) {
            String pass = user.get().getUser_password();
            if (password.equals(pass)) {
                System.out.println("로그인 성공");
                return "redirect:/";
            } else {
                System.out.println("비밀번호 틀림");
                return "loginForm";
            }
        } else {
            System.out.println("아이디 없음");
            return "loginForm";
        }
    }
}
