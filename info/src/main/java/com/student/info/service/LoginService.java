package com.student.info.service;

import com.student.info.dto.LoginDto;
import com.student.info.dto.LoginResponse;
import com.student.info.entity.Students;
import com.student.info.ropository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private JwtService jwtService;

    private final StudentRepo repo;
    private final PasswordEncoder passwordEncoder;

    public LoginService(StudentRepo repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse studentLogin(LoginDto loginDto) {

        Students student = repo.findByEmail(loginDto.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Student not found, Register first"));

        if (!passwordEncoder.matches(
                loginDto.getPassword(),
                student.getPassword())) {

            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(
                student.getId(),
                student.getEmail()
        );

        return new LoginResponse(
                "Login successful",
                token
        );
    }
}