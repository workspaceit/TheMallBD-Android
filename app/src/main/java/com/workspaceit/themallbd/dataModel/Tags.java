package com.workspaceit.themallbd.dataModel;

/**
 * Created by rajib on 2/15/16.
 */
public class Tags {

    public int id;
    public String title;
    public String createdOn;
    public Shop shop;

    public Tags() {
        this.id = 0;
        this.title = "";
        this.createdOn = "";
        this.shop = new Shop();
    }
}
