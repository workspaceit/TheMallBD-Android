package com.themallbd.workspaceit.dataModel;

/**
 * Created by rajib on 2/11/16.
 */
public class Picture {

    public int id;
    public int productId;
    public String name;
    public String caption;
    public int position;
    public String cover;
    public String createdOn;

    public Picture() {
        this.id = 0;
        this.productId = 0;
        this.name = "";
        this.caption = "";
        this.position = 0;
        this.cover = "";
        this.createdOn = "";
    }
}
