package com.jtna.holyshift;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.jtna.holyshift.backend.Day;
import com.jtna.holyshift.backend.TimeSlot;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class CalendarFragment extends Fragment {
    private static final String ARG_GROUPNAME = "groupName";

    private static int SELECTED_COLOR = Color.BLUE;
    private static int UNSELECTED_COLOR = Color.GRAY;

    private CalendarCell[][] mCalendarGrid;
    private List<TimeSlot> selected;
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

    private CalendarListener myListener;

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
                if (myListener == null) {
                    myListener = new CalendarListener() {
                        @Override
                        public void onSaveClicked(CalendarFragment cal) {

                        }

                        @Override
                        public void onCellClicked(CalendarFragment cal, CalendarCell cell) {
                            int color = ((ColorDrawable) cell.getBackground()).getColor();
                            TimeSlot slot = new TimeSlot(Day.values()[cell.getMyDay()],
                                    cell.getMyHour());
                            if (color == SELECTED_COLOR) {
                                cell.setBackgroundColor(UNSELECTED_COLOR);
                                selected.remove(slot);
                            } else {
                                cell.setBackgroundColor(SELECTED_COLOR);
                                selected.add(slot);
                            }
                        }
                    };
                }
                myListener.onSaveClicked(CalendarFragment.this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Make sure this gets called to have save functionality;
     * @param listener
     */
    public void setSaveListener(CalendarListener listener) {
        myListener = listener;
    }

    public void setSelectedColor(int color) {
        SELECTED_COLOR = color;
    }

    public void setUnselectedColor(int color) {
        UNSELECTED_COLOR = color;
    }

    public void setCells(List<TimeSlot> selectedSlots) {
        selected = selectedSlots;
    }

    public List<TimeSlot> getSelectedCells() {
        return selected;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        initializeComponent(rootView);
        return rootView;
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
                CalendarCell cell = mCalendarGrid[i][j];
                boolean isSelected = false;
                for (TimeSlot slot: selected) {
                    if (slot.getMyDay().ordinal() == cell.getMyDay()
                            && slot.getStartHr() == cell.getMyHour()) {
                        isSelected = true;
                        break;
                    }
                }
                if (isSelected) {
                    mCalendarGrid[i][j].setBackgroundColor(SELECTED_COLOR);
                }
                else {
                    mCalendarGrid[i][j].setBackgroundColor(UNSELECTED_COLOR);
                }
                mCalendarGrid[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CalendarCell cell = (CalendarCell) v;
                        myListener.onCellClicked(CalendarFragment.this, cell);
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
