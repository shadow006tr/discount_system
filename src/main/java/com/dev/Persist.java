package com.dev;


import com.dev.objects.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class Persist {
    private final SessionFactory sessionFactory;

    @Autowired
    public Persist (SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public  List<DiscountObject> getSalesRelevantForUser(String token) {
        List<DiscountObject> discounts = null;
        Session session = sessionFactory.openSession();
        UserObject user=getUserFromDatabaseWithToken(session,token);
        for(OrganizationObject org:user.getOrganizations()){
            for(DiscountObject discount :org.getOperation()){
                discounts.add(discount);
            }
        }
        session.close();
        return discounts;
    }

    public String getTokenByUsernameAndPassword(String username, String password) {
        String token = null;
        Session session = sessionFactory.openSession();
        UserObject userObject = (UserObject) session.createQuery("FROM UserObject WHERE username = :username AND password = :password")
                .setParameter("username", username)
                .setParameter("password", password)
                .uniqueResult();
        session.close();
        if (userObject != null) {
            token = userObject.getToken();
        }
        return token;
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




    public void updateMembership(String token, String name, boolean doMembership) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        UserObject userObject = getUserFromDatabaseWithToken(session,token);
        OrganizationObject organization =getOrganizationFromDatabaseWithName(session,name);
        if (doMembership){
            userObject.addOrganization(organization);
        }else{
            userObject.deleteOrganization(organization);
        }
        session.saveOrUpdate(userObject);
        transaction.commit();
        session.close();
    }

    private OrganizationObject getOrganizationFromDatabaseWithName(Session session, String name) {
        return (OrganizationObject)session.createQuery("FROM OrganizationObject WHERE organizationName=:name").setParameter("name",name).uniqueResult();
    }

    private UserObject getUserFromDatabaseWithToken(Session session, String token) {
        return (UserObject) session.createQuery("FROM UserObject WHERE token = :token")
                .setParameter("token", token)
                .uniqueResult();
    }

//    This function returns the whole sales and check if the discount is relevant for user
    public List<DiscountObject> getAllSales(String token){
        List<DiscountObject> discounts=null;
        Session session = sessionFactory.openSession();
        discounts=(List<DiscountObject>)session.createQuery("FROM DiscountObject ").list();
        UserObject user =getUserFromDatabaseWithToken(session,token);
       for(DiscountObject discount:discounts){
           for(OrganizationObject org :user.getOrganizations()){
                if(discount.getOrganization().contains(org)){
                    discount.setRelevantFotUser(true);
                }
           }
       }
        return discounts;
    }

    public int getCounterLogins(String token) {
        Session session = sessionFactory.openSession();
        UserObject userObject = (UserObject) session.createQuery("FROM UserObject WHERE token = :token")
                .setParameter("token", token)
                .uniqueResult();
        session.close();
        return userObject.getCounterLogins();

    }

    private int getIdOfOrganizationByName(String name){
        Session session = sessionFactory.openSession();
        OrganizationObject org=(OrganizationObject) session.createQuery("FROM OrganizationObject WHERE  organizationName= :name").setParameter("name",name).uniqueResult();
        return org.getOrganizationId();
    }

}
