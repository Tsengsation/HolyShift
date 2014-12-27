package com.jtna.holyshift.backend;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nishad Agrawal on 11/15/14.
 */
@ParseClassName("Group")
public class Group extends ParseObject {
    private static final String USERS = "users";
    private static final String MY_SHIFTS = "myShifts";
    private static final String GROUP_NAME = "groupName";
    private static final String PASSWORD = "password";

    public Group() {
        this("", "", new ArrayList<Shift>());
    }

    public Group(String groupName, String password,  List<Shift> shifts) {
        this(new ArrayList<ParseUser>(),shifts, groupName, password);
    }

    public Group(List<ParseUser> users, List<Shift> shifts, String groupName, String password) {
        addAllUnique(USERS, users);
        addAllUnique(MY_SHIFTS, shifts);
        put(GROUP_NAME, groupName);
        put(PASSWORD, password);
    }

    public List<ParseUser> getUsers() {
        return getList(USERS);
    }

    public void setUsers(List<ParseUser> users) {
        remove(USERS);
        addAllUnique(USERS, users);
    }

    public void addUser(ParseUser u) {
        addUnique(USERS, u);
    }

    public void removeUser(ParseUser u) {
        removeAll(USERS, Arrays.asList(u));
    }

    public List<Shift> getMyShifts() {
        return getList(MY_SHIFTS);
    }

    public void updateShifts(List<Shift> shifts) {
        remove(MY_SHIFTS);
        addAllUnique(MY_SHIFTS, shifts);
    }

    public void addShift(Shift s) {
        addUnique(MY_SHIFTS, s);
    }

    public void removeShift(Shift s) {
        removeAll(MY_SHIFTS, Arrays.asList(s));
    }

    public String getGroupName() {
        return getString(GROUP_NAME);
    }

    public String getPassword() {
        return getString(PASSWORD);
    }

    public static ParseQuery<Group> getQuery() {
        return ParseQuery.getQuery(Group.class);
    }

    public void updateAvailability() {
        for (Shift s: getMyShifts()) {
            for (ParseUser u: getUsers()) {
                try {
                    for (AvailabilitySlot avail: Availability.getAvailabilityByUser(u).getSlots()) {
                        if (s.equals(avail) && avail.isAvailable()) {
                            s.addAvailable(u);
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void assignShifts() {
        Map<ParseUser, Integer> shiftCounts = new HashMap<ParseUser, Integer>();
        for (ParseUser u: getUsers()) {
            shiftCounts.put(u, new Integer(0));
        }

        for (Shift shift: getMyShifts()) {
            for (int i = 0; i < shift.getNumRequired(); i++) {
                ParseUser assignee = getMostAvailable(shiftCounts, shift);
                if (assignee != null) {
                    shift.addAssigned(assignee);
                    try {
                        Availability avail = Availability.getAvailabilityByUser(assignee);
                        avail.updateSlot(new AvailabilitySlot(false, shift.getMyDay(), shift.getStartHr()));
                        shiftCounts.put(assignee, shiftCounts.get(assignee) + 1);
                    }
                    catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private ParseUser getMostAvailable(Map<ParseUser, Integer> map, Shift s) {
        ParseUser u = null;
        int min = Integer.MAX_VALUE;
        for (ParseUser myUser: map.keySet()) {
            if (s.getWhosAvailable().contains(myUser) && map.get(myUser) < min) {
                u = myUser;
                min = map.get(myUser);
            }
        }
        return u;
    }
}
