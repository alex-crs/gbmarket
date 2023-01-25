package com.gb.market.api.dtos;

import lombok.Data;

@Data
public class OrderInfo {
    /*
    Данный класс необходим для обмена дополнительной информацией о заказе
    (адрес, дата доставки, телефон покупателя, телефон получателя и т. д.)
    */
//    private String username;
    private String address;
    private String phone;

    public OrderInfo() {
    }

    public OrderInfo(String username, String address, String phone) {
        this.address = address;
        this.phone = phone;
//        this.username = username;
    }
}
