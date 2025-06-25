package com.Fitory.fitory.controller.api;
import com.Fitory.fitory.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/signup")
public class SignupApiController {

    @Autowired
    UserServiceImpl userService;


    @GetMapping("emailchk")
    public boolean emailchk (@RequestParam String inputEmail) {
        System.out.println(userService.findByEmail(inputEmail)+"결과!$%!%#!%#");
        return userService.findByEmail(inputEmail);
    }
}
