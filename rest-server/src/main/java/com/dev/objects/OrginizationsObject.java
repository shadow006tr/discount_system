package com.dev.objects;

import java.util.List;

public class OrginizationsObject {

    private String name ;
    private List<DiscountObject> discounts;

    public OrginizationsObject(String name) {
        this.name = name;
    }

    public OrginizationsObject(String name, List<DiscountObject> discounts) {
        this.name = name;
        this.discounts = discounts;
    }

    public List<DiscountObject> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<DiscountObject> discounts) {
        this.discounts = discounts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   
}
