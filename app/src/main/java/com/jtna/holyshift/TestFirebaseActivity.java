package com.jtna.holyshift;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.jtna.holyshift.backend.Day;
import com.jtna.holyshift.backend.Group;
import com.jtna.holyshift.backend.Shift;
import com.jtna.holyshift.backend.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class TestFirebaseActivity extends Activity {
    private Firebase rootRef;
    private Firebase usersRef;
    private Firebase groupsRef;
    private int i;
    private int j;
    private List<User> users;
    private List<Group> groups;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_firebase);
        Firebase.setAndroidContext(this);

        rootRef = new Firebase("https://holyshift.firebaseio.com/");
        usersRef = rootRef.child("users");
        groupsRef = rootRef.child("groups");
        users = new ArrayList<User>();
        groups = new ArrayList<Group>();

        Button clear = (Button) findViewById(R.id.button);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootRef.removeValue();
                users.clear();
            }
        });

        Button addUser = (Button) findViewById(R.id.button2);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("asdf","add user");
                createUser();
                i++;
            }
        });

        Button addGroup = (Button) findViewById(R.id.button3);
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("asdf","add group");
                createGroup();
                j++;
            }
        });

        groupsRef.addChildEventListener(getGroupListener());


    }


    private ChildEventListener getGroupListener() {
        return new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                Map<String, Object> group = (Map<String, Object>) snapshot.getValue();
                Group g = new Group((List<User>)group.get("members"),
                        (List<Shift>) group.get("myShifts"),
                        (String) group.get("groupName"),
                        (String) group.get("password"));
                Log.e("group", g.getGroupName());
            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {

            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildKey) {

            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildKey) {

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

    private void createGroup() {
        List<Shift> shifts = new ArrayList<Shift>();
        for (int i = 0; i < 24; i++) {
            Shift s = new Shift(Day.SUNDAY, i, 1);
            shifts.add(s);
        }
        Group g = new Group("group " + j, "group_password",  shifts);


        for (User user: users) {
            user.addNewGroup(g);
            g.addMember(user);
        }

        for (User user: users) {
            usersRef.child(user.getUserName()).setValue(user);
        }

        g.updateAvailability();
        Firebase newGroup = groupsRef.push();
        newGroup.setValue(g);
    }

    private void createUser() {
        User u = new User("name " + i, "userName"+i, "password");
        users.add(u);
        usersRef.child(u.getUserName()).setValue(u);
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
