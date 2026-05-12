package com.exam.dias.dto;

import com.exam.dias.util.AbdullayevDiasRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbdullayevDiasRegisterRequest {
    private String email;
    private String password;
    private String fullName;
    private AbdullayevDiasRole role;
}
