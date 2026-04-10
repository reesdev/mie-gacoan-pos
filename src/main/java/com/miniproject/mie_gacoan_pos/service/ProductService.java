package com.miniproject.mie_gacoan_pos.service;

import com.miniproject.mie_gacoan_pos.entity.Product;
import com.miniproject.mie_gacoan_pos.exception.ProductNotFoundException;
import com.miniproject.mie_gacoan_pos.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @CacheEvict(value = "products", allEntries = true)
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Cacheable("products")
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @CacheEvict(value = "products", allEntries = true)
    public Product update(Long id, Product request) {
        Product product = getById(id);

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        return productRepository.save(product);
    }

    @CacheEvict(value = "products", allEntries = true)
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}