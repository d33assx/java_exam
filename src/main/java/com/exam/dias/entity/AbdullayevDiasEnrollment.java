package com.exam.dias.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbdullayevDiasEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AbdullayevDiasUser user;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private AbdullayevDiasCourse course;

    @Column(nullable = false)
    private LocalDateTime enrolledAt;

    private Integer progress;

    @PrePersist
    protected void onCreate() {
        enrolledAt = LocalDateTime.now();
        if (progress == null) {
            progress = 0;
        }
    }
}
