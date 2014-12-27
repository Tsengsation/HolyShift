package com.jtna.holyshift;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class CalendarFragment extends Fragment {
    private static final String ARG_GROUPNAME = "groupName";

    private static final int SELECTED_COLOR = Color.GREEN;
    private static final int UNSELECTED_COLOR = Color.BLACK;

    private CalendarCell[][] mCalendarGrid;
    private List<CalendarCell> selected;
    private String myGroupName;
    private String myPassword;

    private LinearLayout mCalendar;
    private String[] mColumns = new String[] {
        "HOUR",
        "SUN",
        "MON",
        "TUES",
        "WED",
        "THUR",
        "FRI",
        "SAT"
    };

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CalendarFragment.
     */
    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public CalendarFragment() {
        mCalendarGrid = new CalendarCell[24][7];
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selected = new ArrayList<>();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_calendar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                // TODO
                Log.d("MENU", "save stuff");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        initializeComponent(rootView);
        return rootView;
    }

    public void setGroupNameAndPassword(String groupName, String password) {
        myGroupName = groupName;
        myPassword = password;
    }

    private void initializeComponent(View root) {
        mCalendar = (LinearLayout) root.findViewById(R.id.calendar_tablelayout);
        mCalendar.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));

        mCalendar.addView(createTableHeader());
        for (int i = 0; i < 24; i++) {
            mCalendar.addView(createRow(i));
        }

        for (int i = 0; i < mCalendarGrid.length; i++) {
            for (int j = 0; j < mCalendarGrid[0].length; j++) {
                mCalendarGrid[i][j].setBackgroundColor(UNSELECTED_COLOR);
                mCalendarGrid[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int color = ((ColorDrawable)((CalendarCell)v).getBackground()).getColor();
                        if (color == SELECTED_COLOR) {
                            v.setBackgroundColor(UNSELECTED_COLOR);
                            selected.remove(v);
                        } else {
                            v.setBackgroundColor(SELECTED_COLOR);
                            selected.add((CalendarCell) v);
                        }
                    }
                });
            }
        }

    }

    private LinearLayout createTableHeader() {
        LinearLayout row = new LinearLayout(getActivity());
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        for (int i = 0; i < mColumns.length; i++) {
            TextView view = new TextView(getActivity());
            view.setLayoutParams(new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            view.setText(mColumns[i]);
            view.setGravity(Gravity.CENTER);
            row.addView(view, i);
        }
        return row;
    }

    private LinearLayout createRow(int hour) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        layoutParams.setMargins(0,0,0,0);

        LinearLayout row = new LinearLayout(getActivity());
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        TextView text = new TextView(getActivity());
        text.setLayoutParams(layoutParams);
        text.setText(hour + ":00");
        row.addView(text, 0);
        for (int i = 0; i < mColumns.length-1; i++) {
            CalendarCell cell = new CalendarCell(getActivity(), i, hour);
            row.addView(cell, i + 1);
            mCalendarGrid[hour][i] = cell;
        }
        return row;
    }

}
