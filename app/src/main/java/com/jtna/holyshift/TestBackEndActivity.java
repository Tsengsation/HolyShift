package com.jtna.holyshift;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.jtna.holyshift.backend.AvailabilitySlot;
import com.jtna.holyshift.backend.Day;
import com.jtna.holyshift.backend.Group;
import com.jtna.holyshift.backend.Shift;
import com.jtna.holyshift.backend.User;

import java.util.ArrayList;
import java.util.List;


public class TestBackEndActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_back_end);

        List<Shift> shifts = new ArrayList<Shift>();
        for (int i = 0; i < 10; i++) {
            shifts.add(new Shift(Day.FRIDAY, i, 1));
        }


        List<User> users = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            users.add(new User("name" + i, "userName"+i, "password"));
            for (int j = 0; j < 10; j++) {
                if ((9-i) != j) {
                    users.get(i).getMyAvail().updateSlot(new AvailabilitySlot(false, Day.FRIDAY, j));
                }
            }
        }

        Group g = new Group(users, shifts, "mygroup", "password");
        g.updateAvailability();
        g.assignShifts();
        for (Shift s: g.getMyShifts()) {
            Log.e("shift", "Day: " + s.getMyDay() + "Time: " + s.getStartHr());
            for (User u: s.getWhosAssigned()) {
                Log.e("user", "Name: " + u.getFullName());
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_back_end, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
