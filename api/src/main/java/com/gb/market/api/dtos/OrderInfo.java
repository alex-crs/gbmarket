package com.gb.market.api.dtos;


public class OrderInfo {
    /*
    Данный класс необходим для обмена дополнительной информацией о заказе
    (адрес, дата доставки, телефон покупателя, телефон получателя и т. д.)
    */
    private String username;
    private String address;
    private String phone;

    public OrderInfo() {
    }

    public OrderInfo(String username, String address, String phone) {
        this.address = address;
        this.phone = phone;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
