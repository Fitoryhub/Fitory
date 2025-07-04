package com.Fitory.fitory.controller;

import com.Fitory.fitory.dto.SessionUserDTO;
import com.Fitory.fitory.dto.UserDTO;
import com.Fitory.fitory.dto.UserHealthInfoDTO;
import com.Fitory.fitory.entity.ExerciseRoutine;
import com.Fitory.fitory.entity.User;
import com.Fitory.fitory.service.ExerciseRoutineService;
import com.Fitory.fitory.service.UserHealthServiceImpl;
import com.Fitory.fitory.service.UserService;
import com.Fitory.fitory.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class MemberContoller {

    @Autowired
    UserService userService;
    @Autowired
    ExerciseRoutineService exerciseRoutineService;
    @Autowired
    UserServiceImpl userServiceImpl;
    @Autowired
    UserHealthServiceImpl userhealthserviceimpl;

    // 회원가입 페이지 이동
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    // 로그인 페이지 이동
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
            if (udto.getPassword().equals(body.getPassword())) {
                HttpSession session = request.getSession();
                SessionUserDTO suser = new SessionUserDTO(udto.getId(), udto.getNickname());
                session.setAttribute("userInfo", suser);
                return "redirect:/";
            } else {
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
        if (session != null) {
            session.invalidate(); // 세션 무효화(삭제)
        }
        return "main";
    }

    @GetMapping("/mypage")
    public String mypage(HttpSession session, Model model) {
        UserDTO udto = new UserDTO();
        UserHealthInfoDTO hdto = new UserHealthInfoDTO();
        SessionUserDTO userInfo = (SessionUserDTO) session.getAttribute("userInfo");
        if (userInfo == null) {
            return "redirect:/login"; // 로그인 안 된 상태일 경우
        }

        List<ExerciseRoutine> routineList = exerciseRoutineService.findByUserID(userInfo.getId());
        Optional<User> user = userService.findById(userInfo.getId());
        user.ifPresent(u -> model.addAttribute("user", u));
        model.addAttribute("routineList", routineList);
        udto = userServiceImpl.userInfo(userInfo.getId());
        model.addAttribute("user", udto);
        hdto = userhealthserviceimpl.findInfo(userInfo.getId());
        model.addAttribute("userInfo", hdto);
        return "mypage";
    }

    // id 중복체크
    @GetMapping("/idChk")
    @ResponseBody
    public Map<String, Boolean> IDcheck(@RequestParam String id) {
        boolean exists = userServiceImpl.idchk(id);
        Map<String, Boolean> result = new HashMap<>();
        result.put("exists", exists);
        return result;
    }

    // 닉네임 중복체크
    @GetMapping("/nickChk")
    @ResponseBody
    public Map<String, Boolean> nickChk(@RequestParam String nickname) {
        boolean exists = userServiceImpl.nicknamechk(nickname);
        Map<String, Boolean> result = new HashMap<>();
        result.put("exists", exists);
        return result;
    }

    @PostMapping("/edit_passChk")
    @ResponseBody
    public UserDTO passChk(HttpSession session, @RequestBody Map<String, String> payload) {
        UserDTO udto = new UserDTO();
        SessionUserDTO userInfo = (SessionUserDTO) session.getAttribute("userInfo");
        udto = userServiceImpl.userInfo(userInfo.getId());
        String pass = payload.get("pass");

        if(udto.getPassword().equals(pass)){
            return udto;
        } else{
            return null;
        }
    }

    // 회원가입
    @PostMapping("/user")
    public String demoController(@ModelAttribute UserDTO body) {
        userServiceImpl.usersave(body);
        return "main";
    }

    @PostMapping("/user/update")
    public String updateUser(HttpSession session,@ModelAttribute UserDTO body) {
        SessionUserDTO userInfo = (SessionUserDTO) session.getAttribute("userInfo");
        body.setId(userInfo.getId());
        userServiceImpl.usersave(body);
        return "main";
    }

    @GetMapping("/user/withdraw")
    public String deleteUser(HttpSession session) {
        SessionUserDTO userInfo = (SessionUserDTO) session.getAttribute("userInfo");
        userServiceImpl.deleteUser(userInfo.getId());
        session.invalidate();
        return "main";
    }

    @PostMapping("/healthInfo")
    public String healthInfo_save(HttpSession session, @ModelAttribute UserHealthInfoDTO udto) {
        SessionUserDTO userInfo = (SessionUserDTO) session.getAttribute("userInfo");
        udto.setUid(userInfo.getId());
        if(udto.getTargetWeight().isEmpty()){
            udto.setTargetWeight("0.0");
        }
        if(udto.getBodyFat().isEmpty()){
            udto.setBodyFat("0.0");
        }
        userhealthserviceimpl.Infosave(udto);
        return "redirect:/mypage";
    }

    @PostMapping("/healthInfoMod")
    public String healthInfo_mod(HttpSession session, @ModelAttribute UserHealthInfoDTO udto) {
        SessionUserDTO userInfo = (SessionUserDTO) session.getAttribute("userInfo");
        udto.setUid(userInfo.getId());
        System.out.println(udto.getTargetWeight()+"getTargetWeight입니당!@%!#%!");
        System.out.println(udto.getBodyFat()+"getBodyFat입니당!@%!#%!");
        if(udto.getTargetWeight().isEmpty()){
            udto.setTargetWeight("0.0");
        }
        if(udto.getBodyFat().isEmpty()){
            udto.setBodyFat("0.0");
        }
        userhealthserviceimpl.Infosave(udto);
        return "redirect:/mypage";
    }

    @GetMapping("/edit")
    public String profile_edit() {
        return "profile_edit";
    }

}
