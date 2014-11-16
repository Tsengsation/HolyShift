package com.jtna.holyshift.backend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nishad Agrawal on 11/15/14.
 */
public class User {
    private String fullName;
    private String userName;
    private String password;
    private List<String> myGroups;
    private Availability myAvail;

    public User(String fullName, String userName, String password) {
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
        myGroups = new ArrayList<String>();
        myAvail = new Availability();
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
        return myGroups;
    }

    public void setMyGroups(List<String> myGroups) {
        this.myGroups = myGroups;
    }

    public void addNewGroup(Group g) {
        myGroups.add(g.getGroupName());
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
