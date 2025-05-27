package com.Fitory.fitory.controller;

import com.Fitory.fitory.dto.SessionUserDTO;
import com.Fitory.fitory.dto.UserDTO;
import com.Fitory.fitory.repository.UserRepository;
import com.Fitory.fitory.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Controller
public class MemberContoller {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServiceImpl userServiceImpl;

    // 메인 홈페이지
    @GetMapping("/")
    public String main(HttpSession session, Model model) {
        SessionUserDTO userInfo = (SessionUserDTO) session.getAttribute("userInfo");
        model.addAttribute("userInfo", userInfo);
        return "main";
    }

    // 회원가입 페이지 이동
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    //로그인 페이지 이동
    @GetMapping("/login")
    public String login() {
        return "login";
    }


    // 로그인
    @PostMapping("/signin")
    public String signin(HttpServletRequest request, Model model, @ModelAttribute UserDTO body) {
        UserDTO udto = new UserDTO();
        udto = userServiceImpl.login(body.getId());
        if (udto != null) {
            if(udto.getPassword().equals(body.getPassword())){
                HttpSession session = request.getSession();
                SessionUserDTO suser = new SessionUserDTO(udto.getId(), udto.getNickname());
                session.setAttribute("userInfo", suser);
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

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션이 없으면 null 반환
        if(session != null) {
            session.invalidate();  // 세션 무효화(삭제)
        }
        return "main";
    }

    // id 중복체크
    @GetMapping("/idChk")
    @ResponseBody
    public Map<String, Boolean> IDcheck(@RequestParam String id){
        boolean exists = userServiceImpl.idchk(id);
        Map<String, Boolean> result = new HashMap<>();
        result.put("exists", exists);
        return result;
    }

    // 회원가입
    @PostMapping("/user")
    public String demoController(@ModelAttribute UserDTO body) {
        userServiceImpl.usersave(body);
        return "main";
    }
}
