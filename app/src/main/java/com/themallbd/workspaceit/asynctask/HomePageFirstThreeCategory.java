package com.themallbd.workspaceit.asynctask;

import android.os.AsyncTask;

import com.themallbd.workspaceit.activity.MainActivity;
import com.themallbd.workspaceit.dataModel.Category;

import java.util.ArrayList;

/**
 * Created by Mausum on 4/4/2016.
 */
public class HomePageFirstThreeCategory extends AsyncTask<String,String,ArrayList<Category>> {

    private MainActivity mainContext;

    @Override
    protected void onPostExecute(ArrayList<Category> categories) {
        super.onPostExecute(categories);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Category> doInBackground(String... params) {
        return null;
    }
}
