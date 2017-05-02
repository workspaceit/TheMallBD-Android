package com.themallbd.workspaceit.dataModel;

import java.util.ArrayList;

/**
 * Created by Tomal on 7/22/2016.
 */
public class MallBdPackage {
    public int id;
    public String  packageTitle;
    public String  description;
    public String startDate;
    public String endDate;
    public String image;
    public float originalPriceTotal;
    public float packagePriceTotal;
    public String status;
    public String createdOn;
    public ArrayList<MallBdPackageProduct> packageProduct;

    public MallBdPackage(){
        this.id=0;
        this.packageTitle="";
        this.description="";
        this.startDate="";
        this.endDate="";
        this.image="";
        this.originalPriceTotal=0;
        this.packagePriceTotal=0;
        this.status="";
        this.createdOn="";
        this.packageProduct=new ArrayList<>();

    }
}
