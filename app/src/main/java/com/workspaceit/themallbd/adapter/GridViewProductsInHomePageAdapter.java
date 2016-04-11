package com.workspaceit.themallbd.adapter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.workspaceit.themallbd.activity.MainActivity;
import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.utility.MakeToast;
import com.workspaceit.themallbd.utility.Utility;

/**
 * Created by rajib on 2/15/16.
 */
public class GridViewProductsInHomePageAdapter extends BaseAdapter {

    private MainActivity mainActivity;
    private LayoutInflater layoutInflater;
    private int state;

    private static String productUrl = "product/large/";


    public GridViewProductsInHomePageAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;

        this.layoutInflater = mainActivity.getLayoutInflater();
    }

    public class ViewHolder {

        public ImageView productImage;
        public TextView productName;
        public TextView priceView;

    }

    @Override
    public int getCount() {
        return MainActivity.allProductsForGridViewList.size();
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


        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.custom_grid_view_item,null);
            viewHolder = new ViewHolder();

            viewHolder.productImage = (ImageView) convertView.findViewById(R.id.iv_productImage_gv);
            viewHolder.productName = (TextView) convertView.findViewById(R.id.tv_productName_gv);
            viewHolder.priceView = (TextView) convertView.findViewById(R.id.tv_productPrice_gv);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            int size = MainActivity.allProductsForGridViewList.get(position).pictures.size();
            if (size>=1) {
            //    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.mainActivity) .build();
            //    ImageLoader.getInstance().init(config);
                ImageLoader.getInstance().displayImage(
                        Utility.IMAGE_URL + productUrl +
                                MainActivity.allProductsForGridViewList.get(position).pictures.get(0).name,
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
            viewHolder.productName.setText(MainActivity.allProductsForGridViewList.get(position).title);
            if (MainActivity.allProductsForGridViewList.get(position).prices.size()>0)
                viewHolder.priceView.setText(""+MainActivity.allProductsForGridViewList.get(position).prices.get(0).retailPrice);
            else
                viewHolder.priceView.setText("No price");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        state += 1;
        return convertView;
    }
}
