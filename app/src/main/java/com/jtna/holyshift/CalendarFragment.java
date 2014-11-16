package com.jtna.holyshift;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class CalendarFragment extends Fragment {
    private static final String ARG_GROUPNAME = "groupName";

    private String mGroupName;

    private ToggleButton[][] mCalendarGrid;

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
     * @param groupName name of group
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String groupName) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_GROUPNAME, groupName);
        fragment.setArguments(args);
        return fragment;
    }
    public CalendarFragment() {
        mCalendarGrid = new ToggleButton[24][7];
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGroupName = getArguments().getString(ARG_GROUPNAME);
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

    private void initializeComponent(View root) {
        mCalendar = (LinearLayout) root.findViewById(R.id.calendar_tablelayout);
        mCalendar.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        mCalendar.addView(createTableHeader());
        for (int i = 0; i < 24; i++) {
            mCalendar.addView(createRow(i));
        }

        for (ToggleButton[] row : mCalendarGrid) {
            for (ToggleButton butt : row) {
                butt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }

    }

    private LinearLayout createTableHeader() {
        LinearLayout row = new LinearLayout(getActivity());
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        for (int i = 0; i < mColumns.length; i++) {
            TextView view = new TextView(getActivity());
            view.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            view.setText(mColumns[i]);
            view.setGravity(Gravity.CENTER);
            row.addView(view, i);
        }
        return row;
    }

    private LinearLayout createRow(int hour) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        layoutParams.setMargins(0,0,0,0);

        LinearLayout row = new LinearLayout(getActivity());
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        TextView text = new TextView(getActivity());
        text.setLayoutParams(layoutParams);
        text.setText(hour + ":00");
        row.addView(text, 0);
        for (int i = 0; i < mColumns.length-1; i++) {
            ToggleButton b = new ToggleButton(getActivity());
            b.setText("");
            b.setTextOff("");
            b.setTextOn("");
            b.setLayoutParams(layoutParams);
            row.addView(b, i + 1);
            mCalendarGrid[hour][i] = b;
        }
        return row;
    }


}
