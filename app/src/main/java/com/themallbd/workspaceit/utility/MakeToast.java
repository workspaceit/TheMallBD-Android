package com.themallbd.workspaceit.utility;

import android.content.Context;
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



}
