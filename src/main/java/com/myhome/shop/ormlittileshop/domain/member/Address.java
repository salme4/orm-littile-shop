package com.myhome.shop.ormlittileshop.domain.member;


import javax.persistence.Embeddable;

@Embeddable
public class Address {

    private String city;
    private String street;
    private String zipcode;


}
