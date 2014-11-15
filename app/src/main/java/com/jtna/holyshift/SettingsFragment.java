package com.jtna.holyshift;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class SettingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USERNAME = "username";

    private ImageView mProfPicImage;
    private TextView mUsernameTextView;
    private Switch mNotificationsSwitch;

    private String mUsername;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param username Username.
     * @return A new instance of fragment SettingsFragment.
     */
    public static SettingsFragment newInstance(String username) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        fragment.setArguments(args);
        return fragment;
    }
    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUsername = getArguments().getString(ARG_USERNAME);
        }
    }

    private void initializeComponents(View root) {
        //TODO: user firebase to get database info for app
        mProfPicImage = (ImageView) root.findViewById(R.id.settings_image_profile);
        mUsernameTextView = (TextView) root.findViewById(R.id.settings_textview_username);
        mNotificationsSwitch = (Switch) root.findViewById(R.id.settings_switch_notifications);

        mUsernameTextView.setText("username");
        mNotificationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mUsernameTextView.setText("username " + isChecked);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        initializeComponents(rootView);
        return rootView;
    }

}
