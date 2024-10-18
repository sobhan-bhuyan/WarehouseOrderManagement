package com.warehouse.repositories.entities;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class OrderDetailsEntity {


    private long orderId;

    private String orderPlacedBy;

    private int warehousePincode;

}
