package com.warehouse.controllers;


import com.warehouse.common.models.request.CreateOrderRequest;
import com.warehouse.common.models.response.GetOrderResponse;
import com.warehouse.common.models.response.Response;
import com.warehouse.common.models.response.ResultInfo;
import com.warehouse.common.utilities.ResponseBuilder;
import com.warehouse.services.WarehouseService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1")
public class OrderManagementController {

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private RestTemplate restTemplate;


    @PostMapping(
            value = "/orders/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Response<String>> createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest) {
        log.info("Create order request: {}", createOrderRequest);

        String orderId = warehouseService.createOrder(createOrderRequest);

        return ResponseBuilder.buildSuccessResponse(
                ResultInfo.builder()
                        .resultCode("E200")
                        .resultMsg("Order Created Successfully")
                        .build(), orderId);

    }

    @GetMapping(value = "orders/fetch/{orderId}")

    public ResponseEntity<GetOrderResponse> getOrder(@PathVariable Long orderId, @RequestParam(required = false) String deviceType) {
        log.info("Order ID from URL: {}", orderId);
        log.info("Device Type: {}", deviceType);
        GetOrderResponse response = warehouseService.getOrder(orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/itemDetails")
    public List getItemDescription() {
        String url = "http://demo2086836.mockable.io/ims/get-products";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, List.class);
    }

    @GetMapping(value = "/patchConsume")
    public ResponseEntity<String> patchConsume() {
        String url = "https://sobhan.free.beeceptor.com/ims/patch";

        ResponseEntity<String> response = restTemplate.exchange(RequestEntity.patch(URI.create(url)).build(), String.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

}
