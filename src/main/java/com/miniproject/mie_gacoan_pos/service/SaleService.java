package com.miniproject.mie_gacoan_pos.service;

import com.miniproject.mie_gacoan_pos.dto.CreateSaleRequest;
import com.miniproject.mie_gacoan_pos.dto.SaleItemRequest;
import com.miniproject.mie_gacoan_pos.entity.Product;
import com.miniproject.mie_gacoan_pos.entity.Sale;
import com.miniproject.mie_gacoan_pos.entity.SaleItem;
import com.miniproject.mie_gacoan_pos.repository.ProductRepository;
import com.miniproject.mie_gacoan_pos.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;

    @Transactional
    public Sale createSale(CreateSaleRequest request) {

        List<SaleItem> saleItems = new ArrayList<>();
        double totalAmount = 0;

        for (SaleItemRequest itemRequest : request.getItems()) {

            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStock() < itemRequest.getQuantity()) {
                throw new RuntimeException("Stock not enough");
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

        return saleRepository.save(sale);
    }
}