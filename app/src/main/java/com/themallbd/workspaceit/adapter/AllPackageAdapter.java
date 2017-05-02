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
import com.themallbd.workspaceit.activity.MainActivity;

import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;



/**
 * Created by Tomal on 7/28/2016.
 */
public class AllPackageAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater layoutInflater;
    private String packageUrl = "package/general/";

    public AllPackageAdapter(Activity activity) {
        this.activity = activity;
        this.layoutInflater = activity.getLayoutInflater();

    }

    @Override
    public int getCount() {
        return MainActivity.packgeProductForHorizontalList.size();
    }

    @Override
    public Object getItem(int position) {
        return MainActivity.packgeProductForHorizontalList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return MainActivity.packgeProductForHorizontalList.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.all_package_single_item, null);
            viewHolder = new ViewHolder();

            viewHolder.productImage = (ImageView) convertView.findViewById(R.id.product_category_imageview);
            viewHolder.productName = (TextView) convertView.findViewById(R.id.product_category_name);
            viewHolder.priceView = (TextView) convertView.findViewById(R.id.product_category_price);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.productImage.setImageBitmap(null);
        }
        try {
            String image = MainActivity.packgeProductForHorizontalList.get(position).image;
            if (image != "") {
                //    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.mainActivity) .build();
                //    ImageLoader.getInstance().init(config);
                ImageLoader.getInstance().displayImage(
                        Utility.IMAGE_URL + packageUrl +
                                MainActivity.packgeProductForHorizontalList.get(position).image,
                        viewHolder.productImage);
            } else {


                BitmapFactory.Options ourOptions = new BitmapFactory.Options();
                ourOptions.inDither = false;
                ourOptions.inPurgeable = true;
                ourOptions.inInputShareable = true;
                ourOptions.inTempStorage = new byte[32 * 1024];
                Bitmap bm = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.image_not_found, ourOptions);
                // viewHolder.productImage.setImageResource(R.drawable.image_not_found);
                viewHolder.productImage.setImageBitmap(bm);
            }
            viewHolder.productName.setText(MainActivity.packgeProductForHorizontalList.get(position).packageTitle);

            viewHolder.priceView.setText(MainActivity.packgeProductForHorizontalList.get(position).packagePriceTotal+" "+Utility.CURRENCY_CODE);

        } catch (Exception e) {
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
