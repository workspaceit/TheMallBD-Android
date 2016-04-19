package com.workspaceit.themallbd.utility;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.workspaceit.themallbd.activity.MainActivity;
import com.workspaceit.themallbd.adapter.SearchProductAdapter;
import com.workspaceit.themallbd.asynctask.ProductSearchAsynTask;

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
        Log.v("taiful", s.toString());
        new ProductSearchAsynTask(activity).execute(s.toString(),String.valueOf(6),String.valueOf(0));

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
