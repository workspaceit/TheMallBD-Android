package com.themallbd.workspaceit.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;

/**
 * Created by Tomal on 9/8/2016.
 */
public class AllManufracturerAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater layoutInflater;
    private String manufracturerImageUrl="manufacturer/";

    public AllManufracturerAdapter(Activity activity) {
        this.activity = activity;
        this.layoutInflater = this.activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return Utility.manufacturers.size();
    }

    @Override
    public Object getItem(int position) {
        return Utility.manufacturers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.single_manufracturer_view, null);
            viewHolder = new ViewHolder();

            viewHolder.manufracturerImageView = (ImageView) convertView.findViewById(R.id.manufracturer_image_view);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
           
        }
        try {


            ImageLoader.getInstance().displayImage(Utility.IMAGE_URL + manufracturerImageUrl + Utility.manufacturers.get(position).icon,
                    viewHolder.manufracturerImageView);


        } catch (Exception e) {

            e.printStackTrace();
        }
        return convertView;
    }

    private class ViewHolder {

        public ImageView manufracturerImageView;


    }
}
