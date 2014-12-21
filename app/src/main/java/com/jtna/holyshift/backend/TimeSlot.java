package com.jtna.holyshift.backend;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Nishad Agrawal on 11/15/14.
 */
@ParseClassName("TimeSlot")
public abstract class TimeSlot extends ParseObject {

    private static final String MY_DAY = "myDay";
    private static final String START_HR = "startHr";

    protected TimeSlot(Day d, int hr) {
        put(MY_DAY, d.name());
        put(START_HR, hr);
    }

    public Day getMyDay() {
        return Day.valueOf(getString(MY_DAY));
    }

    public int getStartHr() {
        return getInt(START_HR);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TimeSlot)) {
            return false;
        }
        TimeSlot t = (TimeSlot) o;
        return t.getMyDay().equals(this.getMyDay()) && t.getStartHr() == this.getStartHr();
    }
}
