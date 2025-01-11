package com.warehouse.exceptions.handler;

import com.warehouse.common.models.response.Response;
import com.warehouse.common.models.response.ResultInfo;
import com.warehouse.common.utilities.ResponseBuilder;
import com.warehouse.exceptions.UnauthorisedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.View;

import java.util.stream.Collectors;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private final View error;

    public GlobalExceptionHandler(View error) {
        this.error = error;
    }

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Void>> handle(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return ResponseBuilder.buildInternalServerErrorResponse(
                ResultInfo.builder()
                        .resultCode("E500")
                        .resultMsg("Internal Server Error")
                        .build());
    }

    @ExceptionHandler(UnauthorisedException.class)
    public ResponseEntity<Response<Void>> handle(UnauthorisedException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return ResponseBuilder.buildUnauthorizedResponse(
                ResultInfo.builder()
                        .resultCode("E401")
                        .resultMsg(ex.getMessage())
                        .build());
    }
}