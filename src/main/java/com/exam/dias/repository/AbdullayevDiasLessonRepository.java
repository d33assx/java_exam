package com.exam.dias.repository;

import com.exam.dias.entity.AbdullayevDiasLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbdullayevDiasLessonRepository extends JpaRepository<AbdullayevDiasLesson, Long> {
    List<AbdullayevDiasLesson> findByCourseIdOrderByOrderNumberAsc(Long courseId);
}
