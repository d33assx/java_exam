package com.exam.dias.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbdullayevDiasEnrollmentDto {
    private Long id;
    private Long userId;
    private String userEmail;
    private Long courseId;
    private String courseTitle;
    private Integer progress;
}
