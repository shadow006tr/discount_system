package com.dev.objects;

import javax.persistence.*;
import java.util.List;

@Embeddable
@Entity
@Table(name="store")
public class StoreObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name = "content")
    private String content;



    public StoreObject(String name_of_store) {
        this.name = name_of_store;

    }

    public StoreObject() {

    }

    public String getName_of_store() {
        return name;
    }

    public void setName_of_store(String name_of_store) {
        this.name = name_of_store;
    }


}
