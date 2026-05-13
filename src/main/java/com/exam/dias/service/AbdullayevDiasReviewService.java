package com.exam.dias.service;

import com.exam.dias.dto.AbdullayevDiasReviewDto;
import com.exam.dias.entity.AbdullayevDiasCourse;
import com.exam.dias.entity.AbdullayevDiasReview;
import com.exam.dias.entity.AbdullayevDiasUser;
import com.exam.dias.exception.AbdullayevDiasResourceNotFoundException;
import com.exam.dias.repository.AbdullayevDiasCourseRepository;
import com.exam.dias.repository.AbdullayevDiasReviewRepository;
import com.exam.dias.repository.AbdullayevDiasUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbdullayevDiasReviewService {

    private static final Logger logger = LoggerFactory.getLogger(AbdullayevDiasReviewService.class);

    private final AbdullayevDiasReviewRepository reviewRepository;
    private final AbdullayevDiasUserRepository userRepository;
    private final AbdullayevDiasCourseRepository courseRepository;

    public AbdullayevDiasReviewService(AbdullayevDiasReviewRepository reviewRepository,
                                       AbdullayevDiasUserRepository userRepository,
                                       AbdullayevDiasCourseRepository courseRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public List<AbdullayevDiasReviewDto> getReviewsByCourse(Long courseId) {
        logger.info("Fetching reviews for course: {}", courseId);
        return reviewRepository.findByCourseId(courseId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<AbdullayevDiasReviewDto> getReviewsByUser(Long userId) {
        logger.info("Fetching reviews by user: {}", userId);
        return reviewRepository.findByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public AbdullayevDiasReviewDto getReviewById(Long id) {
        logger.info("Fetching review by id: {}", id);
        AbdullayevDiasReview review = reviewRepository.findById(id)
                .orElseThrow(() -> new AbdullayevDiasResourceNotFoundException("Review not found"));
        return convertToDto(review);
    }

    public AbdullayevDiasReviewDto createReview(AbdullayevDiasReviewDto dto) {
        logger.info("Creating new review for course: {}", dto.getCourseId());

        AbdullayevDiasUser user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new AbdullayevDiasResourceNotFoundException("User not found"));

        AbdullayevDiasCourse course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new AbdullayevDiasResourceNotFoundException("Course not found"));

        AbdullayevDiasReview review = new AbdullayevDiasReview();
        review.setUser(user);
        review.setCourse(course);
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());

        AbdullayevDiasReview saved = reviewRepository.save(review);
        return convertToDto(saved);
    }

    public AbdullayevDiasReviewDto updateReview(Long id, AbdullayevDiasReviewDto dto) {
        logger.info("Updating review: {}", id);
        AbdullayevDiasReview review = reviewRepository.findById(id)
                .orElseThrow(() -> new AbdullayevDiasResourceNotFoundException("Review not found"));

        review.setRating(dto.getRating());
        review.setComment(dto.getComment());

        AbdullayevDiasReview updated = reviewRepository.save(review);
        return convertToDto(updated);
    }

    public void deleteReview(Long id) {
        logger.info("Deleting review: {}", id);
        if (!reviewRepository.existsById(id)) {
            throw new AbdullayevDiasResourceNotFoundException("Review not found");
        }
        reviewRepository.deleteById(id);
    }

    private AbdullayevDiasReviewDto convertToDto(AbdullayevDiasReview review) {
        AbdullayevDiasReviewDto dto = new AbdullayevDiasReviewDto();
        dto.setId(review.getId());
        dto.setUserId(review.getUser().getId());
        dto.setUserName(review.getUser().getFullName());
        dto.setCourseId(review.getCourse().getId());
        dto.setCourseTitle(review.getCourse().getTitle());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        return dto;
    }
}
