package com.student.info.controller;

import com.student.info.entity.Students;
import com.student.info.service.StudentRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/auth")
public class Register {
    @Autowired
    StudentRegisterService registerService;
    @PostMapping("/register")
    public Students register(@RequestBody Students student){
        return registerService.registerStudent(student);
    }
}
