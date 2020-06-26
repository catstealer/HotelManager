package com.example.demo.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
public class ApiError {

   private HttpStatus status;
   private LocalDateTime timestamp;
   private String message;
   private String debugMessage;

    public ApiError(HttpStatus status, LocalDateTime timestamp, String message, String debugMessage) {
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.debugMessage = debugMessage;
    }
}
