package com.student.info.controller;

import com.student.info.dto.LoginDto;
import com.student.info.entity.Students;
import com.student.info.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;

@RestController
@RequestMapping("/app/auth")
public class Login {
    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public Students login (@RequestBody LoginDto loginDto){
        return loginService.studentLogin(loginDto);
    }

}
