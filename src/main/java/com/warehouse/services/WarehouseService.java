package com.warehouse.services;

import com.warehouse.common.models.request.CreateOrderRequest;
import com.warehouse.common.models.response.GetOrderResponse;

public interface WarehouseService {

    String createOrder(CreateOrderRequest createOrderRequest);

    GetOrderResponse getOrder(Long getOrderRequest);
}
