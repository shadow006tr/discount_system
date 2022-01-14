package com.dev.objects;


import java.util.Date;

public class DiscountObject {

    private StoreObject store;
    private String content ;
    private Date start_date;
    private Date end_time;
    private boolean isGlobal;

    public DiscountObject(StoreObject store, String content, Date start, Date end, boolean isGlobal) {
        this.store = store;
        this.content = content;
        this.start_date = start;
        this.end_time = end;
        this.isGlobal = isGlobal;
    }

    public StoreObject getStore() {
        return store;
    }

    public void setStore(StoreObject store) {
        this.store = store;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStart() {
        return start_date;
    }

    public void setStart(Date start) {
        this.start_date = start;
    }

    public Date getEnd() {
        return end_time;
    }

    public void setEnd(Date end) {
        this.end_time = end;
    }

    public boolean isGlobal() {
        return isGlobal;
    }

    public void setGlobal(boolean global) {
        isGlobal = global;
    }
}
