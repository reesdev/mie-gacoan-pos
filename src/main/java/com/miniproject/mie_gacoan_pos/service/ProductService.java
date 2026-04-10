package com.miniproject.mie_gacoan_pos.service;

import com.miniproject.mie_gacoan_pos.entity.Product;
import com.miniproject.mie_gacoan_pos.exception.ProductNotFoundException;
import com.miniproject.mie_gacoan_pos.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    @CacheEvict(value = "products", allEntries = true)
    public Product create(Product product) {
        log.info("Creating product: {}", product.getName());
        return productRepository.save(product);
    }

    @Cacheable("products")
    public List<Product> getAll() {
        log.info("Fetching all products");
        return productRepository.findAll();
    }

    public Product getById(Long id) {
        log.info("Fetching product with id: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @CacheEvict(value = "products", allEntries = true)
    public Product update(Long id, Product request) {
        Product product = getById(id);

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        log.info("Updating product id: {}", id);
        return productRepository.save(product);
    }

    @CacheEvict(value = "products", allEntries = true)
    public void delete(Long id) {
        log.info("Deleting product id: {}", id);
        productRepository.deleteById(id);
    }
}