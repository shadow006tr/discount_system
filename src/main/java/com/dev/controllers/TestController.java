package com.dev.controllers;

import com.dev.Persist;
import com.dev.objects.DiscountObject;
import com.dev.objects.OrganizationObject;
import com.dev.objects.StoreObject;
import com.dev.objects.UserObject;

import com.dev.utils.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

import java.sql.SQLException;

import java.util.*;


@RestController
public class TestController {


    @Autowired
    private Persist persist;

    @PostConstruct
    private void init () {

    }
// If the user blocked the token will be -1
    @RequestMapping("sign-in")
    public String signIn (String username, String password) {
        return persist.getTokenByUsernameAndPassword(username, password);
    }

    @RequestMapping("create-account")
    public boolean createAccount (String username, String password) {
        boolean success = false;
        boolean alreadyExists = persist.getTokenByUsernameAndPassword(username, password) != null;
        if (!alreadyExists) {
            String hash = Utils.createHash(username, password);
            UserObject userObject = new UserObject(username,password,hash);
            userObject.setCounterLogins(0);
            userObject.setFirstLogin(true);
            success = persist.addAccount(userObject);
        }

        return success;
    }


    @RequestMapping("membership")
    public void updateMembership(String token ,int organizationId,boolean haveMembership){ persist.updateMembership(token,organizationId,haveMembership);}


    @RequestMapping("finish-settings")
    public void setFirstLoginToFalse(String token){persist.setFirstLoginToFalse(token);}

    @RequestMapping("is-first-login")
    public boolean cheakFirstLogin(String token){
        return persist.cheakFirstLogin(token);
    }



    @RequestMapping("get-all-sales")
    public List<DiscountObject> getAllSales(String token){
        return persist.getAllSales(token);
    }

    @RequestMapping("get-all-shops")
    public List<StoreObject> getAllShops(){
       return persist.getAllShops();
    }

    @RequestMapping("get-all-organizations")
    public List<OrganizationObject> getAllOrganizations(String token) throws SQLException {
        return persist.getAllOrganizations(token);}

    @RequestMapping("sales-for-dashboard")
    public List<DiscountObject> salesRelevantForUser(String token){
        return persist.getSalesRelevantForUser(token);
    }


    @RequestMapping("get-organizations-of-user")
    public Set<OrganizationObject> getOrganizationsOfUser(String token){
        return persist.getOrganizationsOfUser(token);
    }


    @RequestMapping("check-connection-to-organization")
    public boolean cheakUserConnectToOrganization(String token,int idOrganization){
        return persist.checkUserConnectToOrganization(token,idOrganization);
    }

    @RequestMapping("search-discounts")
    public Set<DiscountObject> searchDiscounts(String token,String query){
        List<DiscountObject> discounts=persist.getAllSales(token);
        return persist.searchDiscounts(query,discounts);
    }
    }



