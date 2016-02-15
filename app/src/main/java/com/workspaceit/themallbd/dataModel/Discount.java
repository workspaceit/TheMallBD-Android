package com.workspaceit.themallbd.dataModel;

/**
 * Created by rajib on 2/11/16.
 */
public class Discount {

    public int id;
    public String title;
    public String type;
    public double amount;
    public String startDate;
    public String endDate;
    public String createdOn;

    public Discount() {
        this.id = 0;
        this.title = "";
        this.type = "";
        this.amount = 0.0;
        this.startDate = "";
        this.endDate = "";
        this.createdOn = "";
    }
}
