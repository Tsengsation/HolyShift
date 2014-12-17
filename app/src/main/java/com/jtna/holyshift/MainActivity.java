package com.jtna.holyshift;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.jtna.holyshift.GroupTabbedView.GroupFragment;


public class MainActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks{

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

        //Starts the Login/Signup Activity
        Intent intent = new Intent(this, LoginActivity.class);
        //TODO: uncomment out
        //startActivity(intent);
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
            Log.e("yay", fragmentName);
        } else if (fragmentName.equals(getResources().getString(R.string.search_groups))) {
            fragment = SearchGroupsFragment.newInstance();
        } else if (fragmentName.equals(getResources().getString(R.string.my_availability))) {
            Log.e("yay", fragmentName);
        } else if (fragmentName.equals(getResources().getString(R.string.settings))) {
            //TODO: pass the username
            fragment = SettingsFragment.newInstance("");
        } else if (fragmentName.equals(getString(R.string.parse_test))) {
            Intent intent = new Intent(this, ParseTestActivity.class);
            startActivity(intent);
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

}
