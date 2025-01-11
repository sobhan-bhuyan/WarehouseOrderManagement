package com.warehouse.repositories.entities;

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

    private String warehousePincode;

    private List<ItemDetailsEntity> itemDetails;

}
