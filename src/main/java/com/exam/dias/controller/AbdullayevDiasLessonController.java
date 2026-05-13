package com.exam.dias.controller;

import com.exam.dias.dto.AbdullayevDiasLessonDto;
import com.exam.dias.service.AbdullayevDiasFileStorageService;
import com.exam.dias.service.AbdullayevDiasLessonService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class AbdullayevDiasLessonController {

    private final AbdullayevDiasLessonService lessonService;
    private final AbdullayevDiasFileStorageService fileStorageService;

    public AbdullayevDiasLessonController(AbdullayevDiasLessonService lessonService,
                                          AbdullayevDiasFileStorageService fileStorageService) {
        this.lessonService = lessonService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<AbdullayevDiasLessonDto>> getLessonsByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(lessonService.getLessonsByCourse(courseId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbdullayevDiasLessonDto> getLessonById(@PathVariable Long id) {
        return ResponseEntity.ok(lessonService.getLessonById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public ResponseEntity<AbdullayevDiasLessonDto> createLesson(@RequestBody AbdullayevDiasLessonDto dto) {
        return ResponseEntity.ok(lessonService.createLesson(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public ResponseEntity<AbdullayevDiasLessonDto> updateLesson(@PathVariable Long id,
                                                                 @RequestBody AbdullayevDiasLessonDto dto) {
        return ResponseEntity.ok(lessonService.updateLesson(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/upload")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public ResponseEntity<String> uploadMaterial(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        String fileName = lessonService.uploadMaterial(id, file);
        return ResponseEntity.ok(fileName);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
