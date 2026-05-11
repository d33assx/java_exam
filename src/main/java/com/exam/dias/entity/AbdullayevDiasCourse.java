package com.exam.dias.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbdullayevDiasCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private AbdullayevDiasUser teacher;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private AbdullayevDiasCategory category;

    private Double price;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<AbdullayevDiasLesson> lessons;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<AbdullayevDiasEnrollment> enrollments;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<AbdullayevDiasReview> reviews;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
