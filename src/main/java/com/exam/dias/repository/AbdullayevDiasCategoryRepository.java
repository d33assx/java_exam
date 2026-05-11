package com.exam.dias.repository;

import com.exam.dias.entity.AbdullayevDiasCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbdullayevDiasCategoryRepository extends JpaRepository<AbdullayevDiasCategory, Long> {
}
