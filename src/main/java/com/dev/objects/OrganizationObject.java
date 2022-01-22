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

   @Column
   private String url;

    @JsonIgnore
    @ManyToMany
    @JoinTable (name = "user_organization", joinColumns = {@JoinColumn(name="organizationId")},
            inverseJoinColumns = {@JoinColumn(name = "userId")})
    Set<UserObject> users = new HashSet<>();


    @ManyToMany
    @JoinTable (name = "organization_discount", joinColumns = {@JoinColumn(name="organizationId")},
            inverseJoinColumns = {@JoinColumn(name = "operationId")})
    Set<DiscountObject> operation = new HashSet<>();



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


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}