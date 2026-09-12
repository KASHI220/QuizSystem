package com.student.info.service;

import com.student.info.entity.Students;
import com.student.info.ropository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentRegisterService {
    @Autowired
    StudentRepo repo;

    public Students registerStudent(Students student) {
          return repo.save(student);
    }
}
