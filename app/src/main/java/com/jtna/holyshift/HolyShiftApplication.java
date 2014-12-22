package com.jtna.holyshift;

import android.app.Application;

import com.jtna.holyshift.backend.Availability;
import com.jtna.holyshift.backend.AvailabilitySlot;
import com.jtna.holyshift.backend.Group;
import com.jtna.holyshift.backend.Shift;
import com.jtna.holyshift.backend.TimeSlot;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by Nishad Agrawal on 12/16/14.
 */
public class HolyShiftApplication extends Application {

    private static final String appId = "SaVX1oRI2gqcdn6XqFkW9klBCyX83ezXVERrHNbu";
    private static final String clientId = "guvwkmkYriof4Fs5nz49vSexcRr8afDwiXhZAm0E";

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Availability.class);
        ParseObject.registerSubclass(AvailabilitySlot.class);
        ParseObject.registerSubclass(Group.class);
        ParseObject.registerSubclass(TimeSlot.class);
        ParseObject.registerSubclass(Shift.class);
        Parse.initialize(this, appId, clientId);
    }
}
