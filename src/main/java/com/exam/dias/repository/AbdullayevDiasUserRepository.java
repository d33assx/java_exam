package com.exam.dias.repository;

import com.exam.dias.entity.AbdullayevDiasUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AbdullayevDiasUserRepository extends JpaRepository<AbdullayevDiasUser, Long> {
    Optional<AbdullayevDiasUser> findByEmail(String email);
    boolean existsByEmail(String email);
}
