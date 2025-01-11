package com.warehouse.common.utilities;

import com.warehouse.common.models.response.Response;
import com.warehouse.common.models.response.ResultInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {
    public static <T> ResponseEntity<Response<T>> buildValidationErrorResponse(ResultInfo resultInfo) {
        final Response<Object> response = Response.builder().resultInfo(resultInfo).build();
        //response.setRequestId();
        final ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        return responseEntity;
    }

    public static <T> ResponseEntity<Response<T>> buildInternalServerErrorResponse(ResultInfo resultInfo) {
        final Response<Object> response = Response.builder().resultInfo(resultInfo).build();
        final ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        return responseEntity;
    }

    public static <T> ResponseEntity<Response<T>> buildSuccessResponse(ResultInfo resultInfo, T data) {
        final Response<Object> response = Response.builder().resultInfo(resultInfo).data(data).build();
        final ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(response);
        return responseEntity;
    }

    public static <T> ResponseEntity<Response<T>> buildUnauthorizedResponse(ResultInfo resultInfo) {
        final Response<Object> response = Response.builder().resultInfo(resultInfo).build();
        final ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        return responseEntity;
    }

}
