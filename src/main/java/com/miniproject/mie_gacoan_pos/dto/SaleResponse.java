package com.miniproject.mie_gacoan_pos.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class SaleResponse {
    private Long id;
    private LocalDateTime orderDate;
    private Double totalAmount;
    private List<SaleItemResponse> items;
}