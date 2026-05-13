package com.exam.dias.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AbdullayevDiasAsyncLogService {

    private static final Logger logger = LoggerFactory.getLogger(AbdullayevDiasAsyncLogService.class);

    @Async
    public void logAction(String username, String action) {
        logger.info("User: {} performed action: {}", username, action);
    }
}
