package com.jtna.holyshift.backend;

import com.parse.ParseClassName;

/**
 * Created by Nishad Agrawal on 11/15/14.
 */
@ParseClassName("AvailabilitySlot")
public class AvailabilitySlot extends TimeSlot {
    private boolean isAvailable;

    public AvailabilitySlot(Day d, int hr) {
        super(d, hr);
        isAvailable = true;
    }

    public AvailabilitySlot(boolean avail, Day d, int hr) {
        super(d, hr);
        isAvailable = avail;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
