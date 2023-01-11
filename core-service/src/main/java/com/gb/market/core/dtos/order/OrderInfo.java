package com.gb.market.core.dtos.order;

import lombok.Data;

@Data
public class OrderInfo {
    /*
    Данный класс необходим для обмена дополнительной информацией о заказе
    (адрес, дата доставки, телефон покупателя, телефон получателя и т. д.)
    */
    private String address;
    private String phone;
}
