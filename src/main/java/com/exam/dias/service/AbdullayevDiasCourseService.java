package com.exam.dias.service;

import com.exam.dias.dto.AbdullayevDiasCourseDto;
import com.exam.dias.entity.AbdullayevDiasCategory;
import com.exam.dias.entity.AbdullayevDiasCourse;
import com.exam.dias.entity.AbdullayevDiasUser;
import com.exam.dias.exception.AbdullayevDiasResourceNotFoundException;
import com.exam.dias.repository.AbdullayevDiasCategoryRepository;
import com.exam.dias.repository.AbdullayevDiasCourseRepository;
import com.exam.dias.repository.AbdullayevDiasUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AbdullayevDiasCourseService {

    private static final Logger logger = LoggerFactory.getLogger(AbdullayevDiasCourseService.class);

    private final AbdullayevDiasCourseRepository courseRepository;
    private final AbdullayevDiasUserRepository userRepository;
    private final AbdullayevDiasCategoryRepository categoryRepository;

    public AbdullayevDiasCourseService(AbdullayevDiasCourseRepository courseRepository,
                                       AbdullayevDiasUserRepository userRepository,
                                       AbdullayevDiasCategoryRepository categoryRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<AbdullayevDiasCourseDto> getAllCourses(Pageable pageable) {
        logger.info("Fetching all courses");
        return courseRepository.findAll(pageable).map(this::convertToDto);
    }

    public Page<AbdullayevDiasCourseDto> getCoursesByCategory(Long categoryId, Pageable pageable) {
        logger.info("Fetching courses by category: {}", categoryId);
        return courseRepository.findByCategoryId(categoryId, pageable).map(this::convertToDto);
    }

    public Page<AbdullayevDiasCourseDto> getCoursesByTeacher(Long teacherId, Pageable pageable) {
        logger.info("Fetching courses by teacher: {}", teacherId);
        return courseRepository.findByTeacherId(teacherId, pageable).map(this::convertToDto);
    }

    public AbdullayevDiasCourseDto getCourseById(Long id) {
        logger.info("Fetching course by id: {}", id);
        AbdullayevDiasCourse course = courseRepository.findById(id)
                .orElseThrow(() -> new AbdullayevDiasResourceNotFoundException("Course not found"));
        return convertToDto(course);
    }

    public AbdullayevDiasCourseDto createCourse(AbdullayevDiasCourseDto dto) {
        logger.info("Creating new course: {}", dto.getTitle());
        AbdullayevDiasCourse course = new AbdullayevDiasCourse();
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setPrice(dto.getPrice());

        AbdullayevDiasUser teacher = userRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new AbdullayevDiasResourceNotFoundException("Teacher not found"));
        course.setTeacher(teacher);

        if (dto.getCategoryId() != null) {
            AbdullayevDiasCategory category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new AbdullayevDiasResourceNotFoundException("Category not found"));
            course.setCategory(category);
        }

        AbdullayevDiasCourse saved = courseRepository.save(course);
        return convertToDto(saved);
    }

    public AbdullayevDiasCourseDto updateCourse(Long id, AbdullayevDiasCourseDto dto) {
        logger.info("Updating course: {}", id);
        AbdullayevDiasCourse course = courseRepository.findById(id)
                .orElseThrow(() -> new AbdullayevDiasResourceNotFoundException("Course not found"));

        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setPrice(dto.getPrice());

        if (dto.getCategoryId() != null) {
            AbdullayevDiasCategory category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new AbdullayevDiasResourceNotFoundException("Category not found"));
            course.setCategory(category);
        }

        AbdullayevDiasCourse updated = courseRepository.save(course);
        return convertToDto(updated);
    }

    public void deleteCourse(Long id) {
        logger.info("Deleting course: {}", id);
        if (!courseRepository.existsById(id)) {
            throw new AbdullayevDiasResourceNotFoundException("Course not found");
        }
        courseRepository.deleteById(id);
    }

    private AbdullayevDiasCourseDto convertToDto(AbdullayevDiasCourse course) {
        AbdullayevDiasCourseDto dto = new AbdullayevDiasCourseDto();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setPrice(course.getPrice());
        dto.setTeacherId(course.getTeacher().getId());
        dto.setTeacherName(course.getTeacher().getFullName());
        if (course.getCategory() != null) {
            dto.setCategoryId(course.getCategory().getId());
            dto.setCategoryName(course.getCategory().getName());
        }
        return dto;
    }
}
