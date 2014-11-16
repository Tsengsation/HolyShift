package com.jtna.holyshift.backend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nishad Agrawal on 11/15/14.
 */
public class Availability {
    List<AvailabilitySlot> myAvail;

    public Availability() {
        this.myAvail = new ArrayList<AvailabilitySlot>();
        for (Day d: Day.values()) {
            for (int hr = 0; hr < 24; hr++) {
                myAvail.add(new AvailabilitySlot(d, hr));
            }
        }
    }

    public List<AvailabilitySlot> getSlots() {
        return myAvail;
    }

    public void updateSlot(AvailabilitySlot slot) {
        for (AvailabilitySlot s: myAvail) {
            if (slot.getMyDay().equals(s.getMyDay()) && slot.getStartHr() == s.getStartHr()) {
                s.setAvailable(slot.isAvailable());
            }
        }
    }
}
