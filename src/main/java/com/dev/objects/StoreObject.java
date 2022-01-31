package com.dev.objects;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Embeddable
@Entity
@Table(name="store")
public class StoreObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name" ,unique = true,nullable = false)
    private String name;

    @Column(name = "content")
    private String content;


    @OneToMany
    @JoinColumn(name = "store_discount")
    private Set<DiscountObject> discount = new HashSet<DiscountObject>();


    public StoreObject(StoreObject store){
            this.discount=store.discount;
            this.content=store.content;
            this.id=store.id;
            this.name=store.name;
    }


    public StoreObject() {

    }

    public String getName_of_store() {
        return name;
    }

    public void setName_of_store(String name_of_store) {
        this.name = name_of_store;
    }

    public int getId() {
        return id;
    }





    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<DiscountObject> getDiscount() {
        return discount;
    }

    public void setDiscount(Set<DiscountObject> discount) {
        this.discount = discount;
    }
}
