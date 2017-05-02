package com.themallbd.workspaceit.dataModel;

/**
 * Created by rajib on 2/15/16.
 */
public class WareHouse {

    public int id;
    public String code;
    public String name;
    public String zone;
    public String address;
    public String country;
    public String city;
    public String zipCode;
    public User manager;
    public String createdOn;

    public WareHouse() {
        this.id = 0;
        this.code = "";
        this.name = "";
        this.zone = "";
        this.address = "";
        this.country = "";
        this.city = "";
        this.zipCode = "";
        this.manager = new User();
        this.createdOn = "";
    }
}
