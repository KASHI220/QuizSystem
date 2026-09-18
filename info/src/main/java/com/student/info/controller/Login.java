package com.student.info.controller;

import com.student.info.dto.LoginDto;
import com.student.info.dto.LoginResponse;
import com.student.info.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/auth")
public class Login {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginDto loginDto) {
        return loginService.studentLogin(loginDto);
    }
}