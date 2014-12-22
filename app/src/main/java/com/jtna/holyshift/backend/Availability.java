package com.jtna.holyshift.backend;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nishad Agrawal on 11/15/14.
 */
@ParseClassName("Availability")
public class Availability extends ParseObject {

    private static final String USER = "user";
    private static final String MY_AVAIL = "myAvail";

    public Availability() {
        List<AvailabilitySlot> slots = new ArrayList<>();
        for (Day d: Day.values()) {
            for (int hr = 0; hr < 24; hr++) {
                slots.add(new AvailabilitySlot(d, hr));
            }
        }
        put(MY_AVAIL, slots);
    }

    public List<AvailabilitySlot> getSlots() {
        return getList(MY_AVAIL);
    }

    public void updateSlot(AvailabilitySlot slot) {
        for (Object o: getList(MY_AVAIL)) {
            AvailabilitySlot s = (AvailabilitySlot) o;
            if (slot.getMyDay().equals(s.getMyDay()) && slot.getStartHr() == s.getStartHr()) {
                s.setAvailable(slot.isAvailable());
            }
        }
    }

    public ParseUser getUser() {
        return getParseUser(USER);
    }

    public void setUser(ParseUser value) {
        put(USER, value);
    }

    public static ParseQuery<Availability> getQuery() {
        return ParseQuery.getQuery(Availability.class);
    }

    public static Availability getAvailabilityByUser(ParseUser u) throws ParseException {
        ParseQuery<Availability> query = Availability.getQuery();
        query.whereEqualTo(USER, u);
        return query.getFirst();
    }


}
