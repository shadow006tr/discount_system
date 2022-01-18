package com.dev.objects;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public int id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String token;

    @ColumnDefault("0")
    @Column(name="counter" )
    private int counterLogins ;



    @ManyToMany
    @JoinTable (name = "user_organization", joinColumns = {@JoinColumn(name="UserId")},
            inverseJoinColumns = {@JoinColumn(name = "organizationId")})
    Set<OrganizationObject> organizations = new HashSet<>();

    public UserObject() {
        counterLogins=0;
    }

    public UserObject(String username, String password, String token) {
        this.username = username;
        this.password = password;
        this.token = token;
        counterLogins=0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }


    public int getCounterLogins() {
        return counterLogins;
    }

    public void setCounterLogins(int counterLogins) {
        this.counterLogins = counterLogins;
    }

    public Set<OrganizationObject> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(Set<OrganizationObject> organizations) {
        this.organizations = organizations;
    }

    public void addOrganization (OrganizationObject o){
        this.organizations.add(o);
    }

    public void deleteOrganization(OrganizationObject organization) {
        this.organizations.remove(organization);
    }
}