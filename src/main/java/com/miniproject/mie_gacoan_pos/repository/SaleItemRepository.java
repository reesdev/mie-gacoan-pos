package com.miniproject.mie_gacoan_pos.repository;

import com.miniproject.mie_gacoan_pos.entity.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {
}