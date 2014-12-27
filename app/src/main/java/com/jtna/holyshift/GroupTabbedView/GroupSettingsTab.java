package com.jtna.holyshift.GroupTabbedView;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jtna.holyshift.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupSettingsTab#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class GroupSettingsTab extends Fragment {
    private static final String ARG_GROUPNAME = "groupName";

    private String mGroupName;

    private EditText mPasswordEditText;
    private Button mUpdateButton;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param groupName name of group.
     * @return A new instance of fragment GroupSettingsTab.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupSettingsTab newInstance(String groupName) {
        GroupSettingsTab fragment = new GroupSettingsTab();
        Bundle args = new Bundle();
        args.putString(ARG_GROUPNAME, groupName);
        fragment.setArguments(args);
        return fragment;
    }
    public GroupSettingsTab() {
        // Required empty public constructor
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
        View rootView = inflater.inflate(R.layout.fragment_group_settings_tab, container, false);
        initializeComponents(rootView);
        return rootView;
    }

    private void initializeComponents(View root) {
        mPasswordEditText = (EditText) root.findViewById(R.id.group_settings_edittext_password);
        mUpdateButton = (Button) root.findViewById(R.id.group_settings_button_update);
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPasswordEditText.setError(null);
                if (mPasswordEditText.getText().toString().isEmpty()) {
                    mPasswordEditText.setError("Field Required");
                    mPasswordEditText.requestFocus();
                    return;
                }
                // TODO: Implement with Parse
            }
        });
    }

}
