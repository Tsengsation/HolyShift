package com.jtna.holyshift;

import android.util.Log;

import com.jtna.holyshift.backend.Availability;
import com.jtna.holyshift.backend.AvailabilitySlot;
import com.jtna.holyshift.backend.Group;
import com.jtna.holyshift.backend.Shift;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Nishad Agrawal on 12/27/14.
 */
public class ParseUtility {

    public static Availability getAvailability() {
        Availability avail = Availability.getAvailabilityByUser(ParseUser.getCurrentUser());
        if (avail == null) {
            Log.d("PARSE", "no existing availability");
            avail = new Availability();
            avail.initialize();
            avail.saveInBackground();
            avail.setUser(ParseUser.getCurrentUser());
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

    public static Group createGroup(String groupName, String password, List<Shift> shifts) {
        Group g = new Group(groupName, password, shifts);
        g.saveInBackground();
        return g;
    }

    public static List<Group> getAllGroups() {
        ParseQuery<Group> query = Group.getQuery();
        try {
            List<Group> groups = query.find();
            if (!groups.isEmpty()) {
                for (Group g : groups) {
                    g.fetchIfNeeded();
                    // TODO: add fetching
//                    for (Shift s : g.getMyShifts()) {
//                        s.fetchIfNeeded();
//                    }
                }
            }
            return groups;
        }
        catch (ParseException e) {
            return new ArrayList<>();
        }
    }

    public static boolean joinGroup(String password, Group group) {
        ParseUser me = ParseUser.getCurrentUser();
        if (!group.getUsers().contains(me) && group.getPassword().equals(password)) {
            group.addUser(me);
            group.saveInBackground();
            return true;
        }
        return false;
    }

    public static List<Group> getMyGroups() {
        final List<Group> allGroups = new ArrayList<>();
        ParseQuery<Group> query = Group.getQuery();
        query.whereContainsAll("users", Arrays.asList(ParseUser.getCurrentUser()));
        query.findInBackground(new FindCallback<Group>() {
            @Override
            public void done(List<Group> results, ParseException e) {
                allGroups.addAll(results);
            }
        });
        return allGroups;
    }

}