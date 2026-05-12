package com.exam.dias.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbdullayevDiasUserDto {
    private Long id;
    private String email;
    private String fullName;
    private String role;
}
