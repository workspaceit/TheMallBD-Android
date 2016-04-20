package com.workspaceit.themallbd.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.dataModel.SearchResult;
import com.workspaceit.themallbd.utility.MakeToast;
import com.workspaceit.themallbd.utility.Utility;

import java.util.ArrayList;
import java.util.List;


public class SearchProductAdapter extends ArrayAdapter<String> {
    private LayoutInflater layoutInflater;
    private Context context;
    public ArrayList<String> originalSearchResults;
    public ArrayList<String>filterSearchResult;
    private int viewResourceId;

    private ArrayList<String> productTitle;


    public SearchProductAdapter(Context context, int resourceID,ArrayList<String>productTitle){
        super(context,resourceID,productTitle);


        this.context=context;
        this.productTitle = productTitle;
        this.layoutInflater=LayoutInflater.from(getContext());


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;



        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.search_product_row,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.productTitle=(TextView)convertView.findViewById(R.id.search_product_text_view);
            convertView.setTag(viewHolder);

        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }

        if(Utility.searchProductTitle.size()>0) {
            viewHolder.productTitle.setText(productTitle.get(position));
        }

        return convertView;
    }




    public class ViewHolder{
        public TextView productTitle;
    }




}
