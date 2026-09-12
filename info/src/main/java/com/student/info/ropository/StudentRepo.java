package com.student.info.ropository;

import com.student.info.entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<Students, Long> {

    Optional<Students> findByEmail(String email);
}
