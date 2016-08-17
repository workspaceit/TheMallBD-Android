package com.themallbd.workspaceit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.workspaceit.themall.R;
import com.themallbd.workspaceit.activity.CategoryListViewActivity;
import com.themallbd.workspaceit.dataModel.Category;
import com.themallbd.workspaceit.utility.Utility;

import java.util.ArrayList;

/**
 * Created by rajib on 2/26/16.
 */
public class CategoryInListViewAdapter extends BaseAdapter {

    
    private static String productUrl = "category/banner/";

    private CategoryListViewActivity categoryListViewActivity;
    private ArrayList<Category> categoryArrayList;
    private LayoutInflater layoutInflater;

    public CategoryInListViewAdapter(CategoryListViewActivity activity,ArrayList<Category> categories) {

        this.categoryListViewActivity = activity;
        this.categoryArrayList = categories;
        layoutInflater = this.categoryListViewActivity.getLayoutInflater();
    }
    public class ViewHolder {

        public ImageView categoryBannerImage;
        public TextView categoryName;

    }

    @Override
    public int getCount() {
        return this.categoryArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.categoryArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.categoryArrayList.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.category_parents_item,null);
            viewHolder = new ViewHolder();

            viewHolder.categoryBannerImage = (ImageView) convertView.findViewById(R.id.category_banner_image);
            viewHolder.categoryName = (TextView) convertView.findViewById(R.id.category_parent_text);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        try {
            String icon = this.categoryArrayList.get(position).banner;
            int size = icon!=null? icon.length(): 0;
            if (size>0) {
                //    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.mainActivity) .build();
                //    ImageLoader.getInstance().init(config);
                ImageLoader.getInstance().displayImage(
                        Utility.IMAGE_URL + productUrl +
                                this.categoryArrayList.get(position).banner,
                        viewHolder.categoryBannerImage);
            } else {
                viewHolder.categoryBannerImage.setImageResource(R.drawable.image_not_found);
            }

            viewHolder.categoryName.setText(this.categoryArrayList.get(position).title);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return convertView;
    }
}
