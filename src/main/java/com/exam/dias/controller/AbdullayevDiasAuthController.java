package com.exam.dias.controller;

import com.exam.dias.dto.AbdullayevDiasAuthResponse;
import com.exam.dias.dto.AbdullayevDiasLoginRequest;
import com.exam.dias.dto.AbdullayevDiasRegisterRequest;
import com.exam.dias.service.AbdullayevDiasAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AbdullayevDiasAuthController {

    private final AbdullayevDiasAuthService authService;

    public AbdullayevDiasAuthController(AbdullayevDiasAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AbdullayevDiasAuthResponse> register(@RequestBody AbdullayevDiasRegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AbdullayevDiasAuthResponse> login(@RequestBody AbdullayevDiasLoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
