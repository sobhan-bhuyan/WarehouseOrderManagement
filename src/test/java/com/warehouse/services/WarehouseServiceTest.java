package com.warehouse.services;

import com.warehouse.common.models.request.CreateOrderRequest;
import com.warehouse.repositories.OrderDetailsRepository;
import com.warehouse.repositories.entities.OrderDetailsEntity;
import com.warehouse.services.impls.WarehouseServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class WarehouseServiceTest {

    @InjectMocks
    private WarehouseServiceImpl warehouseService;

    @Spy
    private OrderDetailsRepository orderDetailsRepository;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createOrderTest() {
        when(orderDetailsRepository.getNextOrderId()).thenReturn(1);
        doNothing().when(orderDetailsRepository).insertOrderDetails(any(OrderDetailsEntity.class));
        doNothing().when(orderDetailsRepository).insertItemDetails(any(List.class));


        CreateOrderRequest createOrderRequest = CreateOrderRequest.builder()
                .username("test")
                .warehousePincode("123456")
                .itemDetails(List.of(CreateOrderRequest.ItemDetails.builder().itemId("test").quantity(1).build()))
                .build();

        String orderId = warehouseService.createOrder(createOrderRequest);

        Assertions.assertEquals("1", orderId);

        verify(orderDetailsRepository, times(1)).getNextOrderId();
        verify(orderDetailsRepository, times(1)).insertOrderDetails(any(OrderDetailsEntity.class));
        verify(orderDetailsRepository, times(1)).insertItemDetails(any(List.class));
    }
}
