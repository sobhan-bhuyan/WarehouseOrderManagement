package com.warehouse.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ItemDetailsEntity {

    private int id;
    private long orderId;
    private String itemName;
    private int itemQuantity;

}

