package com.miniproject.mie_gacoan_pos.service;

import com.miniproject.mie_gacoan_pos.dto.CreateSaleRequest;
import com.miniproject.mie_gacoan_pos.dto.SaleItemRequest;
import com.miniproject.mie_gacoan_pos.dto.SaleItemResponse;
import com.miniproject.mie_gacoan_pos.dto.SaleResponse;
import com.miniproject.mie_gacoan_pos.entity.Product;
import com.miniproject.mie_gacoan_pos.entity.Sale;
import com.miniproject.mie_gacoan_pos.entity.SaleItem;
import com.miniproject.mie_gacoan_pos.exception.InsufficientStockException;
import com.miniproject.mie_gacoan_pos.exception.ProductNotFoundException;
import com.miniproject.mie_gacoan_pos.repository.ProductRepository;
import com.miniproject.mie_gacoan_pos.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private static final Logger log = LoggerFactory.getLogger(SaleService.class);

    @CacheEvict(value = "products", allEntries = true)
    @Transactional
    public Sale createSale(CreateSaleRequest request) {
        log.info("Starting sales transaction");
        List<SaleItem> saleItems = new ArrayList<>();
        double totalAmount = 0;

        for (SaleItemRequest itemRequest : request.getItems()) {
            log.info("Processing product id: {}", itemRequest.getProductId());
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));

            if (product.getStock() < itemRequest.getQuantity()) {
                log.warn("Stock not enough for product id: {}", product.getId());
                throw new InsufficientStockException("Stock not enough");
            }

            product.setStock(product.getStock() - itemRequest.getQuantity());

            double price = product.getPrice();
            double subTotal = price * itemRequest.getQuantity();

            totalAmount += subTotal;

            SaleItem saleItem = SaleItem.builder()
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .price(price)
                    .build();

            saleItems.add(saleItem);
        }

        Sale sale = Sale.builder()
                .totalAmount(totalAmount)
                .items(saleItems)
                .build();

        // set relation
        for (SaleItem item : saleItems) {
            item.setSale(sale);
        }
        log.info("Transaction success with total amount: {}", totalAmount);
        return saleRepository.save(sale);
    }
    public SaleResponse mapToResponse(Sale sale) {
        return SaleResponse.builder()
                .id(sale.getId())
                .orderDate(sale.getOrderDate())
                .totalAmount(sale.getTotalAmount())
                .items(sale.getItems().stream().map(item ->
                        SaleItemResponse.builder()
                                .productId(item.getProduct().getId())
                                .quantity(item.getQuantity())
                                .price(item.getPrice())
                                .build()
                ).toList())
                .build();
    }
}