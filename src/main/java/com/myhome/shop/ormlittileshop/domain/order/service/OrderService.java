package com.myhome.shop.ormlittileshop.domain.order.service;

import com.myhome.shop.ormlittileshop.domain.delivery.Delivery;
import com.myhome.shop.ormlittileshop.domain.member.Member;
import com.myhome.shop.ormlittileshop.domain.member.repository.MemberRepository;
import com.myhome.shop.ormlittileshop.domain.order.Order;
import com.myhome.shop.ormlittileshop.domain.order.OrderItem;
import com.myhome.shop.ormlittileshop.domain.order.OrderSearch;
import com.myhome.shop.ormlittileshop.domain.order.repository.OrderRepository;
import com.myhome.shop.ormlittileshop.domain.product.Item;
import com.myhome.shop.ormlittileshop.domain.product.NotEnoughStockException;
import com.myhome.shop.ormlittileshop.domain.product.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("orderService")
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    public OrderService(OrderRepository orderRepository, MemberRepository memberRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.memberRepository = memberRepository;
        this.itemRepository = itemRepository;
    }

    public Long order(Long memberId, Long itemId, int count) throws NotEnoughStockException {
        Member member = memberRepository.findById(memberId).get();
        Item item = itemRepository.findById(itemId).get();

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);
        return order.getId();
    }

    public void cancelOrder(Long orderId) throws NotEnoughStockException {
        Order order = orderRepository.findById(orderId).get();
        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        List<Order> orders = orderRepository.findAllOrderBySearchs(orderSearch);
        return orders;
    }

}
