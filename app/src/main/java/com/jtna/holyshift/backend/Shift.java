package com.jtna.holyshift.backend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nishad Agrawal on 11/15/14.
 */
public class Shift extends TimeSlot {

    private int numRequired;
    private List<User> whosAvailable;
    private List<User> whosAssigned;

    public Shift(Day d, int hr, int required) {
        super(d, hr);
        numRequired = required;
        whosAvailable = new ArrayList<User>();
        whosAssigned = new ArrayList<User>();
    }

    public int getNumRequired() {
        return numRequired;
    }

    public void setNumRequired(int numRequired) {
        this.numRequired = numRequired;
    }

    public List<User> getWhosAvailable() {
        return whosAvailable;
    }

    public void setWhosAvailable(List<User> whosAvailable) {
        this.whosAvailable = whosAvailable;
    }

    public void addNewAvailableMember(User u) {
        whosAvailable.add(u);
    }

    public void removeAvailableMember(User u) {
        whosAvailable.remove(u);
        whosAssigned.remove(u);
    }

    public List<User> getWhosAssigned() {
        return whosAssigned;
    }

    public void setWhosAssigned(List<User> whosAssigned) {
        this.whosAssigned = whosAssigned;
    }

    public void addAssigned(User u) {
        whosAssigned.add(u);
    }

    public void removeAssigned(User u) {
        whosAssigned.remove(u);
    }
}
