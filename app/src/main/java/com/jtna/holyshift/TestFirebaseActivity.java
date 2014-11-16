package com.jtna.holyshift;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.jtna.holyshift.backend.Day;
import com.jtna.holyshift.backend.Group;
import com.jtna.holyshift.backend.Shift;
import com.jtna.holyshift.backend.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestFirebaseActivity extends Activity {
    private Firebase rootRef;
    private Firebase usersRef;
    private Firebase groupsRef;
    private int i;
    private int j;
    private Map<String, User> users;
    private Map<String, Group> groups;
    private ValueEventListener connectedListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_firebase);
        Firebase.setAndroidContext(this);

        rootRef = new Firebase("https://holyshift.firebaseio.com/");
        usersRef = rootRef.child("users");
        groupsRef = rootRef.child("groups");
        users = new HashMap<String, User>();
        groups = new HashMap<String, Group>();

        Button clear = (Button) findViewById(R.id.button);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootRef.removeValue();

            }
        });

        Button addUser = (Button) findViewById(R.id.button2);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("asdf","add user");
                createUser();
                usersRef.setValue(users, getListener());
                i++;
            }
        });

        Button addGroup = (Button) findViewById(R.id.button3);
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("asdf","add group");
                Group g = createGroup();
                groupsRef.setValue(groups, getListener());
                usersRef.setValue(users, getListener());
                j++;
            }
        });

        Query q = groupsRef.orderByKey();
        q.addValueEventListener(getGroupListener());
    }

    private ValueEventListener getGroupListener() {
        return new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                StringBuilder sb = new StringBuilder("Groups: \n");
                TextView groupText = (TextView) findViewById(R.id.groupTextView);
                for (DataSnapshot child: snapshot.getChildren()) {
                    Log.e("groups", child.getKey());
                    sb.append(child.getKey() + "\n");
                }
                groupText.setText(sb.toString());
            }

            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("error", error.getMessage());
            }
        };
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

    private Group createGroup() {
        List<Shift> shifts = new ArrayList<Shift>();
        for (int i = 0; i < 24; i++) {
            Shift s = new Shift(Day.SUNDAY, i, 1);
            shifts.add(s);
        }
        Group g = new Group("group " + j, "group_password",  shifts);
        for (User user: users.values()) {
            user.addNewGroup(g);
            g.addMember(user);
        }

        g.updateAvailability();
        groups.put(g.getGroupName(), g);
        return g;
    }

    private void createUser() {
        User u = new User("name " + i, "userName"+i, "password");
        users.put(u.getUserName(), u);
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
        getMenuInflater().inflate(R.menu.menu_test_firebase, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
