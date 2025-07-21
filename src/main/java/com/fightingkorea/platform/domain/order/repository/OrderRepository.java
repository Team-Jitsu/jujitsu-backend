package com.fightingkorea.platform.domain.order.repository;

import com.fightingkorea.platform.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByTossOrderId(String tossOrderId);
}
