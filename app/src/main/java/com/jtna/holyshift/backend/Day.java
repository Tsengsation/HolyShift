package com.jtna.holyshift.backend;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Nishad Agrawal on 11/15/14.
 */
public enum Day {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
    THURSDAY, FRIDAY, SATURDAY, UNSPECIFIED;

    public static List<Day> getDaysOfWeek() {
        List<Day> days = Arrays.asList(Day.values());
        days.remove(UNSPECIFIED);
        return days;
    }
}
