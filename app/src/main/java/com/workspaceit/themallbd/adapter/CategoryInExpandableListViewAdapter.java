package com.workspaceit.themallbd.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.activity.CategoryInExpandableListViewActivity;
import com.workspaceit.themallbd.activity.ProductFromCategoryActivity;
import com.workspaceit.themallbd.dataModel.Category;
import com.workspaceit.themallbd.utility.MakeToast;

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
        ViewHolder viewHolder;
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
        SecondLevelExpandableListView secondLevelELV = new SecondLevelExpandableListView(activity);
        secondLevelELV.setAdapter(new SecondLevelAdapter(activity,children));
        secondLevelELV.setGroupIndicator(null);
        return secondLevelELV;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public class SecondLevelExpandableListView extends ExpandableListView {

        public SecondLevelExpandableListView(Context context) {
            super(context);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            //999999 is a size in pixels. ExpandableListView requires a maximum height in order to do measurement calculations.
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(999999, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public class SecondLevelAdapter extends BaseExpandableListAdapter {

        private Context context;
        private Category childrens;

        public SecondLevelAdapter(Context context,Category children) {
            this.context = context;
            this.childrens = children;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groupPosition;
        }

        @Override
        public int getGroupCount() {
            return 1;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            TextView  text;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.category_child_items_in_group, null);
                text = (TextView) convertView.findViewById(R.id.lblListItem);
                text.setText(childrens.title);
            }
            return convertView;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childrens.childrens.get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childrens.childrens.get(childPosition).id;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            TextView  text = null;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.category_child_of_child_items, null);
                text = (TextView) convertView.findViewById(R.id.child_of_child);
                text.setText(childrens.childrens.get(childPosition).title);
            }
            if (text != null) {
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(activity, ProductFromCategoryActivity.class);
                        intent.putExtra("category_id",childrens.childrens.get(childPosition).id);
                        activity.startActivity(intent);
                    }
                });
            }
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return childrens.childrens.size();
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }


}
