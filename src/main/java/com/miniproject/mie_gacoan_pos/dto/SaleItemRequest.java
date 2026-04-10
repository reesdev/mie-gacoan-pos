package com.miniproject.mie_gacoan_pos.dto;

import lombok.Data;

@Data
public class SaleItemRequest {
    private Long productId;
    private Integer quantity;
}