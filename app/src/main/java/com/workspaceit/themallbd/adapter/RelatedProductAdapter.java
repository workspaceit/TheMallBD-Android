package com.workspaceit.themallbd.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.workspaceit.themallbd.activity.BaseActivityWithoutDrawer;
import com.workspaceit.themallbd.activity.ProductDetailsActivity;

/**
 * Created by Mausum on 4/22/2016.
 */
public class RelatedProductAdapter extends BaseAdapter {

    ProductDetailsActivity contex;

    public RelatedProductAdapter(ProductDetailsActivity activity){
        this.contex=activity;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
