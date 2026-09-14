package com.student.info.service;

import com.student.info.entity.Students;
import com.student.info.ropository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentRegisterService {
    @Autowired
    StudentRepo repo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Students registerStudent(Students student) {
        student.setPassword(
                passwordEncoder.encode(student.getPassword())
        );

        return repo.save(student);
    }
}
