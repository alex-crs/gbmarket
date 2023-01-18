package com.gb.market.core.repositories;

import com.gb.market.core.entities.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @NotNull Page<Product> findAll(@NotNull Pageable pageable);
}
