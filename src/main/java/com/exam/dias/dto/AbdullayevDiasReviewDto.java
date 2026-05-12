package com.exam.dias.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbdullayevDiasReviewDto {
    private Long id;
    private Long userId;
    private String userName;
    private Long courseId;
    private String courseTitle;
    private Integer rating;
    private String comment;
}
