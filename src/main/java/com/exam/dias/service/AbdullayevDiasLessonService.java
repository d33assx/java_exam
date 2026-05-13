package com.exam.dias.service;

import com.exam.dias.dto.AbdullayevDiasLessonDto;
import com.exam.dias.entity.AbdullayevDiasCourse;
import com.exam.dias.entity.AbdullayevDiasLesson;
import com.exam.dias.exception.AbdullayevDiasResourceNotFoundException;
import com.exam.dias.repository.AbdullayevDiasCourseRepository;
import com.exam.dias.repository.AbdullayevDiasLessonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbdullayevDiasLessonService {

    private static final Logger logger = LoggerFactory.getLogger(AbdullayevDiasLessonService.class);

    private final AbdullayevDiasLessonRepository lessonRepository;
    private final AbdullayevDiasCourseRepository courseRepository;
    private final AbdullayevDiasFileStorageService fileStorageService;

    public AbdullayevDiasLessonService(AbdullayevDiasLessonRepository lessonRepository,
                                       AbdullayevDiasCourseRepository courseRepository,
                                       AbdullayevDiasFileStorageService fileStorageService) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
        this.fileStorageService = fileStorageService;
    }

    public List<AbdullayevDiasLessonDto> getLessonsByCourse(Long courseId) {
        logger.info("Fetching lessons for course: {}", courseId);
        return lessonRepository.findByCourseIdOrderByOrderNumberAsc(courseId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public AbdullayevDiasLessonDto getLessonById(Long id) {
        logger.info("Fetching lesson by id: {}", id);
        AbdullayevDiasLesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new AbdullayevDiasResourceNotFoundException("Lesson not found"));
        return convertToDto(lesson);
    }

    public AbdullayevDiasLessonDto createLesson(AbdullayevDiasLessonDto dto) {
        logger.info("Creating new lesson: {}", dto.getTitle());
        AbdullayevDiasLesson lesson = new AbdullayevDiasLesson();
        lesson.setTitle(dto.getTitle());
        lesson.setContent(dto.getContent());
        lesson.setOrderNumber(dto.getOrderNumber());

        AbdullayevDiasCourse course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new AbdullayevDiasResourceNotFoundException("Course not found"));
        lesson.setCourse(course);

        AbdullayevDiasLesson saved = lessonRepository.save(lesson);
        return convertToDto(saved);
    }

    public AbdullayevDiasLessonDto updateLesson(Long id, AbdullayevDiasLessonDto dto) {
        logger.info("Updating lesson: {}", id);
        AbdullayevDiasLesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new AbdullayevDiasResourceNotFoundException("Lesson not found"));

        lesson.setTitle(dto.getTitle());
        lesson.setContent(dto.getContent());
        lesson.setOrderNumber(dto.getOrderNumber());

        AbdullayevDiasLesson updated = lessonRepository.save(lesson);
        return convertToDto(updated);
    }

    public void deleteLesson(Long id) {
        logger.info("Deleting lesson: {}", id);
        if (!lessonRepository.existsById(id)) {
            throw new AbdullayevDiasResourceNotFoundException("Lesson not found");
        }
        lessonRepository.deleteById(id);
    }

    public String uploadMaterial(Long lessonId, MultipartFile file) {
        logger.info("Uploading material for lesson: {}", lessonId);
        AbdullayevDiasLesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new AbdullayevDiasResourceNotFoundException("Lesson not found"));

        String fileName = fileStorageService.storeFile(file);
        lesson.setMaterialFileName(fileName);
        lessonRepository.save(lesson);

        return fileName;
    }

    private AbdullayevDiasLessonDto convertToDto(AbdullayevDiasLesson lesson) {
        AbdullayevDiasLessonDto dto = new AbdullayevDiasLessonDto();
        dto.setId(lesson.getId());
        dto.setTitle(lesson.getTitle());
        dto.setContent(lesson.getContent());
        dto.setCourseId(lesson.getCourse().getId());
        dto.setOrderNumber(lesson.getOrderNumber());
        dto.setMaterialFileName(lesson.getMaterialFileName());
        return dto;
    }
}
