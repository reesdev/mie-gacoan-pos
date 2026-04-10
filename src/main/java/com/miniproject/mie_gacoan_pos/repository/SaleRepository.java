package com.miniproject.mie_gacoan_pos.repository;

import com.miniproject.mie_gacoan_pos.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}