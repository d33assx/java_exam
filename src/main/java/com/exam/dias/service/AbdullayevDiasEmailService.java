package com.exam.dias.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AbdullayevDiasEmailService {

    private static final Logger logger = LoggerFactory.getLogger(AbdullayevDiasEmailService.class);

    @Async
    public CompletableFuture<String> sendEmail(String to, String subject, String body) {
        logger.info("Sending email to: {} with subject: {}", to, subject);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        logger.info("Email sent successfully to: {}", to);
        return CompletableFuture.completedFuture("Email sent to " + to);
    }
}
