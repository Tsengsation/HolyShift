package com.jtna.holyshift;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Nishad Agrawal on 12/16/14.
 */
public class HolyShiftApplication extends Application {

    private static final String appId = "SaVX1oRI2gqcdn6XqFkW9klBCyX83ezXVERrHNbu";
    private static final String clientId = "guvwkmkYriof4Fs5nz49vSexcRr8afDwiXhZAm0E";

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, appId, clientId);
    }
}
