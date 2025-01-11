package com.warehouse.common.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderResponse {
    private Long orderId;
    private String orderPlacedBy;
    private String warehousePincode;
    private List<ItemDetails> itemDetails;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemDetails {
        private String itemId;
        private int quantity;
        private ItemLocation itemLocation;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ItemLocation {
            private String itemRow;
            private int itemColumn;
            private String itemHeight;
        }
    }
}
