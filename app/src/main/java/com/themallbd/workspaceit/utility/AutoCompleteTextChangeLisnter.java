package com.themallbd.workspaceit.utility;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.themallbd.workspaceit.activity.MainActivity;
import com.themallbd.workspaceit.asynctask.ProductSearchAsynTask;

/**
 * Created by Mausum on 4/19/2016.
 */
public class AutoCompleteTextChangeLisnter implements TextWatcher {
    private MainActivity activity;
    public static boolean callFlag=true;


    public AutoCompleteTextChangeLisnter(MainActivity activity){
        this.activity=activity;

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {


    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


        System.out.println(s+" "+String.valueOf(callFlag)+" "+count);
        if(count>2 && callFlag) {
            if (!s.toString().equals("")) {
                System.out.println("called");
                callFlag=false;
                Utility.searchProductTitle.clear();
                activity.setSeacrhAdater();
                new ProductSearchAsynTask(activity, (short) 1).execute(s.toString());
            }
        }

    }

    @Override
    public void afterTextChanged(Editable s) {


    }
}
