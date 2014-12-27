package com.jtna.holyshift;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.jtna.holyshift.backend.Availability;
import com.jtna.holyshift.backend.Day;
import com.jtna.holyshift.backend.Group;
import com.jtna.holyshift.backend.Shift;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ParseTestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParseTestFragment extends Fragment {
    public static final String NUM_REGEX = "-?[0-9]+\\.?[0-9]*";

    private Spinner daySpinner;
    private EditText shiftStartEditText;
    private EditText numberRequiredEditText;
    private Button setShiftButton;
    private EditText groupNameEditText;
    private EditText passwordEditText;
    private Button createGroupButton;
    private Button setAvailabilityButton;
    private Button addUserButton;
    private Button assignShiftsButton;
    private List<Shift> myShifts;
    private Group myGroup;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ParseTestFragment.
     */
    public static ParseTestFragment newInstance() {
        ParseTestFragment fragment = new ParseTestFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ParseTestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initializeComponents(View rootView) {
        daySpinner = (Spinner) rootView.findViewById(R.id.day_spinner);
        daySpinner.setAdapter(new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, Day.getDaysOfWeek()));

        shiftStartEditText = (EditText) rootView.findViewById(R.id.shiftStartEditText);
        numberRequiredEditText = (EditText) rootView.findViewById(R.id.numberRequiredEditText);

        myShifts = new ArrayList<>();
        setShiftButton = (Button) rootView.findViewById(R.id.setShiftButton);
        setShiftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (daySpinner.getSelectedItem() != null &&
                        !shiftStartEditText.getText().toString().isEmpty() &&
                        Pattern.matches(NUM_REGEX, shiftStartEditText.getText().toString()) &&
                        !numberRequiredEditText.getText().toString().isEmpty() &&
                        Pattern.matches(NUM_REGEX, numberRequiredEditText.getText().toString())) {
                    Shift s = new Shift((Day) daySpinner.getSelectedItem(),
                            Integer.parseInt(shiftStartEditText.getText().toString()),
                            Integer.parseInt(numberRequiredEditText.getText().toString()));
                    s.saveInBackground();
                    myShifts.clear();
                    myShifts.add(s);
                    Log.d("PARSE TEST", "created new shift");
                }
            }
        });


        groupNameEditText = (EditText) rootView.findViewById(R.id.groupNameEditText);
        passwordEditText = (EditText) rootView.findViewById(R.id.passwordEditText);
        createGroupButton = (Button) rootView.findViewById(R.id.createGroupButton);
        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!groupNameEditText.getText().toString().isEmpty() &&
                        !passwordEditText.getText().toString().isEmpty() &&
                        !myShifts.isEmpty()) {
                    ParseUtility.createGroup(groupNameEditText.getText().toString(),
                            passwordEditText.getText().toString(),
                            myShifts);
                    Log.d("PARSE TEST", "group set");
                }
            }
        });

        setAvailabilityButton = (Button) rootView.findViewById(R.id.setAvailButton);
        setAvailabilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Availability avail = ParseUtility.getAvailability();
                Log.d("PARSE TEST", "availability retrieved");
            }
        });

        addUserButton = (Button) rootView.findViewById(R.id.addUserButton);
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myGroup != null) {
                    ParseUtility.joinGroup(myGroup);
                    Log.d("PARSE TEST", "user added to group");
                }
            }
        });

        assignShiftsButton = (Button) rootView.findViewById(R.id.assignShiftsButton);
        assignShiftsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("PARSE TEST", "to be implemented");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_parse_test, container, false);
        initializeComponents(rootView);
        return rootView;
    }

}
