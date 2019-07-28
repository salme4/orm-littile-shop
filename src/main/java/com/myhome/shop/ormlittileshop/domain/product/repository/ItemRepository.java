package com.myhome.shop.ormlittileshop.domain.product.repository;

import com.myhome.shop.ormlittileshop.domain.product.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
