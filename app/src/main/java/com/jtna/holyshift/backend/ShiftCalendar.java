package com.jtna.holyshift.backend;

import java.util.List;

/**
 * Created by Nishad Agrawal on 11/15/14.
 */
public class ShiftCalendar {
    private List<Shift> myShifts;

    public ShiftCalendar(List<Shift> myShifts) {
        this.myShifts = myShifts;
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

    public List<Shift> getMyShifts() {
        return myShifts;
    }

    public void updateAvailability(List<User> members) {
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
}
