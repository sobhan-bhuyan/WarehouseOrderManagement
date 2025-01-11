package com.warehouse.common.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -2269873755248391250L;

    private String requestId;
    private ResultInfo resultInfo;
    private T data;
    
}
