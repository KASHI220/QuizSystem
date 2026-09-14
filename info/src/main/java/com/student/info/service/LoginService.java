package com.student.info.service;

import com.student.info.dto.LoginDto;
import com.student.info.entity.Students;
import com.student.info.ropository.StudentRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final StudentRepo repo;
    private final PasswordEncoder passwordEncoder;

    public LoginService(StudentRepo repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    public Students studentLogin(LoginDto loginDto) {

        Students st = repo.findByEmail(loginDto.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Student not found, Register first"));

        if (!passwordEncoder.matches(
                loginDto.getPassword(),
                st.getPassword())) {

            throw new RuntimeException("Invalid password");
        }

        return st;
    }
}