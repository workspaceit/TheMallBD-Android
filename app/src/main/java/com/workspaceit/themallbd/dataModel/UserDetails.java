package com.workspaceit.themallbd.dataModel;

/**
 * Created by rajib on 2/15/16.
 */
public class UserDetails {

    public int id;
    public Address address;
    public ShippingAddress shippingAddress;

    public UserDetails() {
        this.id = 0;
        this.address = new Address();
        this.shippingAddress = new ShippingAddress();
    }
}
