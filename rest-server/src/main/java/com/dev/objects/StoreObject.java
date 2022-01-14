package com.dev.objects;

import java.util.List;

public class StoreObject {
    private String name_of_store;
    private List<DiscountObject> discountObjects;

    public StoreObject(String name_of_store) {
        this.name_of_store = name_of_store;
    }

    public StoreObject(String name_of_store, List<DiscountObject> discountObjects) {
        this.name_of_store = name_of_store;
        this.discountObjects = discountObjects;
    }

    public String getName_of_store() {
        return name_of_store;
    }

    public void setName_of_store(String name_of_store) {
        this.name_of_store = name_of_store;
    }

    public List<DiscountObject> getDiscountObjects() {
        return discountObjects;
    }

    public void setDiscountObjects(List<DiscountObject> discountObjects) {
        this.discountObjects = discountObjects;
    }
}
