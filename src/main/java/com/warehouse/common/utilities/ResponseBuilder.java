package com.warehouse.common.utilities;

import com.warehouse.common.dto.response.Response;
import com.warehouse.common.dto.response.ResultInfo;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {
    public static <T> ResponseEntity<Response<T>> buildValidationErrorResponse(ResultInfo resultInfo) {
        final Response<Object> response = Response.builder().resultInfo(resultInfo).build();
        //response.setRequestId();
        final ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        return responseEntity;
    }
}
