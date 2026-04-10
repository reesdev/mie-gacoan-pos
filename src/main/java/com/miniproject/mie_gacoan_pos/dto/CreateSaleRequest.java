package com.miniproject.mie_gacoan_pos.dto;

import lombok.Data;
import java.util.List;

@Data
public class CreateSaleRequest {
    private List<SaleItemRequest> items;
}