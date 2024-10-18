package com.warehouse.services;

import com.warehouse.common.dto.CreateOrderRequest;
import com.warehouse.repositories.OrderDetailsRepository;

public interface WarehouseService {

    void createOrder(CreateOrderRequest createOrderRequest);

}
