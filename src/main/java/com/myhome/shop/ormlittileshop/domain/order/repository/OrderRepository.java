package com.myhome.shop.ormlittileshop.domain.order.repository;

import com.myhome.shop.ormlittileshop.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderCustomRepository {
}
