package com.jtna.holyshift;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.jtna.holyshift.backend.Day;
import com.jtna.holyshift.backend.Group;
import com.jtna.holyshift.backend.Shift;
import com.jtna.holyshift.backend.ShiftCalendar;
import com.jtna.holyshift.backend.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestFirebaseActivity extends Activity {
    private Firebase rootRef;
    private Firebase usersRef;
    private Firebase groupsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_firebase);
        Firebase.setAndroidContext(this);

        rootRef = new Firebase("https://holyshift.firebaseio.com/");
        usersRef = rootRef.child("users");
        groupsRef = rootRef.child("groups");

        Button clear = (Button) findViewById(R.id.button);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootRef.removeValue();

            }
        });

        Button refresh = (Button) findViewById(R.id.button2);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("refresh!");
            }
        });

        setUpReceiver();

//
//        Map<String, User> users = createUsers();
//
//
//        Map<String, Group> groups = new HashMap<String, Group>();
//        Group g = createGroup(users);
//        groups.put(g.getGroupName(), g);
//
//        usersRef.setValue(users, getListener());
//        groupsRef.setValue(groups, getListener());
    }

    private Firebase.CompletionListener getListener() {
        return new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    Log.e("FireBase", "Could not be saved. " + firebaseError.getMessage());
                } else {
                    Log.e("FireBase", "Saved successfully.");
                }
            }
        };
    }

    private Group createGroup(Map<String, User> users) {
        List<Shift> shifts = new ArrayList<Shift>();
        for (int i = 0; i < 24; i++) {
            Shift s = new Shift(Day.SUNDAY, i, 1);
            shifts.add(s);
        }
        ShiftCalendar cal = new ShiftCalendar(shifts);
        Group g = new Group("group 1", "group_password",  cal);
        for (User user: users.values()) {
            user.addNewGroup(g);
            g.addMember(user);
        }

        g.updateAvailability();
        return g;
    }

    private Map<String, User> createUsers() {
        Map<String, User> users = new HashMap<String, User>();
        for (int i = 0; i < 10; i++) {
            User u = new User("name " + i, "userName"+i, "password");
            users.put(u.getUserName(), u);
        }
        return users;
    }

    private void setUpReceiver() {
        groupsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_firebase, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
