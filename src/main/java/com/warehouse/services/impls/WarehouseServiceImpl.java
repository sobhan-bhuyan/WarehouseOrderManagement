package com.warehouse.services.impls;

import com.warehouse.common.facade.ItemDetailsFacade;
import com.warehouse.common.facade.models.ims.ItemDetailsResponse;
import com.warehouse.common.models.request.CreateOrderRequest;
import com.warehouse.common.models.response.GetOrderResponse;
import com.warehouse.exceptions.DatabaseException;
import com.warehouse.repositories.OrderDetailsRepository;
import com.warehouse.repositories.entities.ItemDetailsEntity;
import com.warehouse.repositories.entities.OrderDetailsEntity;
import com.warehouse.services.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@Transactional(rollbackFor = DatabaseException.class)
public class WarehouseServiceImpl implements WarehouseService {
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private ItemDetailsFacade itemDetailsFacade;

    @Override
    public String createOrder(CreateOrderRequest createOrderRequest) {

        int orderId = orderDetailsRepository.getNextOrderId();
        log.info("Fetched next seq for orderId : {}", orderId);

        //Order details builder pattern
        OrderDetailsEntity orderDetailsEntity = OrderDetailsEntity.builder()
                .orderId(orderId)
                .orderPlacedBy(createOrderRequest.getUsername())
                .warehousePincode(createOrderRequest.getWarehousePincode())
                .build();

        //Item details builder pattern
        List<ItemDetailsEntity> itemDetails = createOrderRequest.getItemDetails().stream()
                .map(item -> ItemDetailsEntity.builder()
                        .itemId(item.getItemId())
                        .itemQuantity(item.getQuantity())
                        .orderId(orderId)
                        .build())
                .collect(toList());

        try {
            orderDetailsRepository.insertOrderDetails(orderDetailsEntity);
            log.info("Inserted order details for orderId : {}", orderId);
            orderDetailsRepository.insertItemDetails(itemDetails);
            log.info("Inserted item details for orderId : {}", orderId);
        } catch (Exception e) {
            log.error("Exception occurred while inserting order details e : {}", ExceptionUtils.getStackTrace(e));
            throw new DatabaseException("Database exception occurred", e);
        }

        return String.valueOf(orderId);
    }

    public GetOrderResponse getOrder(Long orderId) {
        OrderDetailsEntity orderDetails = orderDetailsRepository.getOrderDetailsByOrderId(orderId);

        if (orderDetails == null) {
            throw new RuntimeException("Order details not found for orderId : " + orderId);
        }

        Set<String> itemIds = orderDetails.getItemDetails().stream()
                .map(ItemDetailsEntity::getItemId)
                .collect(Collectors.toSet());

        Map<String, ItemDetailsResponse.ItemLocation> itemLocationMap = itemDetailsFacade.fetchItemDetails(itemIds);

        List<GetOrderResponse.ItemDetails> itemDetails = orderDetails.getItemDetails().stream()
                .map(item -> {
                    ItemDetailsResponse.ItemLocation itemLocation = itemLocationMap.get(item.getItemId());
                    GetOrderResponse.ItemDetails.ItemLocation getItemLocation = new GetOrderResponse.ItemDetails.ItemLocation(
                            itemLocation.getRow(),
                            itemLocation.getColumn(),
                            itemLocation.getHeight()
                    );
                    return new GetOrderResponse.ItemDetails(
                            item.getItemId(),
                            item.getItemQuantity(),
                            getItemLocation
                    );
                })
                .toList();

        return new GetOrderResponse(
                orderDetails.getOrderId(),
                orderDetails.getOrderPlacedBy(),
                orderDetails.getWarehousePincode(),
                itemDetails

        );
    }
}

