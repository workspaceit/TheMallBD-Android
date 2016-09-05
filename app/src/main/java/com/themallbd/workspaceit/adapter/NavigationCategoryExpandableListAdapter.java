package com.themallbd.workspaceit.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.themallbd.workspaceit.activity.ProductFromCategoryActivity;
import com.themallbd.workspaceit.dataModel.Category;
import com.themallbd.workspaceit.view.AnimatedExpandableListView;
import com.themallbd.workspaceit.view.CustomFontTextView;
import com.workspaceit.themall.R;

import java.util.ArrayList;

/**
 * Created by Tomal on 9/2/2016.
 */
public class NavigationCategoryExpandableListAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    private final ArrayList<Category> secondLevelCategoryList;
    private LayoutInflater layoutInflater;
    private Activity activity;
    private static final int[] EMPTY_STATE_SET = {};
    private static final int[] GROUP_EXPANDED_STATE_SET =
            {android.R.attr.state_expanded};
    private static final int[][] GROUP_STATE_SETS = {
            EMPTY_STATE_SET, // 0
            GROUP_EXPANDED_STATE_SET // 1
    };

    public NavigationCategoryExpandableListAdapter(Activity activity,ArrayList<Category> secondLevelCategoryList) {
        this.secondLevelCategoryList = secondLevelCategoryList;
        this.activity=activity;
        this.layoutInflater=this.activity.getLayoutInflater();

    }

    private class ViewHolder {

        public CustomFontTextView nameTextView;
        public TextView indicatorTextView;
    }

    @Override
    public int getGroupCount() {
        return secondLevelCategoryList.size();
    }



    @Override
    public Object getGroup(int groupPosition) {
        return secondLevelCategoryList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return secondLevelCategoryList.get(groupPosition).childrens.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.nav_fragment_category_second_child_view, null);
            viewHolder = new ViewHolder();

            viewHolder.nameTextView = (CustomFontTextView) convertView
                    .findViewById(R.id.lblListItem);
            viewHolder.indicatorTextView=(TextView)convertView.findViewById(R.id.indicator_second_level);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.nameTextView.setText(secondLevelCategoryList.get(groupPosition).title);
        if (secondLevelCategoryList.get(groupPosition).childrens.isEmpty()){
            viewHolder.indicatorTextView.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.indicatorTextView.setVisibility(View.VISIBLE);
        }

        if (isExpanded){
            viewHolder.nameTextView.setTypeface(null, Typeface.BOLD);
            viewHolder.indicatorTextView.setText("-");

        }else {
            viewHolder.nameTextView.setTypeface(null,Typeface.NORMAL);
            viewHolder.indicatorTextView.setText("+");
        }

        return convertView;
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ThirdLevelViewHolder thirdLevelViewHolder;
        final Category category=secondLevelCategoryList.get(groupPosition).childrens.get(childPosition);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.nav_fragment_category_third_level_child, null);
            thirdLevelViewHolder=new ThirdLevelViewHolder();
            thirdLevelViewHolder.thirdLevelTextView=(CustomFontTextView) convertView.findViewById(R.id.child_of_child);
            convertView.setTag(thirdLevelViewHolder);
        }else {
            thirdLevelViewHolder=(ThirdLevelViewHolder)convertView.getTag();
        }

        thirdLevelViewHolder.thirdLevelTextView.setText(category.title);

        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return secondLevelCategoryList.get(groupPosition).childrens.size();
    }



    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class ThirdLevelViewHolder{
        CustomFontTextView thirdLevelTextView;
    }
}
