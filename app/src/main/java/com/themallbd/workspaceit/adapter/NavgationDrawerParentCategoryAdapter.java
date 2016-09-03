package com.themallbd.workspaceit.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;

/**
 * Created by Tomal on 9/2/2016.
 */
public class NavgationDrawerParentCategoryAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater layoutInflater;

    public NavgationDrawerParentCategoryAdapter(Activity activity){

        this.activity=activity;
        this.layoutInflater=this.activity.getLayoutInflater();


    }


    @Override
    public int getCount() {
        return Utility.parentsCategoryArraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return Utility.parentsCategoryArraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.navgation_fragment_parent_category, null);
            viewHolder = new ViewHolder();

            viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.fragment_parent_category_title_text_view);
            viewHolder.rightArrow=(ImageView)convertView.findViewById(R.id.fragment_arrow_image);
            Typeface face= Typeface.createFromAsset(activity.getAssets(), "fonts/Whitney-Semibold-Bas.otf");
            viewHolder.titleTextView.setTypeface(face);



            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.titleTextView.setText(Utility.parentsCategoryArraylist.get(position).title);
        if (Utility.parentsCategoryArraylist.get(position).childrens.isEmpty()){
            viewHolder.rightArrow.setVisibility(View.GONE);
        }else {
            viewHolder.rightArrow.setVisibility(View.VISIBLE);
        }

        return convertView;
    }


    public class ViewHolder {

        public TextView titleTextView;
        public ImageView rightArrow;

    }
}
