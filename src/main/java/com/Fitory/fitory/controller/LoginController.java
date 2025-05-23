package com.Fitory.fitory.controller;

import com.Fitory.fitory.entity.User;
import com.Fitory.fitory.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/logingo")
    public String login(@RequestParam("id") String userid, @RequestParam("password") String password
            , HttpSession session) {
        User userone = loginService.searchid(userid);
        System.out.println(userone.getUid());
        System.out.println(userone.getPassword());

        if (userone.getUid() != null) {

            if (userone.getPassword().equals(password)) {
                System.out.println(userone);
                session.setAttribute("uid", userone.getUid());
                session.setAttribute("nickname", userone.getNickname());
                System.out.println(userone.getUid());
                return "main";
            }
        }
        return "redirect:/login";

    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("username");
        session.removeAttribute("userid");

        return "main";
    }
}


