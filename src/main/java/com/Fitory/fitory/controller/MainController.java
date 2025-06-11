package com.Fitory.fitory.controller;

import com.Fitory.fitory.entity.User;
import com.Fitory.fitory.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String home() {
        return "main";
    }

    @GetMapping("/logOut")
    public String logOut(HttpSession session) {
       session.invalidate();

        return "redirect:/";
    }
   
}
