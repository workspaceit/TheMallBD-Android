package com.workspaceit.themallbd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.activity.CategoryActivity;
import com.workspaceit.themallbd.activity.MainActivity;
import com.workspaceit.themallbd.dataModel.Category;
import com.workspaceit.themallbd.dataModel.Products;
import com.workspaceit.themallbd.utility.Utility;

import java.util.List;

/**
 * Created by rajib on 2/23/16.
 */
public class CategoryInGridViewAdapter extends BaseAdapter {

    private List<Category> categoryList;

    private static String productUrl = "category/icon/";
    private CategoryActivity categoryActivity;
    private LayoutInflater layoutInflater;

    public CategoryInGridViewAdapter(CategoryActivity categoryActivity) {
        this.categoryActivity = categoryActivity;
        this.layoutInflater = categoryActivity.getLayoutInflater();
    }
    public class ViewHolder {

        public ImageView imageView;
        public TextView nameTextView;
    }

    @Override
    public int getCount() {
        return CategoryActivity.newCategoryArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return  CategoryActivity.newCategoryArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.categories_card,null);
            viewHolder = new ViewHolder();

            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.category_photo);
            viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.category_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            String icon = CategoryActivity.newCategoryArrayList.get(position).icon;
            int size = icon.length();
            if (size>0) {
                    //    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.mainActivity) .build();
                    //    ImageLoader.getInstance().init(config);
                    ImageLoader.getInstance().displayImage(
                            Utility.IMAGE_URL + productUrl +
                                    CategoryActivity.newCategoryArrayList.get(position).icon,
                            viewHolder.imageView);
                } else {
                    viewHolder.imageView.setImageResource(R.drawable.image_not_found);
                }

            viewHolder.nameTextView.setText(CategoryActivity.newCategoryArrayList.get(position).title);

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return convertView;
    }



}
