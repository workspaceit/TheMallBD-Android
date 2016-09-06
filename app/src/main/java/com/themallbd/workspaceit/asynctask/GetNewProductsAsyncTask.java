package com.themallbd.workspaceit.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.themallbd.workspaceit.activity.AllNewProductActivity;
import com.themallbd.workspaceit.activity.MainActivity;
import com.themallbd.workspaceit.dataModel.Products;
import com.themallbd.workspaceit.service.ProductService;
import com.themallbd.workspaceit.utility.Utility;

import java.util.ArrayList;

/**
 * Created by rajib on 2/15/16.
 */
public class GetNewProductsAsyncTask extends AsyncTask<String, String, ArrayList<Products>> {

    private Context mContext;

    //private ProgressDialog mProgressDialog;

    public GetNewProductsAsyncTask(Context mContext) {
        this.mContext = mContext;

    }


    @Override
    protected void onPreExecute() {

/*        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Loading Mall BD. Please Wait..");
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);*/
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Products> doInBackground(String... params) {

        String offset = params[0];
        String limit = params[1];
        ProductService productService = new ProductService();
        return productService.getNewProducts(offset, limit);
    }

    @Override
    protected void onPostExecute(ArrayList <Products> productses) {
        super.onPostExecute(productses);
        //mProgressDialog.dismiss();
        if (Utility.responseStat.status) {
            if (mContext instanceof MainActivity) {
                if (MainActivity.newProductsForHorizontalViewList.size()!=0) {
                    MainActivity.newProductsForHorizontalViewList.remove(MainActivity.newProductsForHorizontalViewList.size() - 1);
                }
                MainActivity.newProductsForHorizontalViewList.addAll(productses);

                ((MainActivity) mContext).setNewProductsList();

            } else if (mContext instanceof AllNewProductActivity) {
                 MainActivity.newProductsForHorizontalViewList.addAll(productses);
                ((AllNewProductActivity) mContext).notifyDataSetChange();

            }
        } else {

            if (mContext instanceof MainActivity) {
                if (MainActivity.newProductsForHorizontalViewList.size()!=0) {
                    MainActivity.newProductsForHorizontalViewList.remove(MainActivity.newProductsForHorizontalViewList.size() - 1);
                }
                ((MainActivity) mContext).setNewProductListError();

            } else if (mContext instanceof AllNewProductActivity) {
                ((AllNewProductActivity) mContext).newProductError();
            }

        }
    }
}
