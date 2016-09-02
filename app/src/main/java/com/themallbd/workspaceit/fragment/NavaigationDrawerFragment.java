package com.themallbd.workspaceit.fragment;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.themallbd.workspaceit.activity.MainActivity;
import com.themallbd.workspaceit.activity.ProductFromCategoryActivity;
import com.themallbd.workspaceit.adapter.NavgationDrawerParentCategoryAdapter;
import com.themallbd.workspaceit.utility.MakeToast;
import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;

import java.util.ArrayList;

/**
 * Created by Tomal on 9/1/2016.
 */
public class NavaigationDrawerFragment extends Fragment implements AdapterView.OnItemClickListener,View.OnClickListener {
    String[] month = { "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December" };



    private ListView parentListView;
    private ImageButton backButton;

    private ViewSwitcher simpleViewSwitcher;
    private NavgationDrawerParentCategoryAdapter navgationDrawerParentCategoryAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragmnet_navigation_drawer, container, false);
        simpleViewSwitcher=(ViewSwitcher)view.findViewById(R.id.simpleViewSwitcher);
        parentListView=(ListView)view.findViewById(R.id.parent_categoey_list_view);
        parentListView.setOnItemClickListener(this);
        backButton=(ImageButton)view.findViewById(R.id.back_button_nav_fragment);
        backButton.setOnClickListener(this);



        return view;
    }



    @Override
    public void onResume() {
        super.onResume();

    }

    public void notifyDataSet(){
        navgationDrawerParentCategoryAdapter=new NavgationDrawerParentCategoryAdapter(getActivity());
        parentListView.setAdapter(navgationDrawerParentCategoryAdapter);
        navgationDrawerParentCategoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (parent==parentListView) {
            if (Utility.parentsCategoryArraylist.get(position).childrens.isEmpty()) {
                Intent intent = new Intent(getActivity(), ProductFromCategoryActivity.class);
                intent.putExtra("category_id", Utility.parentsCategoryArraylist.get(position).id);
                getActivity().startActivity(intent);
            } else {
                simpleViewSwitcher.showNext();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v==backButton){
            simpleViewSwitcher.setDisplayedChild(0);
        }
    }
}
