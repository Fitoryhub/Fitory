package com.Fitory.fitory.controller;

import com.Fitory.fitory.dto.UserDTO;
import com.Fitory.fitory.entity.User;
import com.Fitory.fitory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class MemberContoller {

    @Autowired
    UserRepository userRepository;

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


    @PostMapping("signin")
    public String signin(Model model, @ModelAttribute UserDTO body) {
        Optional<User> optionalUser = userRepository.findById(body.getId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();  // 이제 User 객체
            if(user.getPassword().equals(body.getPassword())){
                return "redirect:/";
            }
            else {
                model.addAttribute("message", "아이디 또는 비밀번호가 틀렸습니다.");
                return "login";
            }
        } else {
            model.addAttribute("message", "아이디 또는 비밀번호가 틀렸습니다.");
            return "login";
        }
    }

    @PostMapping("/user")
    public String demoController(@ModelAttribute UserDTO body) {
        User user = new User();
        user.update_user(body);

        userRepository.save(user);
        System.out.println("저장");
        return "main";
    }
}
