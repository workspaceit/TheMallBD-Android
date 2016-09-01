package com.themallbd.workspaceit.fragment;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.themallbd.workspaceit.activity.MainActivity;
import com.workspaceit.themall.R;

/**
 * Created by Tomal on 9/1/2016.
 */
public class NavaigationDrawerFragment extends ListFragment {
    String[] month = { "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December" };

    private ViewSwitcher simpleViewSwitcher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListAdapter myListAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, month);
        setListAdapter(myListAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragmnet_navigation_drawer, container, false);
        simpleViewSwitcher=(ViewSwitcher)view.findViewById(R.id.simpleViewSwitcher);

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        Toast.makeText(getActivity(),
                getListView().getItemAtPosition(position).toString(),
                Toast.LENGTH_LONG).show();

       simpleViewSwitcher.showNext();

    }
}
