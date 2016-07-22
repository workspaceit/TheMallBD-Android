package com.themallbd.workspaceit.dataModel;

import java.util.ArrayList;

/**
 * Created by Tomal on 7/22/2016.
 */
public class MallBdPackageCell {
    public int id;
    public int quantity;
    public ArrayList<MallBdPackage> mallBdPackage;

    public MallBdPackageCell(){
        this.id=0;
        this.quantity=0;
        this.mallBdPackage=new ArrayList<>();
    }
}
