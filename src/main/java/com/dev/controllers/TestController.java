package com.dev.controllers;

import com.dev.Persist;
import com.dev.objects.DiscountObject;
import com.dev.objects.UserObject;

import com.dev.utils.Utils;
import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.List;


@RestController
public class TestController {


    @Autowired
    private Persist persist;

    @PostConstruct
    private void init () {

    }

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
            success = persist.addAccount(userObject);
        }

        return success;
    }



    @RequestMapping("membership")
    public void updateMembership(String token ,String organizationName,boolean haveMembership){ persist.updateMembership(token,organizationName,haveMembership);}




    @RequestMapping("is-first-login")
    public boolean cheakFirstLogin(String token){
        return persist.getCounterLogins(token)==1;
    }

    @RequestMapping("is-blocked")
    public boolean cheakUserBlocked(String token){
        return persist.getCounterLogins(token)>=5;
    }

    @RequestMapping("get-relevant-sales")
    public List<DiscountObject> getSalesRelevant(String token ){
        return persist.getSalesRelevantForUser(token);
    }
    @RequestMapping("get-all-sales")
    public List<DiscountObject> getAllSales(String token ){
        return persist.getAllSales(token);
    }

    







}
