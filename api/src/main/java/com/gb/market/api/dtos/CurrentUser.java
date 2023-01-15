package com.gb.market.api.dtos;

public class CurrentUser {
    private String username;

    public CurrentUser() {
    }

    public CurrentUser(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
