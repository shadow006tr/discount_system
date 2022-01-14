package com.dev.objects;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "users-creation")
public class UserObject {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Username")
    private String username;

    @Column(name="Password")
    private String password;

    @Column(name = "Token")
    private String token;

    @Column(name = "First-Login")
    private Boolean firstLogin;


    private List<OrginizationsObject> organizations ;

    public UserObject( String username, String password, String token) {
        this.username = username;
        this.password = password;
        this.token = token;
        this.firstLogin =false;
        this.organizations = new ArrayList<>();
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

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(Boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public List<OrginizationsObject> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<OrginizationsObject> organizations) {
        this.organizations = organizations;
    }
}
