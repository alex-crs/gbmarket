package com.gb.market.market.repositories;

import com.gb.market.market.entities.Order;
import com.gb.market.market.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserName(String userName);
}
