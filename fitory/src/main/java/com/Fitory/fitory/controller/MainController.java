package com.Fitory.fitory.controller;

import com.Fitory.fitory.dto.SessionUserDTO;
import com.Fitory.fitory.dto.UserDTO;
import com.Fitory.fitory.dto.UserHealthInfoDTO;
import com.Fitory.fitory.entity.Board;
import com.Fitory.fitory.entity.ExerciseRoutine;
import com.Fitory.fitory.entity.User;
import com.Fitory.fitory.service.BoardService;
import com.Fitory.fitory.service.UserHealthServiceImpl;
import com.Fitory.fitory.service.UserService;
import com.Fitory.fitory.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class MainController {

    @Autowired
    UserService userService;

    @Autowired
    BoardService boardService;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    UserHealthServiceImpl userhealthserviceimpl;
    // 메인 홈페이지
    @GetMapping("/")
    public String main(HttpSession session, Model model) {
        SessionUserDTO userInfo = (SessionUserDTO) session.getAttribute("userInfo");
        model.addAttribute("userInfo", userInfo);
        String category ="운동꿀팁";
        List<Board> boardList =boardService.top7(category);

        model.addAttribute("boardList", boardList);
        return "main";
    }

    @GetMapping("/recommendations")
    public String main12(HttpSession session, Model model) {
        UserDTO udto = new UserDTO();
        UserHealthInfoDTO hdto = new UserHealthInfoDTO();
        SessionUserDTO userInfo = (SessionUserDTO) session.getAttribute("userInfo");
        if (userInfo == null) {
            return "redirect:/login"; // 로그인 안 된 상태일 경우
        }
        udto = userServiceImpl.userInfo(userInfo.getId());
        model.addAttribute("user", udto);
        hdto = userhealthserviceimpl.findInfo(userInfo.getId());
        model.addAttribute("userInfo", hdto);
        return "recommendations";
    }

    @GetMapping("/logOut")
    public String logOut(HttpSession session) {
       session.invalidate();

        return "redirect:/";
    }
    @GetMapping("/boards")
    @ResponseBody
    public  Map<String,Object> selectboards(@RequestParam String category) {
        List<Board> boardList =boardService.top7(category);
        Map<String,Object> map = new HashMap<>();
        map.put("boardList",boardList);
        return map;
    }
}
