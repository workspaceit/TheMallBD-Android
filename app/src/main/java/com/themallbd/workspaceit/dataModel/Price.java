package com.themallbd.workspaceit.dataModel;

/**
 * Created by rajib on 2/11/16.
 */
public class Price {

    public int id;
    public Taxes tax;
    public double purchasePrice;
    public double wholesalePrice;
    public double retailPrice;
    public String createdOn;

    public Price() {
        this.id = 0;
        this.tax = new Taxes();
        this.purchasePrice = 0.0;
        this.wholesalePrice = 0.0;
        this.retailPrice = 0.0;
        this.createdOn = "";
    }
}
