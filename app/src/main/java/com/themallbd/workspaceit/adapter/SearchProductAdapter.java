package com.themallbd.workspaceit.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.themallbd.workspaceit.activity.ProductDetailsActivity;
import com.themallbd.workspaceit.utility.MakeToast;
import com.workspaceit.themall.R;
import com.themallbd.workspaceit.utility.Utility;

import java.util.ArrayList;


public class SearchProductAdapter extends ArrayAdapter<String> {
    private LayoutInflater layoutInflater;
    private Context context;
    public ArrayList<String> originalSearchResults;
    public ArrayList<String>filterSearchResult;
    private int viewResourceId;
    private String productUrl = "/product/general/";

    private ArrayList<String> productTitle;


    public SearchProductAdapter(Context context, int resourceID,ArrayList<String>productTitle){
        super(context,resourceID,productTitle);


        this.context=context;
        this.productTitle = productTitle;
        this.layoutInflater=LayoutInflater.from(getContext());


    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;



        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.search_product_row,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.productTitle=(TextView)convertView.findViewById(R.id.search_product_text_view);
            viewHolder.searchImage=(ImageView)convertView.findViewById(R.id.search_image);
            convertView.setTag(viewHolder);

        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }

        if(Utility.searchProductTitle.size()>0) {
            viewHolder.productTitle.setText(Utility.searchResults.get(position).product_title);
            ImageLoader.getInstance().displayImage(Utility.IMAGE_URL+productUrl+Utility.searchResults.get(position).image_name,
                    viewHolder.searchImage);


        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ProductDetailsActivity.class);
                intent.putExtra("productId", Integer.parseInt(Utility.searchResults.get(position).product_id));
                intent.putExtra("productArray", 7);
                context.startActivity(intent);
            }
        });



        return convertView;
    }




    public class ViewHolder{
        public TextView productTitle;
        public ImageView searchImage;
    }




}
