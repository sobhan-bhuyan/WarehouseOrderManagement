package com.warehouse.common.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetItemDetails {
    private String itemId;
    private String itemName;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemLocation {
        private String itemRow;
        private int itemColumn;
        private String itemHeight;
    }


}