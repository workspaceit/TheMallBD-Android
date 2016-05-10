package com.themallbd.workspaceit.adapter;

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
import com.themallbd.workspaceit.activity.ProductFromCategoryActivity;
import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;

/**
 * Created by rajib on 3/11/16.
 */
public class CategoryWiseProductsAdapter extends BaseAdapter {
    private ProductFromCategoryActivity activity;
    private LayoutInflater layoutInflater;

    private static String productUrl = "product/thumbnail/";

    public CategoryWiseProductsAdapter(ProductFromCategoryActivity activity) {
        this.activity = activity;
        this.layoutInflater = activity.getLayoutInflater();
    }
    public class ViewHolder {

        public ImageView productImage;
        public TextView productName;
        public TextView priceView;
    }

    @Override
    public int getCount() {
        return ProductFromCategoryActivity.categoryWiseProductsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return ProductFromCategoryActivity.categoryWiseProductsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
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
            int size = ProductFromCategoryActivity.categoryWiseProductsArrayList.get(position).pictures.size();
            if (size>=1) {
                //    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.mainActivity) .build();
                //    ImageLoader.getInstance().init(config);
                ImageLoader.getInstance().displayImage(
                        Utility.IMAGE_URL + productUrl +
                                ProductFromCategoryActivity.categoryWiseProductsArrayList.get(position).pictures.get(0).name,
                        viewHolder.productImage);
            } else {
                BitmapFactory.Options ourOptions=new BitmapFactory.Options();
                ourOptions.inDither=false;
                ourOptions.inPurgeable=true;
                ourOptions.inInputShareable=true;
                ourOptions.inTempStorage=new byte[32 * 1024];
                Bitmap bm = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.image_not_found, ourOptions);
                // viewHolder.productImage.setImageResource(R.drawable.image_not_found);
                viewHolder.productImage.setImageBitmap(bm);
            }
            viewHolder.productName.setText(ProductFromCategoryActivity.categoryWiseProductsArrayList.get(position).title);
            if (ProductFromCategoryActivity.categoryWiseProductsArrayList.get(position).prices.size()>0)
                viewHolder.priceView.setText(""+ProductFromCategoryActivity.categoryWiseProductsArrayList.get(position).prices.get(0).retailPrice);
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
}
