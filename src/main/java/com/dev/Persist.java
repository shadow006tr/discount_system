package com.dev;


import com.dev.objects.*;
import org.aspectj.weaver.ast.Or;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.engine.jdbc.BlobProxy;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.stereotype.Component;


import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;

@Component
public class Persist {
    private final String BLOCKED_USER="-1";
    private final String PASSWORD_INCORRECT="-2";
    private final String USERNAME_AND_PASSWORD_INCORRECT="-3";

    private final SessionFactory sessionFactory;

    @Autowired
    public Persist (SessionFactory sf) {
        this.sessionFactory = sf;
    }



    private Set<OrganizationObject> setOfOrganizationsWithOnlyNamesAndId(DiscountObject discount) {

        Set<OrganizationObject> organizations = new HashSet<OrganizationObject>();
        for(OrganizationObject organization :discount.getOrganization()){
            organizations.add(new OrganizationObject(organization.getOrganizationId(),organization.getOrganizationName()));
        }
        return organizations;
    }

//    This function checks if the username and the password are correct. it returns:
//    -1:Blocked user
//    -2:Password incorrect
//    -3:Username and Password Incorrect
//    if all good the function returns the token of the user
    public String getTokenByUsernameAndPassword(String username, String password) {
        String token = this.USERNAME_AND_PASSWORD_INCORRECT;
        Session session = sessionFactory.openSession();
        UserObject userObject = (UserObject) session.createQuery("FROM UserObject WHERE username = :username AND password = :password")
                .setParameter("username", username)
                .setParameter("password", password)
                .uniqueResult();

        if (userObject != null) {
            if (userObject.getCounterLogins()>=5) token=this.BLOCKED_USER;
            else token = userObject.getToken();
        }
        else{
            UserObject userWithUsername = (UserObject) session.createQuery("FROM UserObject WHERE username = :username").uniqueResult();
            if(userWithUsername!=null){
                token=this.PASSWORD_INCORRECT;
                this.updateCounter(userWithUsername,session);
            }
        }
        session.close();
        return token;
    }

    private void updateCounter(UserObject userObject, Session session) {
        userObject.setCounterLogins(userObject.getCounterLogins()+1);
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(userObject);
        transaction.commit();
    }

    public boolean addAccount (UserObject userObject) {
        boolean success = false;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(userObject);
        transaction.commit();
        session.close();
        if (userObject.getId() > 0) {
            success = true;
        }
        return success;
    }



    public Integer getUserIdByToken (String token) {
        Integer id = null;
        Session session = sessionFactory.openSession();
        UserObject userObject = (UserObject) session.createQuery("FROM UserObject WHERE token = :token")
                .setParameter("token", token)
                .uniqueResult();
        session.close();
        if (userObject != null) {
            id = userObject.getId();
        }
        return id;
    }




    public void updateMembership(String token, int id, boolean doMembership) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        UserObject userObject = getUserFromDatabaseWithToken(session,token);
        OrganizationObject organization =getOrganizationFromDatabaseWithId(session,id);
        if (doMembership){
            userObject.addOrganization(organization);
        }else{
            userObject.deleteOrganization(organization);
        }
        session.saveOrUpdate(userObject);
        transaction.commit();
        session.close();
    }



//    This function returns the whole sales who relevant for user
    public List<DiscountObject> getSalesRelevantForUser(String token){
        List<DiscountObject> user_discounts=new ArrayList<>();
        List <DiscountObject> discounts=this.getAllSales(token);
       for(DiscountObject discount:discounts){
                if (discount.isRelevantFotUser()){
                    user_discounts.add(discount);
                }
           }
        return user_discounts;
    }


    public List<StoreObject> getAllShops() {
        Session session = sessionFactory.openSession();
        List<StoreObject> shops =(List<StoreObject>)session.createCriteria(StoreObject.class).list();
        session.close();
        return shops;
    }

    public boolean cheakFirstLogin(String token) {
        Session session = sessionFactory.openSession();
        UserObject u=getUserFromDatabaseWithToken(session,token);
        return u.getFirstLogin();

    }

    public void setFirstLoginToFalse(String token) {
        Session session = sessionFactory.openSession();
        UserObject user=getUserFromDatabaseWithToken(session,token);
        Transaction transaction = session.beginTransaction();
        user.setFirstLogin(false);
        session.saveOrUpdate(user);
        transaction.commit();
        session.close();
    }

    public List<OrganizationObject> getAllOrganizations() throws SQLException {
        try{
            Session session = sessionFactory.openSession();
            List<OrganizationObject> organizations =(List<OrganizationObject>)session.createCriteria(OrganizationObject.class).list();
            session.close();
            return organizations;
        }
        catch (Exception e){
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());

        }
        return null;
    }



    public List<DiscountObject> getAllSales(String token){
        Session session = sessionFactory.openSession();
        List<DiscountObject> discounts =(List<DiscountObject>)session.createCriteria(DiscountObject.class).list();
        this.setAllSalesRelevantToUser(token,discounts,session);
        session.close();
        return discounts;
    }





   private byte[] getImage(String path) {
        String dir="C:\\Users\\USER\\Pictures\\ProjectDiscountImages\\";
        File file =new File(dir+path+".png");
        if(file.exists()){
            try {
                BufferedImage bufferedImage= ImageIO.read(file);
                ByteArrayOutputStream byteOutStream=new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", byteOutStream);
                return byteOutStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private OrganizationObject getOrganizationFromDatabaseWithId(Session session, int id ){
        return (OrganizationObject)session.createQuery("FROM OrganizationObject WHERE id=:id").setParameter("id",id).uniqueResult();
    }

    private UserObject getUserFromDatabaseWithToken(Session session, String token) {
        return (UserObject) session.createQuery("FROM UserObject WHERE token = :token")
                .setParameter("token", token)
                .uniqueResult();
    }

    private void setAllSalesRelevantToUser(String token,List<DiscountObject> discounts,Session session){
        Set<OrganizationObject> organizations=  this.getUserFromDatabaseWithToken(session,token).getOrganizations();
        for (DiscountObject discount:discounts){
            for(OrganizationObject userOrganization :organizations){
                if (discount.getOrganization().contains(userOrganization)|| discount.isGlobal()){
                    discount.setRelevantFotUser(true);
                }
            }
        }
    }

}
