package com.themallbd.workspaceit.dataModel;

import java.util.ArrayList;

/**
 * Created by rajib on 2/11/16.
 */
public class CustomerWishList {

    public int id;
    public int customerId;
    public ArrayList<Products> products;
    public String createdOn;

    public CustomerWishList() {
        this.id = 0;
        this.customerId = 0;
        this.products = new ArrayList<>();
        this.createdOn = "";
    }
}
