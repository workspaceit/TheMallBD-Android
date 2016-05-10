package com.themallbd.workspaceit.dataModel;

/**
 * Created by rajib on 2/15/16.
 */
public class Taxes {

    public int id;
    public String title;
    public String type;
    public double amount;
    public String createdOn;

    public Taxes() {
        this.id = 0;
        this.title = "";
        this.type = "";
        this.amount = 0.0;
        this.createdOn = "";
    }
}
