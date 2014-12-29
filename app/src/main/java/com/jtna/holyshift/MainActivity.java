package com.jtna.holyshift;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.jtna.holyshift.GroupTabbedView.GroupFragment;
import com.jtna.holyshift.backend.AvailabilitySlot;
import com.jtna.holyshift.backend.Day;
import com.jtna.holyshift.backend.Shift;
import com.jtna.holyshift.backend.TimeSlot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private ParseUtility utility;

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

        utility = ParseUtility.getInstance();
        FetchDataTask fetchDataTask = new FetchDataTask();
        fetchDataTask.execute();

        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
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
            fragment = MyGroupsFragment.newInstance();
            ((MyGroupsFragment) fragment).setGroups(utility.getMyGroups());
        } else if (fragmentName.equals(getResources().getString(R.string.new_group))) {
            CreateGroupDialogFragment newFragment = new CreateGroupDialogFragment();
            newFragment.setDialogListener(getDialogListener());
            newFragment.show(getSupportFragmentManager(), getString(R.string.new_group));
        } else if (fragmentName.equals(getResources().getString(R.string.search_groups))) {
            fragment = SearchGroupsFragment.newInstance();
            ((SearchGroupsFragment) fragment).setGroups(utility.getAllGroups());
        } else if (fragmentName.equals(getResources().getString(R.string.my_availability))) {
            fragment = CalendarFragment.newInstance();
            initAvailabilityCalendar((CalendarFragment) fragment);
        } else if (fragmentName.equals(getString(R.string.profile_fragment))) {
            fragment = ProfileFragment.newInstance();
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
        final int selectedColor = Color.GREEN;
        final int unselectedColor = Color.RED;
        fragment.setSelectedColor(selectedColor);
        fragment.setUnselectedColor(unselectedColor);
        fragment.setSaveListener(new CalendarListener() {
            @Override
            public void onSaveClicked(CalendarFragment cal) {
                List<TimeSlot> selected = cal.getSelectedCells();
                for (AvailabilitySlot availSlot: utility.getMyAvailability().getSlots()) {
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
                utility.getMyAvailability().saveInBackground();
            }

            @Override
            public void onCellClicked(CalendarFragment cal, CalendarCell cell) {
                int color = ((ColorDrawable) cell.getBackground()).getColor();
                TimeSlot slot = new TimeSlot(Day.values()[cell.getMyDay()],
                        cell.getMyHour());
                if (color == selectedColor) {
                    cell.setBackgroundColor(unselectedColor);
                    cal.getSelectedCells().remove(slot);
                } else {
                    cell.setBackgroundColor(selectedColor);
                    cal.getSelectedCells().add(slot);
                }
            }
        });
    }

    private List<TimeSlot> getAvailableTimeSlots() {
        List<AvailabilitySlot> selected = utility.getMyAvailability().getSlots();

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

    private DialogListener getDialogListener() {
        return new DialogListener() {
            @Override
            public void onDialogNegativeClick(DialogFragment dialog) {
                //do nothing
            }

            @Override
            public void onDialogPositiveClick(DialogFragment dialog) {
                CreateGroupDialogFragment myDialog = (CreateGroupDialogFragment) dialog;
                final EditText name = myDialog.getGroupNameEditText();
                final EditText password = myDialog.getPasswordEditText();

                if (name.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill in information", Toast.LENGTH_LONG).show();
                    return;
                }

                FragmentManager fragmentManager = getSupportFragmentManager();
                final CalendarFragment fragment = CalendarFragment.newInstance();
                fragment.setCells(new ArrayList<TimeSlot>());
                final int selectedColor = Color.BLUE;
                final int unselectedColor = Color.GRAY;
                fragment.setSelectedColor(selectedColor);
                fragment.setUnselectedColor(unselectedColor);
                fragment.setSaveListener(new CalendarListener() {
                    Map<TimeSlot, Integer> map = new HashMap<>();

                    @Override
                    public void onSaveClicked(CalendarFragment cal) {
                        List<Shift> shifts = new ArrayList<Shift>();
                        for (TimeSlot slot: map.keySet()) {
                            shifts.add(new Shift(slot, map.get(slot)));
                        }
                        utility.createGroup(name.getText().toString(),
                                password.getText().toString(), shifts);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, GroupFragment.newInstance(name.getText().toString()))
                                .commit();
                    }

                    @Override
                    public void onCellClicked(final CalendarFragment cal, final CalendarCell cell) {
                        int color = ((ColorDrawable) cell.getBackground()).getColor();
                        final TimeSlot slot = new TimeSlot(Day.values()[cell.getMyDay()],
                                cell.getMyHour());
                        if (color == selectedColor) {
                            cell.setBackgroundColor(unselectedColor);
                            cal.getSelectedCells().remove(slot);
                            map.remove(slot);
                        } else {
                            PickerDialogFragment newFragment = new PickerDialogFragment();
                            newFragment.setDialogListener(new DialogListener() {
                                @Override
                                public void onDialogPositiveClick(DialogFragment dialog) {
                                    PickerDialogFragment frag = (PickerDialogFragment) dialog;
                                    map.put(slot, frag.getNumPicker().getValue());
                                    cell.setBackgroundColor(selectedColor);
                                }

                                @Override
                                public void onDialogNegativeClick(DialogFragment dialog) {
                                    cal.getSelectedCells().remove(slot);
                                    cell.setBackgroundColor(unselectedColor);
                                }
                            });
                            newFragment.show(getSupportFragmentManager(), "Create New Shift");
                            cal.getSelectedCells().add(slot);
                        }
                    }
                });

                mTitle = getString(R.string.new_group);

                if (fragment != null) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, fragment)
                            .commit();
                } else {
                    // error in creating fragment
                    Log.e("MainActivity", "Error in creating fragment");
                }
            }
        };
    }


    private class FetchDataTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Fetching Data From Server...");
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            utility.fetchData();
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            dialog.dismiss();
        }
    }

}
