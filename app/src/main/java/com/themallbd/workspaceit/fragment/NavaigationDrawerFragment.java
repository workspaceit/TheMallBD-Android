package com.themallbd.workspaceit.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.themallbd.workspaceit.activity.ProductFromCategoryActivity;
import com.themallbd.workspaceit.adapter.NavgationDrawerParentCategoryAdapter;
import com.themallbd.workspaceit.adapter.NavigationCategoryExpandableListAdapter;
import com.themallbd.workspaceit.preferences.LocalCategoryList;
import com.themallbd.workspaceit.utility.Utility;
import com.themallbd.workspaceit.view.AnimatedExpandableListView;
import com.workspaceit.themall.R;

/**
 * Created by Tomal on 9/1/2016.
 */
public class NavaigationDrawerFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener,
        ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener {


    private ListView parentListView;
    private ImageButton backButton;
    private AnimatedExpandableListView expandableListView;
    private int categoryPosition;
    private TextView categoryTitle;

    private ViewSwitcher simpleViewSwitcher;
    private NavgationDrawerParentCategoryAdapter navgationDrawerParentCategoryAdapter;
    private LocalCategoryList localCategoryList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmnet_navigation_drawer, container, false);
        simpleViewSwitcher = (ViewSwitcher) view.findViewById(R.id.simpleViewSwitcher);
        parentListView = (ListView) view.findViewById(R.id.parent_categoey_list_view);
        parentListView.setOnItemClickListener(this);
        backButton = (ImageButton) view.findViewById(R.id.back_button_nav_fragment);
        expandableListView = (AnimatedExpandableListView) view.findViewById(R.id.expandable_list_view_nav_fragment);
        expandableListView.setOnGroupClickListener(this);
        expandableListView.setOnChildClickListener(this);
        backButton.setOnClickListener(this);
        this.categoryTitle = (TextView) view.findViewById(R.id.nav_category_second_page_title);
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Whitney-Semibold-Bas.otf");
        this.categoryTitle.setTypeface(face);
        localCategoryList = new LocalCategoryList(getActivity());
        navgationDrawerParentCategoryAdapter = new NavgationDrawerParentCategoryAdapter(getActivity());
        parentListView.setAdapter(navgationDrawerParentCategoryAdapter);


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    public void notifyDataSet() {

        navgationDrawerParentCategoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (parent == parentListView) {
            if (Utility.parentsCategoryArraylist.get(position).childrens.isEmpty()) {
                Intent intent = new Intent(getActivity(), ProductFromCategoryActivity.class);
                intent.putExtra("category_id", Utility.parentsCategoryArraylist.get(position).id);
                getActivity().startActivity(intent);
            } else {
                NavigationCategoryExpandableListAdapter navigationCategoryExpandableListAdapter = new
                        NavigationCategoryExpandableListAdapter(getActivity(), Utility.parentsCategoryArraylist.get(position).childrens);
                this.categoryPosition = position;
                expandableListView.setAdapter(navigationCategoryExpandableListAdapter);
                navigationCategoryExpandableListAdapter.notifyDataSetChanged();

                this.categoryTitle.setText(Utility.parentsCategoryArraylist.get(position).title);


                simpleViewSwitcher.setDisplayedChild(1);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == backButton) {
            simpleViewSwitcher.setDisplayedChild(0);

        }
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

        if (Utility.parentsCategoryArraylist.get(this.categoryPosition).childrens.get(groupPosition).childrens.isEmpty()) {
            Intent intent = new Intent(getActivity(), ProductFromCategoryActivity.class);
            intent.putExtra("category_id", Utility.parentsCategoryArraylist.get(this.categoryPosition).childrens.get(groupPosition).id);
            getActivity().startActivity(intent);
        } else {
            if (expandableListView.isGroupExpanded(groupPosition)) {

                expandableListView.collapseGroupWithAnimation(groupPosition);
            } else {
                expandableListView.expandGroupWithAnimation(groupPosition);
            }

        }
        return true;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

        Intent intent = new Intent(getActivity(), ProductFromCategoryActivity.class);
        intent.putExtra("category_id", Utility.parentsCategoryArraylist.get(this.categoryPosition).childrens.get(groupPosition).
                childrens.get(childPosition).id);
        getActivity().startActivity(intent);

        return true;
    }

    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }


}
