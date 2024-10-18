package com.warehouse.services.impls;

import com.warehouse.common.dto.CreateOrderRequest;
import com.warehouse.common.dto.response.Response;
import com.warehouse.exceptions.DatabaseException;
import com.warehouse.repositories.entities.ItemDetailsEntity;
import com.warehouse.repositories.entities.OrderDetailsEntity;
import com.warehouse.services.WarehouseService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.warehouse.repositories.OrderDetailsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = DatabaseException.class)
public class WarehouseServiceImpl implements WarehouseService {
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Override
    public void createOrder(CreateOrderRequest createOrderRequest) {

        int orderId = orderDetailsRepository.getNextOrderId();
        log.info("Fetched next seq for orderId : {}", orderId);

        //Order details builder pattern
        OrderDetailsEntity orderDetailsEntity = OrderDetailsEntity.builder()
                .orderId(orderId)
                .orderPlacedBy(createOrderRequest.getUsername())
                .warehousePincode(Integer.parseInt(createOrderRequest.getWarehousePincode()))
                .build();

        //Item details builder pattern
        List<ItemDetailsEntity> itemDetails = createOrderRequest.getItemDetails().stream()
                .map(item -> ItemDetailsEntity.builder()
                        .itemName(item.getItemName())
                        .itemQuantity(item.getQuantity())
                        .orderId(orderId)
                        .build())
                        .collect(Collectors.toList());

        try {
            orderDetailsRepository.insertOrderDetails(orderDetailsEntity);
            orderDetailsRepository.insertItemDetails(itemDetails);
        }
        catch(Exception e) {
            log.error("Exception occurred while inserting order details e : {}", ExceptionUtils.getStackTrace(e));
            throw new DatabaseException("Database exception occurred",e);
        }
    }
}
