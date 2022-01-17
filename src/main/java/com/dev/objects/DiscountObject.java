package com.dev.objects;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "discount")
public class DiscountObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public int discountId;

    @Column
    private String discount;

    @Column
    private Date discountStart;

    @Column
    private Date discountEnd;

    @Column
    private boolean isGlobal;

    @ManyToMany
    @JoinTable (name = "organization_discount", joinColumns = {@JoinColumn(name="operationId")},
            inverseJoinColumns = {@JoinColumn(name = "organizationId")})
    Set<OrganizationObject> organization = new HashSet<>();

    @Transient
    private boolean relevantFotUser=false;


    public DiscountObject(String discount, Date discountStart, Date discountEnd, boolean isGlobal) {
        this.discount = discount;
        this.discountStart = discountStart;
        this.discountEnd = discountEnd;
        this.isGlobal = isGlobal;
    }

    public DiscountObject() {

    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public boolean isRelevantFotUser() {
        return relevantFotUser;
    }

    public void setRelevantFotUser(boolean relevantFotUser) {
        this.relevantFotUser = relevantFotUser;
    }

    public Date getDiscountStart() {
        return discountStart;
    }

    public void setDiscountStart(Date discountStart) {
        this.discountStart = discountStart;
    }

    public Date getDiscountEnd() {
        return discountEnd;
    }

    public void setDiscountEnd(Date discountEnd) {
        this.discountEnd = discountEnd;
    }

    public boolean isGlobal() {
        return isGlobal;
    }

    public void setGlobal(boolean global) {
        isGlobal = global;
    }

    public Set<OrganizationObject> getOrganization() {
        return organization;
    }

    public void setOrganization(Set<OrganizationObject> organization) {
        this.organization = organization;
    }


}