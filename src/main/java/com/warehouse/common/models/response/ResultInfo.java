package com.warehouse.common.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)

@SuperBuilder
public class ResultInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = -1282272539639224023L;
    private String resultStatus;
    private String resultCode;
    private String resultMsg;


}
