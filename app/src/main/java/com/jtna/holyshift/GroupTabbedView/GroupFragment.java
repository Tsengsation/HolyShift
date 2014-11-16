package com.jtna.holyshift.GroupTabbedView;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jtna.holyshift.CalendarFragment;
import com.jtna.holyshift.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class GroupFragment extends Fragment {
    private static final String ARG_GROUPNAME= "groupName";

    private String mGroupName;

    private FragmentTabHost mTabHost;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param groupName name of the group.
     * @return A new instance of fragment GroupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupFragment newInstance(String groupName) {
        GroupFragment fragment = new GroupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_GROUPNAME, groupName);
        fragment.setArguments(args);
        return fragment;
    }
    public GroupFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_group, container, false);
        initializeComponents(rootView);
        return rootView;
    }

    private void initializeComponents(View root) {
        mTabHost = (FragmentTabHost) root.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.tabcontent);
        mTabHost.addTab(mTabHost.newTabSpec(getString(R.string.group_members)).setIndicator(getString(R.string.group_members)), MembersListTab.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(getString(R.string.group_settings)).setIndicator(getString(R.string.group_settings)), GroupSettingsTab.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("what").setIndicator("what"), CalendarFragment.class, null);
    }

}
