package com.myhome.shop.ormlittileshop.domain.order;


import com.myhome.shop.ormlittileshop.domain.product.Item;
import com.myhome.shop.ormlittileshop.domain.product.NotEnoughStockException;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_ITEM")
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    private Long orderPrice;

    private int count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Long getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Long orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    //생성 메서드
    public static OrderItem createOrderItem(Item item, Long orderPrice, int count) throws NotEnoughStockException {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    //주문 취소
    public void cancel() {
        getItem().addStock(count);
    }

    //가격 조회
    public int getTotalPrice() {
        return (int) (getOrderPrice() * getCount());
    }


    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", orderPrice=" + orderPrice +
                ", count=" + count +
                '}';
    }
}
