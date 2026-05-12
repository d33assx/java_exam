package com.exam.dias.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbdullayevDiasCourseDto {
    private Long id;
    private String title;
    private String description;
    private Long teacherId;
    private String teacherName;
    private Long categoryId;
    private String categoryName;
    private Double price;
}
