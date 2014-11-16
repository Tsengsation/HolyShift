package com.jtna.holyshift.backend;

import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Nishad Agrawal on 11/16/14.
 */
public class FireBaseUtility {
    private Firebase rootRef;
    private Firebase usersRef;
    private Firebase groupsRef;

    private List<User> users;
    private List<Group> groups;
    private User currentUser;

    public FireBaseUtility() {
        rootRef = new Firebase("https://holyshift.firebaseio.com/");
        usersRef.child("users");
        groupsRef.child("groups");
        users = new ArrayList<User>();
        groups = new ArrayList<Group>();

        groupsRef.addChildEventListener(getGroupListener());
        usersRef.addChildEventListener(getUserListener());
    }

    public boolean register(String name, String userName, String password) {
        for (User existing: users) {
            if (existing.getUserName().equals(userName)) {
                return false;
            }
        }
        User u = new User(name, userName, password);
        users.add(u);
        usersRef.child(u.getUserName()).setValue(u);
        currentUser = u;
        return true;
    }

    public boolean login(String userName, String password) {
        for (User existing: users) {
            if (existing.getUserName().equals(userName) && existing.getPassword().equals(password)) {
                currentUser = existing;
                return true;
            }
        }
        return false;
    }

    public boolean createGroup() {
        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }
    public List<Group> getAllGroups() {
        return groups;
    }

    public Group getGroupByName(String s) {
        for (Group g: groups) {
            if (g.getGroupName().equals(s)) {
                return g;
            }
        }
        return null;
    }

    public List<User> getAllUsers() {
        return users;
    }

    private ChildEventListener getUserListener() {
        return new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                User g = getUser(snapshot);
                users.add(g);
                Log.e("group", g.getUserName());
            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {
                User g = getUser(snapshot);
                users.remove(g);
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildKey) {
                User g = getUser(snapshot);
                users.remove(g);
                users.add(g);
            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildKey) {

            }

            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("error", error.getMessage());
            }

            private User getUser(DataSnapshot snapshot) {
                Map<String, Object> user = (Map<String, Object>) snapshot.getValue();
                return new User((String)user.get("fullName"),
                        (String) user.get("userName"),
                        (String) user.get("password"),
                        (List<String>) user.get("myGroups"),
                        (Availability) user.get("myAvail"));
            }
        };
    }

    private ChildEventListener getGroupListener() {
        return new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                Group g = getGroup(snapshot);
                groups.add(g);
                Log.e("group", g.getGroupName());
            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {
                Group g = getGroup(snapshot);
                groups.remove(g);
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildKey) {
                Group g = getGroup(snapshot);
                groups.remove(g);
                groups.add(g);
            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildKey) {

            }

            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("error", error.getMessage());
            }

            private Group getGroup(DataSnapshot snapshot) {
                Map<String, Object> group = (Map<String, Object>) snapshot.getValue();
                return new Group((List<User>)group.get("members"),
                        (List<Shift>) group.get("myShifts"),
                        (String) group.get("groupName"),
                        (String) group.get("password"));
            }
        };
    }



}
