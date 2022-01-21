package com.dev.utils;

import com.dev.objects.DiscountObject;
import org.hibernate.Session;
import org.springframework.web.socket.WebSocketSession;


import java.util.List;

public class DiscountAndSession {
    private List<DiscountObject> discounts;
    private WebSocketSession session;


    public DiscountAndSession(List<DiscountObject> discounts,WebSocketSession session) {
        this.discounts = discounts;
        this.session=session;
    }

    public List<DiscountObject> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<DiscountObject> discounts) {
        this.discounts = discounts;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public void setSession(WebSocketSession session) {
        this.session = session;
    }
}
