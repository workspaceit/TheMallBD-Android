package com.themallbd.workspaceit.dataModel;

import java.util.ArrayList;
import java.util.Currency;

/**
 * Created by Tomal on 8/4/2016.
 */
public class Orders {
    public int id;
    public int shopId;
    public int zoneId;
    public String invoiceNo;
    public String currencyCode;
    public String currencyValue;
    public float orderTotal;
    public String orderFrom;
    public float voucher_discount;
    public float discount_total;
    public float shipping_cost;
    public String orderDate;
    public float employee_discount;
    public float special_discount;
    public float onpurchase_discount;
    public String packageSize;
    public String packageWeight;
    public String shippingAddress;
    public String shippingCountry;
    public int shippingZipCode;
    public String isWrapped;
    public String wrappedNote;
    public String createdOn;
    public String createdBy;
    public String updatedOn;
    public String updatedBy;
    public int totalQuantity;
    public Shop shop;
    public Zone zone;
    public ArrayList<OrderProducts> orderProducts;


    public Orders() {
        this.id=0;
        this.shopId=0;
        this.zoneId=0;
        this.invoiceNo="";
        this.currencyCode="";
        this.currencyValue="";
        this.orderTotal=0;
        this.orderFrom="";
        this.orderDate="";
        this.voucher_discount=0;
        this.discount_total=0;
        this.shipping_cost=0;
        this.onpurchase_discount=0;
        this.employee_discount=0;
        this.special_discount=0;
        this.packageSize="";
        this.packageWeight="";
        this.shippingAddress="";
        this.shippingCountry="";
        this.shippingZipCode=0;
        this.isWrapped="";
        this.wrappedNote="";
        this.createdOn="";
        this.createdBy="";
        this.updatedOn="";
        this.updatedBy="";
        this.totalQuantity=0;
        this.shop=new Shop();
        this.zone=new Zone();
        this.orderProducts=new ArrayList<>();


    }

}
