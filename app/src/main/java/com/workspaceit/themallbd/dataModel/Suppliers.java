package com.workspaceit.themallbd.dataModel;

/**
 * Created by rajib on 2/11/16.
 */
public class Suppliers {

    public int id;
    public String code;
    public String name;
    public String phone;
    public String email;
    public String contactPerson;
    public String address;
    public String country;
    public String createdOn;

    public Suppliers() {
        this.id = 0;
        this.code = "";
        this.name = "";
        this.phone = "";
        this.email = "";
        this.contactPerson = "";
        this.address = "";
        this.country = "";
        this.createdOn = "";
    }
}
