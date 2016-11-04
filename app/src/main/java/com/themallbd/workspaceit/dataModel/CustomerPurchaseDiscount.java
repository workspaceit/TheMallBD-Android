package com.themallbd.workspaceit.dataModel;

/**
 * Created by Tomal on 11/4/2016.
 */

public class CustomerPurchaseDiscount {
    private int id;
    private String discount_base;
    private String discount_base_limit;
    private String discount_type;
    private double discount_amount;
    private String createdOn;

    public CustomerPurchaseDiscount(){
        this.id=0;
        this.discount_base="";
        this.discount_base_limit="";
        this.discount_type="";
        this.discount_amount=0;
        this.createdOn="";
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public double getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(double discount_amount) {
        this.discount_amount = discount_amount;
    }

    public String getDiscount_base() {
        return discount_base;
    }

    public void setDiscount_base(String discount_base) {
        this.discount_base = discount_base;
    }

    public String getDiscount_base_limit() {
        return discount_base_limit;
    }

    public void setDiscount_base_limit(String discount_base_limit) {
        this.discount_base_limit = discount_base_limit;
    }

    public String getDiscount_type() {
        return discount_type;
    }

    public void setDiscount_type(String discount_type) {
        this.discount_type = discount_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
