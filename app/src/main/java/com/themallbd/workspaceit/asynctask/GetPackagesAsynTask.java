package com.themallbd.workspaceit.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.themallbd.workspaceit.activity.AllPacakgeActivity;
import com.themallbd.workspaceit.activity.MainActivity;
import com.themallbd.workspaceit.adapter.AllPackageAdapter;
import com.themallbd.workspaceit.dataModel.MallBdPackage;
import com.themallbd.workspaceit.service.PackageService;
import com.themallbd.workspaceit.utility.Utility;

import java.util.ArrayList;

/**
 * Created by Tomal on 7/25/2016.
 */
public class GetPackagesAsynTask extends AsyncTask<String,String,ArrayList<MallBdPackage>> {
    private Context context;

    public GetPackagesAsynTask(Context context){
        this.context=context;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }



    @Override
    protected ArrayList<MallBdPackage> doInBackground(String... params) {
        String limit=params[0];
        String offset=params[1];

        return new PackageService().getAllPackages(limit,offset);
    }

    @Override
    protected void onPostExecute(ArrayList <MallBdPackage> mallBdPackages) {
        super.onPostExecute(mallBdPackages);
        if (Utility.responseStat.status){
            if(context instanceof MainActivity){
                if (MainActivity.packgeProductForHorizontalList.size()!=0) {
                    MainActivity.packgeProductForHorizontalList.remove(MainActivity.packgeProductForHorizontalList.size() - 1);
                }
                MainActivity.packgeProductForHorizontalList.addAll(mallBdPackages);
                ((MainActivity)context).notifyPackageDataSet();
            }if (context instanceof AllPacakgeActivity){
                MainActivity.packgeProductForHorizontalList.addAll(mallBdPackages);
                ((AllPacakgeActivity)context).notifyDataSetChange();
            }


        }else {
            if(context instanceof MainActivity){
                if (MainActivity.packgeProductForHorizontalList.size()!=0) {
                    MainActivity.packgeProductForHorizontalList.remove(MainActivity.packgeProductForHorizontalList.size() - 1);
                }
                ((MainActivity)context).setPackageListError();
            }else if(context instanceof AllPacakgeActivity){
                ((AllPacakgeActivity)context).newProductError();
            }

    }
    }
}
