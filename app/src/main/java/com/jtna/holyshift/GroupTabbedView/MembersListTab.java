package com.jtna.holyshift.GroupTabbedView;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jtna.holyshift.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MembersListTab#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class MembersListTab extends Fragment {
    private static final String ARG_GROUPNAME = "groupName";
    private ListView mMembersListView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MembersListTab.
     */
    public static MembersListTab newInstance() {
        MembersListTab fragment = new MembersListTab();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MembersListTab() {
        // TODO: Query for members list/use firebase list adapter jank
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_members_list_tab, container, false);
        initializeComponents(rootView);
        return rootView;
    }

    private void initializeComponents(View root) {
        mMembersListView = (ListView) root.findViewById(R.id.group_listview_members);

        List<String> list = new ArrayList<>();
        list.add("1");

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_list_item_1, list);
        mMembersListView.setAdapter(adapter);

    }


}
