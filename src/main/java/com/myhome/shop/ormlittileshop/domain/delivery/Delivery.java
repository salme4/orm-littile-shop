package com.myhome.shop.ormlittileshop.domain.delivery;

import com.myhome.shop.ormlittileshop.domain.member.Address;
import com.myhome.shop.ormlittileshop.domain.order.Order;

import javax.persistence.*;

@Entity
@Table(name = "TBL_DELIVERY")
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "DELIVERY_ID")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", order=" + order +
                ", address=" + address +
                ", status=" + status +
                '}';
    }
}
