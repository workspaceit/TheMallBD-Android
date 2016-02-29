package com.workspaceit.themallbd.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.activity.CategoryInExpandableListViewActivity;
import com.workspaceit.themallbd.dataModel.Category;

import java.security.acl.Group;
import java.util.ArrayList;

/**
 * Created by rajib on 2/29/16.
 */
public class CategoryInExpandableListViewAdapter extends BaseExpandableListAdapter {

    private final ArrayList<Category> parentsWithChildrensList;

    public LayoutInflater layoutInflater;
    public CategoryInExpandableListViewActivity activity;

    public CategoryInExpandableListViewAdapter(CategoryInExpandableListViewActivity activity, ArrayList<Category> parentsWithChildrensList) {
        this.activity = activity;
        this.parentsWithChildrensList = parentsWithChildrensList;
        layoutInflater = activity.getLayoutInflater();
    }

    public class ViewHolder {

        public TextView nameTextView;
    }

    public class ChildViewHolder{
        public TextView categoryTitle;
    }

    @Override
    public int getGroupCount() {
        return parentsWithChildrensList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
       return parentsWithChildrensList.get(groupPosition).childrens.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parentsWithChildrensList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return parentsWithChildrensList.get(groupPosition).childrens.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return parentsWithChildrensList.get(groupPosition).childrens.get(childPosition).id;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.category_parents_group, null);
            viewHolder = new ViewHolder();

            viewHolder.nameTextView = (TextView) convertView
                    .findViewById(R.id.lblListHeader);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Category category = (Category) getGroup(groupPosition);
        viewHolder.nameTextView.setText(category.title);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
         Category children = (Category) getChild(groupPosition, childPosition);
        ChildViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.category_child_items_in_group, null);
            viewHolder = new ChildViewHolder();

            viewHolder.categoryTitle = (TextView) convertView
                    .findViewById(R.id.lblListItem);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ChildViewHolder) convertView.getTag();
        }

        viewHolder.categoryTitle.setText(children.title);

       /* if(children.childrens.size() > 0 ) {
           // ExpandableListView elv = new ExpandableListView(this.activity);
         //   elv.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT,  AbsListView.LayoutParams.WRAP_CONTENT));
        //    elv.setAdapter(new CatAdapter(this, children.childrens));
            TextView textView = new TextView(this.activity);
            textView.setText("adas");
                    ((ViewGroup) convertView).addView(textView);
        }*/

        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
