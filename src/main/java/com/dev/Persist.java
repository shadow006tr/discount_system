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
    static final String BLOCKED_USER="-1";
    private final SessionFactory sessionFactory;

    @Autowired
    public Persist (SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public  List<DiscountObject> getSalesRelevantForUser(String token) {
        List<DiscountObject> discounts = new ArrayList<DiscountObject>();
        Session session = sessionFactory.openSession();
        UserObject user=getUserFromDatabaseWithToken(session,token);

        if (user!=null) {
            for (OrganizationObject org : user.getOrganizations()) {
                for (DiscountObject discount : org.getOperation()) {
                    discount.setOrganization(this.setOfOrganizationsWithOnlyNamesAndId(discount));
                    discounts.add(discount);
                }
            }
        }
        session.close();
        return discounts;
    }

    private Set<OrganizationObject> setOfOrganizationsWithOnlyNamesAndId(DiscountObject discount) {

        Set<OrganizationObject> organizations = new HashSet<OrganizationObject>();
        for(OrganizationObject organization :discount.getOrganization()){
            organizations.add(new OrganizationObject(organization.getOrganizationId(),organization.getOrganizationName()));
        }
        return organizations;
    }

    public String getTokenByUsernameAndPassword(String username, String password) {
        String token = null;
        Session session = sessionFactory.openSession();
        UserObject userObject = (UserObject) session.createQuery("FROM UserObject WHERE username = :username AND password = :password")
                .setParameter("username", username)
                .setParameter("password", password)
                .uniqueResult();

        if (userObject != null) {
            if (userObject.getCounterLogins()>=5) token=BLOCKED_USER;
            else token = userObject.getToken();
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
            for (OrganizationObject organization :organizations){
               this.setBlobToString(organization);
            }
            session.close();
            return organizations;
        }
        catch (Exception e){
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
            System.err.println(e.getCause());
        }
        return null;
    }

    private void setBlobToString(OrganizationObject organization) throws SQLException, UnsupportedEncodingException {
        Blob image=organization.getImage();
        byte[] bdata = image.getBytes(1, (int) image.length());
        String data = new String(bdata);
        organization.setStringImage( data);
    }



    public void saveAnImage(int id,String nameOfPicture){
        Session session = sessionFactory.openSession();
        Transaction  transaction = session.beginTransaction();

        OrganizationObject organization=this.getOrganizationFromDatabaseWithId(session,id);
        session.doWork(connection -> {organization.setImage(BlobProxy.generateProxy(getImage(nameOfPicture)));});
        session.saveOrUpdate(organization);
        transaction.commit();
        System.out.println("Image is saved successfully.");

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
}
