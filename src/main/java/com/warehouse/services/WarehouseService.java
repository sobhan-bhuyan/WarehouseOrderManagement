package com.warehouse.services;

import com.warehouse.common.dto.request.CreateOrderRequest;

public interface WarehouseService {

    void createOrder(CreateOrderRequest createOrderRequest);

}
