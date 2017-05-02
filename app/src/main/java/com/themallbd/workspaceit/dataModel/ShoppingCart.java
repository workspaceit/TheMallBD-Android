package com.themallbd.workspaceit.dataModel;

import java.util.ArrayList;

/**
 * Created by rajib on 2/25/16.
 */
public class ShoppingCart {

    public ArrayList<ProductCell> productCell;
    public ArrayList<MallBdPackageCell> mallBdPackageCell;
    public float orderTotal;
    public float productDiscountTotal;
    public float shippingCost;
    public float totalDiscount;
    public float totalPrice;
    public float totalTax;
    public float voucherDiscount;

    public ShoppingCart() {
        this.productCell=new ArrayList<>();
        this.mallBdPackageCell=new ArrayList<>();
        this.orderTotal=0;
        this.productDiscountTotal=0;
        this.shippingCost=0;
        this.totalDiscount=0;
        this.totalPrice=0;
        this.totalTax=0;
        this.voucherDiscount=0;


    }


}
