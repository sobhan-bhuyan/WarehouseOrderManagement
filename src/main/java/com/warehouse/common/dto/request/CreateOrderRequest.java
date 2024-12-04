package com.warehouse.common.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
    public class CreateOrderRequest {

    @NotBlank(message = "Username cannot be blank")
    private String username;

    @NotBlank(message = "Warehouse pin code cannot be blank")
    @Pattern(regexp = "(^[1-9]{1}[0-9]{2}\s{0,1}[0-9]{3}$)", message = "Invalid pin code")
    private String warehousePincode;

    @NotEmpty(message = "Enter at least 1 item")
    @Size(max = 10, message = "Maximum 10 items can be ordered")
    @Valid
    private List<ItemDetails> itemDetails;
    @Data
    public static class ItemDetails{
        @NotBlank(message = "Product/Item name cannot be blank")
        private String itemName;

        @Min(value = 1, message = "Quantity cannot be blank")
        private int quantity;
    }

}
