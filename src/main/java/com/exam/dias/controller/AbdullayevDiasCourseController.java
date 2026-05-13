package com.exam.dias.controller;

import com.exam.dias.dto.AbdullayevDiasCourseDto;
import com.exam.dias.service.AbdullayevDiasCourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class AbdullayevDiasCourseController {

    private final AbdullayevDiasCourseService courseService;

    public AbdullayevDiasCourseController(AbdullayevDiasCourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<Page<AbdullayevDiasCourseDto>> getAllCourses(Pageable pageable) {
        return ResponseEntity.ok(courseService.getAllCourses(pageable));
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<AbdullayevDiasCourseDto>> getCoursesByCategory(@RequestParam Long categoryId,
                                                                               Pageable pageable) {
        return ResponseEntity.ok(courseService.getCoursesByCategory(categoryId, pageable));
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<Page<AbdullayevDiasCourseDto>> getCoursesByTeacher(@PathVariable Long teacherId,
                                                                              Pageable pageable) {
        return ResponseEntity.ok(courseService.getCoursesByTeacher(teacherId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbdullayevDiasCourseDto> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public ResponseEntity<AbdullayevDiasCourseDto> createCourse(@RequestBody AbdullayevDiasCourseDto dto) {
        return ResponseEntity.ok(courseService.createCourse(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public ResponseEntity<AbdullayevDiasCourseDto> updateCourse(@PathVariable Long id,
                                                                 @RequestBody AbdullayevDiasCourseDto dto) {
        return ResponseEntity.ok(courseService.updateCourse(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
