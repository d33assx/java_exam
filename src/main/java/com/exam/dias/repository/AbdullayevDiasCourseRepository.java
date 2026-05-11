package com.exam.dias.repository;

import com.exam.dias.entity.AbdullayevDiasCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbdullayevDiasCourseRepository extends JpaRepository<AbdullayevDiasCourse, Long> {
    Page<AbdullayevDiasCourse> findByCategoryId(Long categoryId, Pageable pageable);
    Page<AbdullayevDiasCourse> findByTeacherId(Long teacherId, Pageable pageable);
}
