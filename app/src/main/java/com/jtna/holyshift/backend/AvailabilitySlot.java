package com.jtna.holyshift.backend;

/**
 * Created by Nishad Agrawal on 11/15/14.
 */
public class AvailabilitySlot extends TimeSlot {
    private boolean isAvailable;

    public AvailabilitySlot(Day d, int hr) {
        super(d, hr);
        isAvailable = true;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
