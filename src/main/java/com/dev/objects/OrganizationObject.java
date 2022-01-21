package com.dev.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.sql.Blob;

@Entity
@Table(name = "organizations")
public class OrganizationObject {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public int organizationId;

    @Column
    private String organizationName;


    public OrganizationObject(int id,String name){
        this.organizationId=id;
        this.organizationName=name;
    }

    @JsonIgnore
    @Column(name = "IMAGE")
    private Blob image;

    @JsonIgnore
    @ManyToMany
    @JoinTable (name = "user_organization", joinColumns = {@JoinColumn(name="organizationId")},
            inverseJoinColumns = {@JoinColumn(name = "userId")})
    Set<UserObject> users = new HashSet<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable (name = "organization_discount", joinColumns = {@JoinColumn(name="organizationId")},
            inverseJoinColumns = {@JoinColumn(name = "operationId")})
    Set<DiscountObject> operation = new HashSet<>();

    @Transient
    private String stringImage;

    public OrganizationObject() {

    }

    public Set<UserObject> getUsers() {
        return users;
    }

    public void setUsers(Set<UserObject> users) {
        this.users = users;
    }

    public Set<DiscountObject> getOperation() {
        return operation;
    }

    public void setOperation(Set<DiscountObject> operation) {
        this.operation = operation;
    }

    public int getOrganizationId() {return organizationId; }

    public void setOrganizationId(int organizationId) { this.organizationId = organizationId; }

    public String getOrganizationName() { return organizationName; }

    public void setOrganizationName(String organizationName) { this.organizationName = organizationName; }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public String getStringImage() {
        return stringImage;
    }

    public void setStringImage(String stringImage) {
        this.stringImage = stringImage;
    }
}