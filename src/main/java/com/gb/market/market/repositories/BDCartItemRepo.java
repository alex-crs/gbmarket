package com.gb.market.market.repositories;

import com.gb.market.market.entities.BDCartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BDCartItemRepo extends JpaRepository<BDCartItem, Long> {
}
