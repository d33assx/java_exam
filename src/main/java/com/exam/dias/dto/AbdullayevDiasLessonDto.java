package com.exam.dias.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbdullayevDiasLessonDto {
    private Long id;
    private String title;
    private String content;
    private Long courseId;
    private Integer orderNumber;
    private String materialFileName;
}
