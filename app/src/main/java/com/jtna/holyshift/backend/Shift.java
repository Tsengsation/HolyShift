package com.jtna.holyshift.backend;

import com.parse.ParseClassName;
import com.parse.ParseUser;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Nishad Agrawal on 11/15/14.
 */
@ParseClassName("Shift")
public class Shift extends TimeSlot {

    private static final String NUM_REQUIRED = "numRequired";
    private static final String WHOS_AVAILABLE = "whosAvailable";
    private static final String WHOS_ASSIGNED = "whosAssigned";

    public Shift() {
        this(Day.UNSPECIFIED, 0, 0);
    }

    public Shift(Day d, int hr, int required) {
        super(d, hr);
        setNumRequired(required);
    }

    public int getNumRequired() {
        return getInt(NUM_REQUIRED);
    }

    public void setNumRequired(int numRequired) {
        put(NUM_REQUIRED, numRequired);
    }

    public List<ParseUser> getWhosAvailable() {
        return getList(WHOS_AVAILABLE);
    }

    public void setWhosAvailable(List<ParseUser> whosAvailable) {
        remove(WHOS_AVAILABLE);
        addAllUnique(WHOS_AVAILABLE, whosAvailable);
    }

    public void addNewAvailableMember(ParseUser u) {
        addUnique(WHOS_AVAILABLE, u);
    }

    public void removeAvailableMember(ParseUser u) {
        removeAll(WHOS_AVAILABLE, Arrays.asList(u));
        removeAssigned(u);
    }

    public List<ParseUser> getWhosAssigned() {
        return getList(WHOS_ASSIGNED);
    }

    public void setWhosAssigned(List<ParseUser> whosAssigned) {
        remove(WHOS_ASSIGNED);
        addAllUnique(WHOS_ASSIGNED, whosAssigned);
    }

    public void addAssigned(ParseUser u) {
        addUnique(WHOS_ASSIGNED, u);
    }

    public void removeAssigned(ParseUser u) {
        removeAll(WHOS_ASSIGNED, Arrays.asList(u));
    }
}
