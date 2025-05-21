package com.Fitory.fitory.controller;

import com.Fitory.fitory.entity.User;
import com.Fitory.fitory.repository.IF_userRepository;
import com.Fitory.fitory.service.IF_UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    IF_userRepository userRepo;
    @Autowired
    IF_UserService userService;

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

    @PostMapping("/login")
    public String login(@RequestParam String user_id, String password, Model model, HttpSession session) {
       Optional<User> user = userService.findById(user_id);
        if(user.isPresent()) {
            User u = user.get();
            if(u.getUser_password().equals(password)) {
                session.setAttribute("user_id", user_id);
                System.out.println("로그인 성공"+user_id);
            }else{
                System.out.println("비밀번호 틀림");
            }
        }else{
            System.out.println("아이디 없음");
        }
        session.setAttribute("user_id", user_id);
        return "main";
    }
}
