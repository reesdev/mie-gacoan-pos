package com.miniproject.mie_gacoan_pos.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaleItemResponse {
    private Long productId;
    private Integer quantity;
    private Double price;
}