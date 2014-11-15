package com.jtna.holyshift;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.Firebase;


public class TestFirebaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_firebase);
        Firebase.setAndroidContext(this);

        Firebase rootRef = new Firebase("https://holyshift.firebaseio.com/");
        Log.e("asdfadsa", "asdfasdf");

//        User test1 = new User("Test 1", "1234", "test1@test.com");
//        User test2 = new User("Test 2", "5678", "test2@test.com");
//
//        Firebase usersRef = rootRef.child("users");
//        Map<String, User> users = new HashMap<String, User>();
//        users.put(test1.getFullName(), test1);
//        users.put(test2.getFullName(), test2);
//        usersRef.setValue(users, new Firebase.CompletionListener() {
//            @Override
//            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
//                if (firebaseError != null) {
//                   Log.e("DataSave", "Data could not be saved. " + firebaseError.getMessage());
//                } else {
//                    Log.e("DataSave", "Data saved successfully.");
//                }
//            }
//        });
//
//        Firebase postRef = rootRef.child("posts");
//        Map<String, String> post1 = new HashMap<String, String>();
//        post1.put("author", "gracehop");
//        post1.put("title", "Announcing COBOL, a New Programming Language");
//        postRef.push().setValue(post1);
//        Map<String, String> post2 = new HashMap<String, String>();
//        post2.put("author", "alanisawesome");
//        post2.put("title", "The Turing Machine");
//        postRef.push().setValue(post2);
//
//        Firebase newPostRef = postRef.push();
//
//        Map<String, String> post3 = new HashMap<String, String>();
//        post3.put("author", "gracehop");
//        post3.put("title", "Announcing COBOL, a New Programming Language");
//        newPostRef.setValue(post3);
//        // Get the unique ID generated by push()
//        String postId = newPostRef.getKey();
//        Log.e("DataSave", postId);
//
//        postRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                Log.e("Data found",snapshot.getValue().toString());
//            }
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                Log.e("Data found","The read failed: " + firebaseError.getMessage());
//            }
//        });
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
