package com.themallbd.workspaceit.dataModel;

/**
 * Created by Tomal on 7/22/2016.
 */
public class MallBdPackageProduct {

    public int id;
    public int packageId;
    public Products product;
    public int quantity;
    public float price;
    public String createdOn;

    public MallBdPackageProduct(){
        this.id=0;
        this.packageId=0;
        this.product=new Products();
        this.quantity=0;
        this.price=0;
        this.createdOn="";
    }

}
