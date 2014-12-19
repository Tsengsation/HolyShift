package com.jtna.holyshift.backend;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Nishad Agrawal on 11/15/14.
 */
@ParseClassName("TimeSlot")
public abstract class TimeSlot extends ParseObject {
    protected Day myDay;
    protected int startHr;

    protected TimeSlot(Day d, int hr) {
        myDay = d;
        startHr = hr;
    }

    public Day getMyDay() {
        return myDay;
    }

    public int getStartHr() {
        return startHr;
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
