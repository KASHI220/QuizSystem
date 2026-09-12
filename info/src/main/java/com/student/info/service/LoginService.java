package com.student.info.service;

import com.student.info.dto.LoginDto;
import com.student.info.entity.Students;
import com.student.info.ropository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {
    @Autowired
    StudentRepo repo;

    public Students studentLogin(LoginDto loginDto) {

        Students st = repo.findByEmail(loginDto.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Student not found, Register first"));

        return st;

    }


}
