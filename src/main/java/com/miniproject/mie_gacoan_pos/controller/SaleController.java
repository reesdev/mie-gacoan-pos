package com.miniproject.mie_gacoan_pos.controller;

import com.miniproject.mie_gacoan_pos.dto.CreateSaleRequest;
import com.miniproject.mie_gacoan_pos.dto.SaleResponse;
import com.miniproject.mie_gacoan_pos.entity.Sale;
import com.miniproject.mie_gacoan_pos.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    public SaleResponse createSale(@RequestBody CreateSaleRequest request) {
        Sale sale = saleService.createSale(request);
        return saleService.mapToResponse(sale);
    }
}