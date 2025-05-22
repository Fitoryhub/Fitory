package com.Fitory.fitory.controller;

import com.Fitory.fitory.entity.User;
import com.Fitory.fitory.repository.UserRepository;
import com.Fitory.fitory.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepo;
    @Autowired
    UserService userService;

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
    public String login(@RequestParam String user_id, String password, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
       Optional<User> user = userService.findById(user_id);
        if(user.isPresent()) {
            User u = user.get();
            if(u.getUserPassword().equals(password)) {
                session.setAttribute("user_id", user_id);
                model.addAttribute("user", u);
                System.out.println("로그인 성공"+user_id);
                return "main";
            }else{
                redirectAttributes.addFlashAttribute("error", "비밀번호가 들렸습니다.");
                return "redirect:/login";
            }
        }else{
            redirectAttributes.addFlashAttribute("error", "존재하지 않는 아이디입니다.");
            return "redirect:/login";
        }
    }
}
