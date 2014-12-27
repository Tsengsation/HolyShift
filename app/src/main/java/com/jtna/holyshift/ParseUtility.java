package com.jtna.holyshift;

import android.util.Log;

import com.jtna.holyshift.backend.Availability;
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
        try {
            return Availability.getAvailabilityByUser(ParseUser.getCurrentUser());
        } catch (ParseException e) {
            Log.d("PARSE", e.getMessage());
            Availability avail = new Availability();
            avail.saveInBackground();
            avail.setUser(ParseUser.getCurrentUser());
            avail.saveInBackground();
            return avail;
        }
    }

    public static Group createGroup(String groupName, String password, List<Shift> shifts) {
        Group g = new Group(groupName, password, shifts);
        g.saveInBackground();
        return g;
    }

    public static List<Group> getAllGroups() {
        final List<Group> allGroups = new ArrayList<>();
        ParseQuery<Group> query = Group.getQuery();
        query.findInBackground(new FindCallback<Group>() {
            @Override
            public void done(List<Group> results, ParseException e) {
                allGroups.addAll(results);
            }
        });
        return allGroups;
    }

    public static void joinGroup(Group group) {
        group.addUser(ParseUser.getCurrentUser());
        group.saveInBackground();
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
