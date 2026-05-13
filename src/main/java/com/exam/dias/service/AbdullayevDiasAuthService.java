package com.exam.dias.service;

import com.exam.dias.dto.AbdullayevDiasAuthResponse;
import com.exam.dias.dto.AbdullayevDiasLoginRequest;
import com.exam.dias.dto.AbdullayevDiasRegisterRequest;
import com.exam.dias.entity.AbdullayevDiasUser;
import com.exam.dias.exception.AbdullayevDiasUnauthorizedException;
import com.exam.dias.repository.AbdullayevDiasUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AbdullayevDiasAuthService {

    private static final Logger logger = LoggerFactory.getLogger(AbdullayevDiasAuthService.class);

    private final AbdullayevDiasUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AbdullayevDiasJwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AbdullayevDiasAsyncLogService asyncLogService;
    private final AbdullayevDiasEmailService emailService;

    public AbdullayevDiasAuthService(AbdullayevDiasUserRepository userRepository,
                                     PasswordEncoder passwordEncoder,
                                     AbdullayevDiasJwtService jwtService,
                                     AuthenticationManager authenticationManager,
                                     AbdullayevDiasAsyncLogService asyncLogService,
                                     AbdullayevDiasEmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.asyncLogService = asyncLogService;
        this.emailService = emailService;
    }

    public AbdullayevDiasAuthResponse register(AbdullayevDiasRegisterRequest request) {
        logger.info("Registering new user: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AbdullayevDiasUnauthorizedException("Email already exists");
        }

        AbdullayevDiasUser user = new AbdullayevDiasUser();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setRole(request.getRole());

        userRepository.save(user);

        asyncLogService.logAction(user.getEmail(), "USER_REGISTERED");
        emailService.sendEmail(user.getEmail(), "Welcome", "Thank you for registering!");

        String token = jwtService.generateToken(user);

        return new AbdullayevDiasAuthResponse(token, user.getEmail(), user.getFullName(), user.getRole().name());
    }

    public AbdullayevDiasAuthResponse login(AbdullayevDiasLoginRequest request) {
        logger.info("User login attempt: {}", request.getEmail());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        AbdullayevDiasUser user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AbdullayevDiasUnauthorizedException("Invalid credentials"));

        asyncLogService.logAction(user.getEmail(), "USER_LOGGED_IN");

        String token = jwtService.generateToken(user);

        return new AbdullayevDiasAuthResponse(token, user.getEmail(), user.getFullName(), user.getRole().name());
    }
}
