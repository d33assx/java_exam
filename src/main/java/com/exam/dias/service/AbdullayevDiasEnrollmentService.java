package com.exam.dias.service;

import com.exam.dias.dto.AbdullayevDiasEnrollmentDto;
import com.exam.dias.entity.AbdullayevDiasCourse;
import com.exam.dias.entity.AbdullayevDiasEnrollment;
import com.exam.dias.entity.AbdullayevDiasUser;
import com.exam.dias.exception.AbdullayevDiasResourceNotFoundException;
import com.exam.dias.repository.AbdullayevDiasCourseRepository;
import com.exam.dias.repository.AbdullayevDiasEnrollmentRepository;
import com.exam.dias.repository.AbdullayevDiasUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbdullayevDiasEnrollmentService {

    private static final Logger logger = LoggerFactory.getLogger(AbdullayevDiasEnrollmentService.class);

    private final AbdullayevDiasEnrollmentRepository enrollmentRepository;
    private final AbdullayevDiasUserRepository userRepository;
    private final AbdullayevDiasCourseRepository courseRepository;

    public AbdullayevDiasEnrollmentService(AbdullayevDiasEnrollmentRepository enrollmentRepository,
                                           AbdullayevDiasUserRepository userRepository,
                                           AbdullayevDiasCourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public List<AbdullayevDiasEnrollmentDto> getEnrollmentsByUser(Long userId) {
        logger.info("Fetching enrollments for user: {}", userId);
        return enrollmentRepository.findByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<AbdullayevDiasEnrollmentDto> getEnrollmentsByCourse(Long courseId) {
        logger.info("Fetching enrollments for course: {}", courseId);
        return enrollmentRepository.findByCourseId(courseId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public AbdullayevDiasEnrollmentDto getEnrollmentById(Long id) {
        logger.info("Fetching enrollment by id: {}", id);
        AbdullayevDiasEnrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new AbdullayevDiasResourceNotFoundException("Enrollment not found"));
        return convertToDto(enrollment);
    }

    public AbdullayevDiasEnrollmentDto createEnrollment(Long userId, Long courseId) {
        logger.info("Creating enrollment for user: {} and course: {}", userId, courseId);

        if (enrollmentRepository.existsByUserIdAndCourseId(userId, courseId)) {
            throw new RuntimeException("User already enrolled in this course");
        }

        AbdullayevDiasUser user = userRepository.findById(userId)
                .orElseThrow(() -> new AbdullayevDiasResourceNotFoundException("User not found"));

        AbdullayevDiasCourse course = courseRepository.findById(courseId)
                .orElseThrow(() -> new AbdullayevDiasResourceNotFoundException("Course not found"));

        AbdullayevDiasEnrollment enrollment = new AbdullayevDiasEnrollment();
        enrollment.setUser(user);
        enrollment.setCourse(course);

        AbdullayevDiasEnrollment saved = enrollmentRepository.save(enrollment);
        return convertToDto(saved);
    }

    public AbdullayevDiasEnrollmentDto updateProgress(Long enrollmentId, Integer progress) {
        logger.info("Updating progress for enrollment: {}", enrollmentId);
        AbdullayevDiasEnrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new AbdullayevDiasResourceNotFoundException("Enrollment not found"));

        enrollment.setProgress(progress);
        AbdullayevDiasEnrollment updated = enrollmentRepository.save(enrollment);
        return convertToDto(updated);
    }

    public void deleteEnrollment(Long id) {
        logger.info("Deleting enrollment: {}", id);
        if (!enrollmentRepository.existsById(id)) {
            throw new AbdullayevDiasResourceNotFoundException("Enrollment not found");
        }
        enrollmentRepository.deleteById(id);
    }

    private AbdullayevDiasEnrollmentDto convertToDto(AbdullayevDiasEnrollment enrollment) {
        AbdullayevDiasEnrollmentDto dto = new AbdullayevDiasEnrollmentDto();
        dto.setId(enrollment.getId());
        dto.setUserId(enrollment.getUser().getId());
        dto.setUserEmail(enrollment.getUser().getEmail());
        dto.setCourseId(enrollment.getCourse().getId());
        dto.setCourseTitle(enrollment.getCourse().getTitle());
        dto.setProgress(enrollment.getProgress());
        return dto;
    }
}
