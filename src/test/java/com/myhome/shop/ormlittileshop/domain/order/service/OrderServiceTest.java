package com.myhome.shop.ormlittileshop.domain.order.service;

import com.myhome.shop.ormlittileshop.domain.member.Address;
import com.myhome.shop.ormlittileshop.domain.member.Member;
import com.myhome.shop.ormlittileshop.domain.member.repository.MemberRepository;
import com.myhome.shop.ormlittileshop.domain.order.Order;
import com.myhome.shop.ormlittileshop.domain.order.OrderStatus;
import com.myhome.shop.ormlittileshop.domain.order.repository.OrderRepository;
import com.myhome.shop.ormlittileshop.domain.product.Book;
import com.myhome.shop.ormlittileshop.domain.product.Item;
import com.myhome.shop.ormlittileshop.domain.product.NotEnoughStockException;
import com.myhome.shop.ormlittileshop.domain.product.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;


    @Test
    public void testOrder() throws NotEnoughStockException {
        //given
        Member member = createMember();
        Item item = createBook("Jpa 표준", 10000, 10); //이름, 가격, 재고
        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //then
        Order findOrder = orderRepository.findById(orderId).get();

        //주문 시 상태확인
        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.ORDER);

        //상품 종류 수 확인
        assertThat(findOrder.getOrderItems().size()).isEqualTo(1);

        //주문가격은 가격 * 수량
        assertThat(findOrder.getTotalPrice()).isEqualTo(10000 * 2);

        //주문 수량 재고 확인
        assertThat(item.getStockQuantity()).isEqualTo(8);

    }

    @Test(expected = NotEnoughStockException.class)
    public void testOverStockQuantity () throws NotEnoughStockException {
        //given
        Member member = createMember();
        Item item = createBook("Jpa 표준", 10000, 10); //이름, 가격, 재고
        int orderCount = 11;

        //when
        orderService.order(member.getId(), item.getId(), orderCount);

        //then
        fail("재고 수량 부족으로 예외 발생해야 한다.");
    }

    @Test
    public void testCancelOrder() throws NotEnoughStockException {
        //given
        Member member = createMember();
        Item item = createBook("Jpa 표준", 10000, 10); //이름, 가격, 재고
        int orderCount = 3;

        //when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        orderService.cancelOrder(orderId);

        //then
        Order foundOrder = orderRepository.findById(orderId).get();

        //주문상태는 취소
        assertThat(foundOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);

        //재고는 다시 10개
        assertThat(item.getStockQuantity()).isEqualTo(10);
    }

    private Item createBook(String name, long price, int quantity) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(quantity);
        book.setPrice(price);
        itemRepository.save(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setUserName("dave");
        member.setAddress(new Address("인천", "남동구", "123-123"));
        memberRepository.save(member);
        return member;
    }
}