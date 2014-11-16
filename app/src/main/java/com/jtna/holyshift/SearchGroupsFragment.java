package com.jtna.holyshift;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchGroupsFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class SearchGroupsFragment extends Fragment {

    private EditText mSearchEditText;
    private ListView mGroupsListView;
    private List<String> mGroups;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchGroupsFragment.
     */
    public static SearchGroupsFragment newInstance() {
        SearchGroupsFragment fragment = new SearchGroupsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public SearchGroupsFragment() {
        // Required empty public constructor
        //TODO: get list of groups
        mGroups = new ArrayList<String>();
        mGroups.add("temp1");
        mGroups.add("temp2");
        mGroups.add("temp3");
        mGroups.add("apple");
        mGroups.add("applepie");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initializeComponents(View rootView) {
        mSearchEditText = (EditText) rootView.findViewById(R.id.search_edittext_groupquery);
        mGroupsListView = (ListView) rootView.findViewById(R.id.search_listview_groupslist);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, mGroups);
        mGroupsListView.setAdapter(adapter);
        mGroupsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "yay " + mGroupsListView.getAdapter().getItem(position), Toast.LENGTH_SHORT).show(); ;
            }
        });
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s.toString());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_groups, container, false);
        initializeComponents(rootView);
        return rootView;
    }

}
