package com.dev.objects;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table (name = "shops")
public class ShopObject {
    @Id
    @Column
    private String name_of_store;

//    @Column
    private Date date;
//    @OneToMany
//    @JoinColumn(name = "discount_id")
//    private DiscountObject discount;


}
