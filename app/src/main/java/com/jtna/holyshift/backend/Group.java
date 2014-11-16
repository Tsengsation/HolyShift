package com.jtna.holyshift.backend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nishad Agrawal on 11/15/14.
 */
public class Group {
    private List<User> members;
    private ShiftCalendar myCal;
    private String groupName;
    private String password;

    public Group(String groupName, String password, ShiftCalendar myCal) {
        this.password = password;
        this.groupName = groupName;
        this.myCal = myCal;
        members = new ArrayList<User>();
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public void addMember(User u) {
        members.add(u);
    }

    public void removeMember(User u) {
        members.remove(u);
    }

    public ShiftCalendar getMyCal() {
        return myCal;
    }

    public void setMyCal(ShiftCalendar myCal) {
        this.myCal = myCal;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getPassword() {
        return password;
    }

    public void updateAvailability() {
        myCal.updateAvailability(members);
    }

    public List<TimeSlot> getShiftsByUser(User u) {
        return null;
    }
}
