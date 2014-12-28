package com.jtna.holyshift;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.jtna.holyshift.GroupTabbedView.GroupFragment;
import com.jtna.holyshift.backend.Availability;
import com.jtna.holyshift.backend.AvailabilitySlot;
import com.jtna.holyshift.backend.TimeSlot;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        CreateGroupDialogFragment.DialogListener{

    private Availability myAvail;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        myAvail = ParseUtility.getAvailability();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        displayView(position);
    }

    public void displayView(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        String fragmentName = getResources().getStringArray(R.array.navigation_drawer_strings)[position];
        Fragment fragment = null;
        if (fragmentName.equals(getResources().getString(R.string.my_groups))) {
            fragment = GroupFragment.newInstance("");
        } else if (fragmentName.equals(getResources().getString(R.string.new_group))) {
            DialogFragment newFragment = new CreateGroupDialogFragment();
            newFragment.show(getSupportFragmentManager(), "Create Group");
        } else if (fragmentName.equals(getResources().getString(R.string.search_groups))) {
            fragment = SearchGroupsFragment.newInstance();
        } else if (fragmentName.equals(getResources().getString(R.string.my_availability))) {
            fragment = CalendarFragment.newInstance();
            initAvailabilityCalendar((CalendarFragment) fragment);
        } else if (fragmentName.equals(getString(R.string.profile_fragment))) {
            fragment = ProfileFragment.newInstance();
        } else if (fragmentName.equals(getString(R.string.parse_test_fragment))) {
            fragment = ParseTestFragment.newInstance();
        }

        mTitle = fragmentName;

        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    private void initAvailabilityCalendar(CalendarFragment fragment) {
        fragment.setCells(getAvailableTimeSlots());
        fragment.setSelectedColor(Color.GREEN);
        fragment.setUnselectedColor(Color.RED);
        fragment.setSaveListener(new CalendarListener() {
            @Override
            public void onSaveClicked(CalendarFragment cal) {
                List<TimeSlot> selected = cal.getSelectedCells();
                for (AvailabilitySlot availSlot: myAvail.getSlots()) {
                    boolean isSelected = false;
                    for (TimeSlot slot: selected) {
                        if (slot.getMyDay().equals(availSlot.getMyDay())
                                && slot.getStartHr() == availSlot.getStartHr()) {
                            isSelected = true;
                            break;
                        }
                    }
                    if (isSelected != availSlot.isAvailable()) {
                        availSlot.setAvailable(isSelected);
                        availSlot.saveInBackground();
                    }
                }
                myAvail.saveInBackground();
            }
        });
    }

    private List<TimeSlot> getAvailableTimeSlots() {
        List<AvailabilitySlot> selected = myAvail.getSlots();

        List<TimeSlot> selectedSlots = new ArrayList<>();
        for (AvailabilitySlot slot: selected) {
            if (slot.isAvailable()) {
                selectedSlots.add(slot);
            }
        }
        return selectedSlots;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        CreateGroupDialogFragment myDialog = (CreateGroupDialogFragment) dialog;
        EditText name = myDialog.getGroupNameEditText();
        EditText password = myDialog.getPasswordEditText();

        FragmentManager fragmentManager = getSupportFragmentManager();
        CalendarFragment fragment = CalendarFragment.newInstance();
        fragment.setSaveListener(new CalendarListener() {

            @Override
            public void onSaveClicked(CalendarFragment cal) {
                // TODO: create group with given shifts here
                // fragment.setGroupNameAndPassword(name.getText().toString(), password.getText().toString());
            }
        });

        mTitle = "New Group";

        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
}
