package com.themallbd.workspaceit.dataModel;

/**
 * Created by Tomal on 8/1/2016.
 */
public class DeliveryMethod {
    public int id;
    public String title;
    public String icon ;
    public float deliveryPrice;
    public String createdOn ;
    public float delivery_price_limit;

    public DeliveryMethod(){
        this.id=0;
        this.title="";
        this.icon="";
        this.deliveryPrice=0;
        this.createdOn="";
        this.delivery_price_limit=0;

    }
}
