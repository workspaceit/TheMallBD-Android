package com.themallbd.workspaceit.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.themallbd.workspaceit.activity.AllFeaturesProductActivity;
import com.themallbd.workspaceit.activity.MainActivity;
import com.themallbd.workspaceit.dataModel.Products;
import com.themallbd.workspaceit.service.ProductService;
import com.themallbd.workspaceit.utility.Utility;

import java.util.ArrayList;

/**
 * Created by rajib on 2/15/16.
 */
public class GetFeaturedProductsAsyncTask extends AsyncTask<String,String,ArrayList<Products>> {

    private Context mContext;
    private ProgressDialog mProgressDialog;
    private boolean progressFlag;

    public GetFeaturedProductsAsyncTask(Context mContext,boolean progressFlag) {
        this.mContext = mContext;
        this.progressFlag=progressFlag;
    }


    @Override
    protected void onPreExecute() {
        if (progressFlag) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setMessage("Loading Mall BD. Please Wait..");
            mProgressDialog.show();
            mProgressDialog.setCancelable(false);
        }
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Products> doInBackground(String... params) {

        String offset = params[0];
        String limit = params[1];

        return new ProductService().getFeaturedProducts(offset,limit);
    }

    @Override
    protected void onPostExecute(ArrayList<Products> productses) {
        super.onPostExecute(productses);
        try {
            if (progressFlag && mProgressDialog!=null && mProgressDialog.isShowing()) {
                mProgressDialog.cancel();
            }
        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        if (Utility.responseStat.status) {
            if(mContext instanceof MainActivity){
                if (mContext instanceof MainActivity) {
                    if (MainActivity.featuredProductsForHorizontalViewList.size() != 0) {
                        MainActivity.featuredProductsForHorizontalViewList.remove(MainActivity.featuredProductsForHorizontalViewList.size() - 1);
                    }

                    MainActivity.featuredProductsForHorizontalViewList.addAll(productses);
                    ((MainActivity)mContext).setFeaturedProductsList();
                }
            }else if(mContext instanceof AllFeaturesProductActivity){
                MainActivity.featuredProductsForHorizontalViewList.addAll(productses);
                        ((AllFeaturesProductActivity) mContext).notifyDataSetChange();
            }

        } else {
            if(mContext instanceof MainActivity){
                if (MainActivity.featuredProductsForHorizontalViewList.size()!=0) {
                    MainActivity.featuredProductsForHorizontalViewList.remove(MainActivity.featuredProductsForHorizontalViewList.size() - 1);
                }

                ((MainActivity)mContext).setFeaturedProductListError();
            }else if (mContext instanceof AllFeaturesProductActivity){
                ((AllFeaturesProductActivity)mContext).featureProductError();
            }



        }
    }
}
