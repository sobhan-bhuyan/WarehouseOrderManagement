package com.warehouse.exceptions.handler;

import com.warehouse.common.dto.response.Response;
import com.warehouse.common.dto.response.ResultInfo;
import com.warehouse.common.utilities.ResponseBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Void>> handle(MethodArgumentNotValidException e) {
        String msg = e.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .sorted()
                .collect(Collectors.joining(", "));

        return ResponseBuilder.buildValidationErrorResponse(
                ResultInfo.builder()
                        .resultCode("E001")
                        .resultMsg(msg)
                        .build());
    }
}
