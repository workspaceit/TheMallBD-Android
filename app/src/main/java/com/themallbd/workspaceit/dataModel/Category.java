package com.themallbd.workspaceit.dataModel;

import java.util.ArrayList;

/**
 * Created by rajib on 2/11/16.
 */
public class Category {

    public int id;
    public String title;
    public Shop shop;
    public String icon;
    public String banner;
    public String metaTitle;
    public String metaDescription;
    public String createdOn;
    public ArrayList<Category> childrens;

    public Category() {
        this.id = 0;
        this.title = "";
        this.shop = new Shop();
        this.icon = "";
        this.metaTitle = "";
        this.metaDescription = "";
        this.createdOn = "";
        this.banner = "";
        this.childrens = new ArrayList<>();
    }
}
