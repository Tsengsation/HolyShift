package com.jtna.holyshift;

import com.jtna.holyshift.backend.Availability;
import com.jtna.holyshift.backend.AvailabilitySlot;
import com.jtna.holyshift.backend.Group;
import com.jtna.holyshift.backend.Shift;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nishad Agrawal on 12/27/14.
 */
public class ParseUtility {
    private static ParseUtility instance = null;

    private Availability myAvail;
    private List<Group> allGroups;
    private ParseUser me;

    protected ParseUtility() {
        // Exists only to defeat instantiation.
    }

    public static ParseUtility getInstance() {
        if(instance == null) {
            instance = new ParseUtility();
        }
        return instance;
    }

    public void fetchData() {
        me = ParseUser.getCurrentUser();
        myAvail = getAvailability();
        allGroups = getGroups();
    }

    public Availability getMyAvailability() {
        return myAvail;
    }

    public List<Group> getAllGroups() {
        return allGroups;
    }

    private Availability getAvailability() {
        Availability avail = Availability.getAvailabilityByUser(me);
        if (avail == null) {
            avail = new Availability();
            avail.initialize();
            avail.saveInBackground();
            avail.setUser(me);
            avail.saveInBackground();
        }
        else {
            try {
                avail = avail.fetchIfNeeded();
                for (AvailabilitySlot slot: avail.getSlots()) {
                    slot.fetchIfNeeded();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return avail;
    }

    public Group createGroup(String groupName, String password, List<Shift> shifts) {
        Group g = new Group(groupName, password, shifts);
        g.saveInBackground();
        joinGroup(password, g);
        allGroups.add(g);
        return g;
    }

    private List<Group> getGroups() {
        ParseQuery<Group> query = Group.getQuery();
        try {
            List<Group> groups = query.find();
            if (!groups.isEmpty()) {
                for (Group g : groups) {
                    g.fetchIfNeeded();
                    for (ParseUser u: g.getUsers()) {
                        u.fetchIfNeeded();
                    }
                }
            }
            return groups;
        }
        catch (ParseException e) {
            return new ArrayList<>();
        }
    }

    public boolean joinGroup(String password, Group group) {
        if (!group.getUsers().contains(me) && group.getPassword().equals(password)) {
            group.addUser(me);
            group.saveInBackground();
            return true;
        }
        return false;
    }

    public List<Group> getMyGroups() {
        List<Group> groups = new ArrayList<>();
        for (Group g: allGroups) {
            for (ParseUser user: g.getUsers()) {
                if (user.getUsername().equals(me.getUsername())) {
                    groups.add(g);
                }
            }
        }
        return groups;
    }

}
