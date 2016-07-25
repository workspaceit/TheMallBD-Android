package com.themallbd.workspaceit.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.themallbd.workspaceit.activity.SearchProductListActivity;
import com.themallbd.workspaceit.dataModel.MallBdPackageProduct;
import com.themallbd.workspaceit.dataModel.Products;
import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;

import java.util.ArrayList;

/**
 * Created by Tomal on 7/25/2016.
 */
public class ProductsInPackageAdapter extends BaseAdapter {
    private Activity contex;
    private LayoutInflater layoutInflater;
    private static String productUrl = "product/thumbnail/";
    private ArrayList<MallBdPackageProduct>mallBdPackageProducts;

    public ProductsInPackageAdapter(Activity activity, ArrayList<MallBdPackageProduct> productses){
        this.contex=activity;
        this.mallBdPackageProducts.addAll(productses);
        layoutInflater = contex.getLayoutInflater();
    }



    @Override
    public int getCount() {
        return this.mallBdPackageProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mallBdPackageProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.mallBdPackageProducts.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.category_wise_products,null);
            viewHolder = new ViewHolder();

            viewHolder.productImage = (ImageView) convertView.findViewById(R.id.product_category_imageview);
            viewHolder.productName = (TextView) convertView.findViewById(R.id.product_category_name);
            viewHolder.priceView = (TextView) convertView.findViewById(R.id.product_category_price);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            int size = mallBdPackageProducts.get(position).product.pictures.size();
            if (size>=1) {

                ImageLoader.getInstance().displayImage(
                        Utility.IMAGE_URL + productUrl +
                                mallBdPackageProducts.get(position).product.pictures.get(0).name,
                        viewHolder.productImage);
            }
            viewHolder.productName.setText(this.mallBdPackageProducts.get(position).product.title);
            if (mallBdPackageProducts.get(position).product.prices.size()>0)
                viewHolder.priceView.setText(""+mallBdPackageProducts.get(position).price);
            else
                viewHolder.priceView.setText("No price");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return convertView;
    }

    public class ViewHolder {

        public ImageView productImage;
        public TextView productName;
        public TextView priceView;
    }
}
