package com.exam.dias.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbdullayevDiasErrorResponse {
    private String message;
    private int status;
    private LocalDateTime timestamp;
}
