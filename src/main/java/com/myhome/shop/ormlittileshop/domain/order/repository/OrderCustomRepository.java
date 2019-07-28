package com.myhome.shop.ormlittileshop.domain.order.repository;

import com.myhome.shop.ormlittileshop.domain.order.Order;
import com.myhome.shop.ormlittileshop.domain.order.OrderSearch;

import java.util.List;

public interface OrderCustomRepository {

    List<Order> findAllOrderBySearchs(OrderSearch orderSearch);
}
