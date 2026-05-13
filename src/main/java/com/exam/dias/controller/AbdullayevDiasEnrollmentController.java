package com.exam.dias.controller;

import com.exam.dias.dto.AbdullayevDiasEnrollmentDto;
import com.exam.dias.service.AbdullayevDiasEnrollmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class AbdullayevDiasEnrollmentController {

    private final AbdullayevDiasEnrollmentService enrollmentService;

    public AbdullayevDiasEnrollmentController(AbdullayevDiasEnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AbdullayevDiasEnrollmentDto>> getEnrollmentsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByUser(userId));
    }

    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public ResponseEntity<List<AbdullayevDiasEnrollmentDto>> getEnrollmentsByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByCourse(courseId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbdullayevDiasEnrollmentDto> getEnrollmentById(@PathVariable Long id) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentById(id));
    }

    @PostMapping
    public ResponseEntity<AbdullayevDiasEnrollmentDto> createEnrollment(@RequestParam Long userId,
                                                                         @RequestParam Long courseId) {
        return ResponseEntity.ok(enrollmentService.createEnrollment(userId, courseId));
    }

    @PutMapping("/{id}/progress")
    public ResponseEntity<AbdullayevDiasEnrollmentDto> updateProgress(@PathVariable Long id,
                                                                       @RequestParam Integer progress) {
        return ResponseEntity.ok(enrollmentService.updateProgress(id, progress));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }
}
