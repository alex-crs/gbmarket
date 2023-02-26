package com.gb.market.market.logservice.handlers;

public abstract class AbstractHandler {
    private AbstractHandler next;
    public void handle(String channel, String message) {
        if(next != null)
            next.handle(channel, message);
    }
    public AbstractHandler link(AbstractHandler next) {
        this.next = next;
        return next;
    }
}
