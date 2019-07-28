package com.myhome.shop.ormlittileshop.domain.order;

public class OrderSearch {

    private String memberName;
    private OrderStatus status;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
