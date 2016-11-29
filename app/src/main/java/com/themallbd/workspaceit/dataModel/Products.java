package com.themallbd.workspaceit.dataModel;

import java.util.ArrayList;

/**
 * Created by rajib on 2/11/16.
 */
public class Products {

    public int id;
    public String code;
    public String title;
    public String description;
    public String productDescriptionMobile;
    public int point;
    public String url;
    public String barCode;
    public String width;
    public String height;
    public String depth;
    public String weight;
    public int quantity;
    public String status;
    public String metaTitle;
    public String metaDescription;
    public String expireDate;
    public String manufacturedDate;
    public String createdOn;
    public float avgRating;
    public float previousPrice;
    public float discountAmount;
    public boolean discountActiveFlag;

    public Shop shop;
    public Suppliers supplier;
    public WareHouse warehouse;
    public Manufacturer manufacturer;

    public ArrayList<Price> prices;
    public ArrayList<Discount> discounts;
    public ArrayList<Category> categories;
    public ArrayList<Tags> tags;
    public ArrayList<Picture> pictures;
    public ArrayList<Attributes> attributes;
    public Boolean isFeatured;
    public ArrayList<Quantity> productQuantity;
    public int minimumOrderQuantity;

    public Products() {
        this.id = 0;
        this.code = "";
        this.title = "";
        this.description = "";
        this.url = "";
        this.point=0;
        this.barCode = "";
        this.width = "";
        this.height = "";
        this.depth = "";
        this.weight = "";
        this.quantity = 0;
        this.status = "";
        this.metaTitle = "";
        this.metaDescription ="";
        this.expireDate = "";
        this.manufacturedDate = "";
        this.createdOn = "";
        this.isFeatured = false;
        this.productDescriptionMobile="";

        this.avgRating=0;
        this.previousPrice=0;
        this.discountAmount=0;
        this.discountActiveFlag=false;
        this.shop = new Shop();
        this.supplier = new Suppliers();
        this.warehouse = new WareHouse();
        this.manufacturer = new Manufacturer();

        this.prices = new ArrayList<>();
        this.discounts = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.pictures = new ArrayList<>();
        this.attributes = new ArrayList<>();
        this.productQuantity = new ArrayList<>();
        this.minimumOrderQuantity=0;

    }
}
