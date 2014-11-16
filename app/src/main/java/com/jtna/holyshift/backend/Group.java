package com.jtna.holyshift.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nishad Agrawal on 11/15/14.
 */
public class Group {
    private List<User> members;
    private List<Shift> myShifts;
    private String groupName;
    private String password;

    public Group(String groupName, String password,  List<Shift> shifts) {
        this.password = password;
        this.groupName = groupName;
        this.myShifts = shifts;
        members = new ArrayList<User>();
    }

    public Group(List<User> members, List<Shift> myShifts, String groupName, String password) {
        this.members = members;
        this.myShifts = myShifts;
        this.groupName = groupName;
        this.password = password;
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

    public List<Shift> getMyShifts() {
        return myShifts;
    }

    public void updateShifts(List<Shift> shifts) {
        myShifts = shifts;
    }

    public void addShift(Shift s) {
        myShifts.add(s);
    }

    public void removeShift(Shift s) {
        myShifts.remove(s);
    }

    public String getGroupName() {
        return groupName;
    }

    public String getPassword() {
        return password;
    }

    public void updateAvailability() {
        for (Shift s: myShifts) {
            for (User u: members) {
                for (AvailabilitySlot avail: u.getMyAvail().getSlots()) {
                    if (s.equals(avail) && avail.isAvailable()) {
                        s.addNewAvailableMember(u);
                    }
                }
            }
        }
    }

    public void assignShifts() {
        Map<User, Integer> shiftCounts = new HashMap<User, Integer>();
        for (User u: members) {
            shiftCounts.put(u, new Integer(0));
        }

        for (Shift shift: myShifts) {
            for (int i = 0; i < shift.getNumRequired(); i++) {
                User assignee = getMostAvailable(shiftCounts, shift);
                if (assignee != null) {
                    shift.addAssigned(assignee);
                    assignee.getMyAvail().updateSlot(new AvailabilitySlot(false, shift.getMyDay(), shift.getStartHr()));
                    shiftCounts.put(assignee, shiftCounts.get(assignee) + 1);
                }
            }
        }
    }

    private User getMostAvailable(Map<User, Integer> map, Shift s) {
        User u = null;
        int min = Integer.MAX_VALUE;
        for (User myUser: map.keySet()) {
            if (s.getWhosAvailable().contains(myUser) && map.get(myUser) < min) {
                u = myUser;
                min = map.get(myUser);
            }
        }
        return u;
    }

    public List<TimeSlot> getShiftsByUser(User u) {
        return null;
    }
}
