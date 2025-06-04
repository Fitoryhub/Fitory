package com.Fitory.fitory.controller;

import com.Fitory.fitory.entity.User;
import com.Fitory.fitory.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MypageController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/mypage")
    public String Mypage(Model model , HttpSession session) {
        if(session!=null) {

        String uid= session.getAttribute("uid").toString();
        User user = userRepository.findByUid(uid);
        model.addAttribute("user", user);
        }
        return "Mypage";
    }
}
