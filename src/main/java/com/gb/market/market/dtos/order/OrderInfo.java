package com.gb.market.market.dtos.order;

import lombok.Data;

@Data
public class OrderInfo {
    //данный класс нужен для обмена дополнительной информацией о заказе (адрес, дата доставки, телефон, телефон получателя)
    private String address;
    private String phone;
}
