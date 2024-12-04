package com.warehouse.controllers;



import com.warehouse.common.dto.request.CreateOrderRequest;
import com.warehouse.common.dto.request.GetOrderRequest;
import com.warehouse.services.WarehouseService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1")
public class OrderManagementController {

    @Autowired
    private WarehouseService warehouseService;


    @PostMapping(
            value = "/orders/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CreateOrderRequest> createOrder(@Valid @RequestBody  CreateOrderRequest createOrderRequest) {
        log.info("Create order request: {}", createOrderRequest);

        warehouseService.createOrder(createOrderRequest);
        return ResponseEntity.ok(createOrderRequest);

    }
    @GetMapping(
            value = "orders/fetch/{orderId}?deviceType={deviceType}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)

    ResponseEntity<GetOrderRequest> getOrder(@Valid @RequestBody GetOrderRequest getOrderRequest) {
        log.info("Get order request: {}", getOrderRequest);
        return ResponseEntity.ok(getOrderRequest);
    }
}
