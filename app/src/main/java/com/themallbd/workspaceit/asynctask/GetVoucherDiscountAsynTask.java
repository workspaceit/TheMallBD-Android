package com.themallbd.workspaceit.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.themallbd.workspaceit.activity.CheckoutActivity;
import com.themallbd.workspaceit.dataModel.Voucher;
import com.themallbd.workspaceit.fragment.CheckoutViewFragment;
import com.themallbd.workspaceit.service.GetVoucherCodeServie;
import com.themallbd.workspaceit.utility.MakeToast;

/**
 * Created by Tomal on 8/5/2016.
 */
public class GetVoucherDiscountAsynTask extends AsyncTask<String,String,Voucher>{
    private Context context;
    private ProgressDialog mProgressDialog;
    private CheckoutViewFragment checkoutViewFragment;

    public GetVoucherDiscountAsynTask(Context context,CheckoutViewFragment checkoutViewFragment){
        this.context=context;
        this.checkoutViewFragment=checkoutViewFragment;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(this.context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Validating Your Voucher Code. Please wait...");
        mProgressDialog.show();
    }


    @Override
    protected Voucher doInBackground(String... params) {
        String voucherCode=params[0];
        return new GetVoucherCodeServie().getVoucherCode(voucherCode);
    }

    @Override
    protected void onPostExecute(Voucher voucher) {
        super.onPostExecute(voucher);
        mProgressDialog.dismiss();

        if (voucher==null){
            MakeToast.showToast(context,"Your Voucher Code is not valid..");
        }else {
            if (context instanceof CheckoutActivity){
                checkoutViewFragment.doAllModificationOnValidVoucherCode(voucher);
            }
        }
    }

}
