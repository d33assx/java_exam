package com.exam.dias.controller;

import com.exam.dias.dto.AbdullayevDiasReviewDto;
import com.exam.dias.service.AbdullayevDiasReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class AbdullayevDiasReviewController {

    private final AbdullayevDiasReviewService reviewService;

    public AbdullayevDiasReviewController(AbdullayevDiasReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<AbdullayevDiasReviewDto>> getReviewsByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(reviewService.getReviewsByCourse(courseId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AbdullayevDiasReviewDto>> getReviewsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbdullayevDiasReviewDto> getReviewById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @PostMapping
    public ResponseEntity<AbdullayevDiasReviewDto> createReview(@RequestBody AbdullayevDiasReviewDto dto) {
        return ResponseEntity.ok(reviewService.createReview(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AbdullayevDiasReviewDto> updateReview(@PathVariable Long id,
                                                                 @RequestBody AbdullayevDiasReviewDto dto) {
        return ResponseEntity.ok(reviewService.updateReview(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
