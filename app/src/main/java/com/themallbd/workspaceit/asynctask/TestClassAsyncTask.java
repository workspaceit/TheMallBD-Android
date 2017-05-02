package com.themallbd.workspaceit.asynctask;

import android.os.AsyncTask;

import com.themallbd.workspaceit.service.TestService;

/**
 * Created by rajib on 2/11/16.
 */
public class TestClassAsyncTask extends AsyncTask<String,String,Boolean> {


    @Override
    protected Boolean doInBackground(String... params) {

        TestService testService = new TestService();
        boolean resp = testService.testConnection();
        System.out.println(resp);
        return true;
    }
}
