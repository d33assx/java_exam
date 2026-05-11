package com.exam.dias.repository;

import com.exam.dias.entity.AbdullayevDiasReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbdullayevDiasReviewRepository extends JpaRepository<AbdullayevDiasReview, Long> {
    List<AbdullayevDiasReview> findByCourseId(Long courseId);
    List<AbdullayevDiasReview> findByUserId(Long userId);
}
