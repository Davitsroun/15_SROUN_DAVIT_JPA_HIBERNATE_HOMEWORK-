package com.example._15_sroun_davit_jpa_hibernate_homework.entity.respone;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse <T> {
    private boolean success;
    private String message;
    private T payload;
    private HttpStatus status;
    private Instant timestamp;
}
