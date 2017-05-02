package com.themallbd.workspaceit.dataModel;

import java.util.ArrayList;

/**
 * Created by Tomal on 8/4/2016.
 */
public class OrderProducts {
    public int id;
    public int productQuantity;
    public int packageQuantity;
    public float price;
    public float total;
    public float tax;
    public float discount;
    public int productId;
    public int packageId;
    public ArrayList<SelectedAttributes> selectedProductAttribute;
    public String itemType;
    public Products productitem;
    public MallBdPackage packageitem;

    public OrderProducts(){
        this.id=0;
        this.productQuantity=0;
        this.packageQuantity=0;
        this.price=0;
        this.total=0;
        this.tax=0;
        this.discount=0;
        this.productId=0;
        this.packageId=0;
        this.selectedProductAttribute=new ArrayList<>();
        this.itemType="";
        this.productitem=new Products();
        this.packageitem=new MallBdPackage();
    }
}
