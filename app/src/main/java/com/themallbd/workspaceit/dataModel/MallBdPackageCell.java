package com.themallbd.workspaceit.dataModel;

import java.util.ArrayList;

/**
 * Created by Tomal on 7/22/2016.
 */
public class MallBdPackageCell {
    public int id;
    public int quantity;
    public MallBdPackage mallBdPackage;

    public MallBdPackageCell(){
        this.id=0;
        this.quantity=0;
        this.mallBdPackage=new MallBdPackage();
    }


    public void setId(int id){
        this.id=id;

    }

    public void setQuantity(int quantity){
        this.quantity=quantity;
    }

    public void setMallBdPackage(MallBdPackage mallBdPackage){
        this.mallBdPackage=mallBdPackage;
    }
}
