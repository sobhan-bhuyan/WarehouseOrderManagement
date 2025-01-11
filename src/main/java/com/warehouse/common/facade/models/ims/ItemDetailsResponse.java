package com.warehouse.common.facade.models.ims;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class ItemDetailsResponse {
    private String itemId;
    private String itemName;
    private ItemLocation itemLocation;

    @Data
    @NoArgsConstructor
    @SuperBuilder
    public static class ItemLocation {
        private String row;
        private int column;
        private String height;
    }
}
