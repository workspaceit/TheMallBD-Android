package com.themallbd.workspaceit.utility;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Mausum on 4/1/2016.
 */
public class MakeToast {


    public MakeToast(Context context,String toast){
        Toast.makeText(context,toast,Toast.LENGTH_SHORT).show();

    }

    public static void showToast(Context context,String toast){
        Toast.makeText(context,toast,Toast.LENGTH_SHORT).show();

    }

    public static void snackBarShow(View view, String msg){
        final Snackbar snackBar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);

        snackBar.setAction("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackBar.dismiss();
            }
        });
        snackBar.show();
    }

}
