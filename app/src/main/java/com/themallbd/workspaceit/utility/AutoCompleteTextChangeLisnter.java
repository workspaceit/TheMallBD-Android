package com.themallbd.workspaceit.utility;

import android.text.Editable;
import android.text.TextWatcher;

import com.themallbd.workspaceit.activity.MainActivity;
import com.themallbd.workspaceit.asynctask.ProductSearchAsynTask;

/**
 * Created by Mausum on 4/19/2016.
 */
public class AutoCompleteTextChangeLisnter implements TextWatcher {
    private MainActivity activity;


    public AutoCompleteTextChangeLisnter(MainActivity activity){
        this.activity=activity;

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(count>2) {
            if (!s.toString().equals("")) {

                Utility.searchProductTitle.clear();
                new ProductSearchAsynTask(activity).execute(s.toString());
            }
        }

    }

    @Override
    public void afterTextChanged(Editable s) {


    }
}
