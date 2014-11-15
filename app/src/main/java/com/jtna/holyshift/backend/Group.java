package com.jtna.holyshift.backend;

import java.util.List;

/**
 * Created by Nishad Agrawal on 11/15/14.
 */
public class Group {
    private List<User> members;
    private ShiftCalendar myCal;
    private String groupName;
    private String password;

    private List<TimeSlot> getShiftsByUser(User u) {
        return null;
    }
}
