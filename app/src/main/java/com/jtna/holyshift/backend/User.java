package com.jtna.holyshift.backend;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nishad Agrawal on 11/15/14.
 */
@ParseClassName("User")
public class User extends ParseObject {
    private String fullName;
    private String userName;
    private String password;
    private List<String> myGroups;
    private Availability myAvail;

    public User() {
        super();
    }

    public User(String fullName, String userName, String password) {
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
        myGroups = new ArrayList<String>();
        myAvail = new Availability();
    }

    public User(String fullName, String userName, String password, List<String> myGroups, Availability myAvail) {
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
        this.myGroups = myGroups;
        this.myAvail = myAvail;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getMyGroups() {
        return (List<String>) get("myGroups");
    }

    public void setMyGroups(List<String> myGroups) {
        put("myGroups", myGroups);
    }

    public void addNewGroup(Group g) {
        add("myGroups", g);
    }

    public void leaveGroup(Group g) {
        myGroups.remove(g);
    }

    public Availability getMyAvail() {
        return myAvail;
    }

    public void setMyAvail(Availability myAvail) {
        this.myAvail = myAvail;
    }

}
