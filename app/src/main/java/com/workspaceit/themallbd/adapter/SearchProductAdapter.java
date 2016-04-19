package com.workspaceit.themallbd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.activity.WishListActivity;
import com.workspaceit.themallbd.utility.Utility;


public class SearchProductAdapter extends BaseAdapter implements Filterable {
    private LayoutInflater layoutInflater;
    private Activity context;

    public SearchProductAdapter(Activity activity){
        this.context=activity;
        this.layoutInflater=context.getLayoutInflater();
    }
    @Override
    public int getCount() {
        return Utility.searchProductArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return Utility.searchProductArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Utility.searchProductArrayList.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.search_product_row,null);
            viewHolder=new ViewHolder();
            viewHolder.productTitle=(TextView)convertView.findViewById(R.id.search_product_text_view);

        }

        viewHolder.productTitle.setText(Utility.searchProductArrayList.get(position).title);

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return null;
    }


    public class ViewHolder{
        public TextView productTitle;
    }


    public class MyFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            return null;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    }
}
