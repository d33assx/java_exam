package com.exam.dias.repository;

import com.exam.dias.entity.AbdullayevDiasEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AbdullayevDiasEnrollmentRepository extends JpaRepository<AbdullayevDiasEnrollment, Long> {
    List<AbdullayevDiasEnrollment> findByUserId(Long userId);
    List<AbdullayevDiasEnrollment> findByCourseId(Long courseId);
    Optional<AbdullayevDiasEnrollment> findByUserIdAndCourseId(Long userId, Long courseId);
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);
}
