package com.Fitory.fitory.controller;

import com.Fitory.fitory.entity.ExerciseRoutine;
import com.Fitory.fitory.entity.User;
import com.Fitory.fitory.repository.UserRepository;
import com.Fitory.fitory.service.ExerciseRoutineService;
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

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {


    @Autowired
    UserService userService;
    @Autowired
    ExerciseRoutineService exerciseRoutineService;

    @GetMapping("/joinForm")
    public String join() {
        return "joinForm";
    }

    @PostMapping("/user_join")
    public String user_join(@ModelAttribute User user) {
        userService.save(user);
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

    @GetMapping("/mypage")
    public String mypage(HttpSession session, Model model) {
        String uid =session.getAttribute("user_id").toString();
       if(uid==null) {
           return "redirect:/login";
       }

        List<ExerciseRoutine> routineList = exerciseRoutineService.findByUserID(uid);
       if(routineList.isEmpty()) {
           System.out.println("루틴 리스트 빔");
       }else {
         System.out.println(routineList.get(0).getRoutineName()+"운동루틴 명");

       }
        Optional<User> user = userService.findById(uid);
        user.ifPresent(u -> model.addAttribute("user", u));
        model.addAttribute("routineList", routineList);
        return "/mypage";
    }
}
