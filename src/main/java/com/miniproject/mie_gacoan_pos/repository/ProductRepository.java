package com.miniproject.mie_gacoan_pos.repository;

import com.miniproject.mie_gacoan_pos.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}