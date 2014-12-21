package com.jtna.holyshift.backend;

import com.parse.ParseClassName;

/**
 * Created by Nishad Agrawal on 11/15/14.
 */
@ParseClassName("AvailabilitySlot")
public class AvailabilitySlot extends TimeSlot {

    private static final String IS_AVAILABLE = "isAvailable";

    public AvailabilitySlot(Day d, int hr) {
        this(true, d, hr);
    }

    public AvailabilitySlot(boolean avail, Day d, int hr) {
        super(d, hr);
        put(IS_AVAILABLE, avail);
    }

    public boolean isAvailable() {
        return getBoolean(IS_AVAILABLE);
    }

    public void setAvailable(boolean isAvailable) {
        put(IS_AVAILABLE, isAvailable);
    }
}
