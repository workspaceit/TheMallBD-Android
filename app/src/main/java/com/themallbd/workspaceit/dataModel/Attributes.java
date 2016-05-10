package com.themallbd.workspaceit.dataModel;

import java.util.ArrayList;

/**
 * Created by rajib on 2/11/16.
 */
public class Attributes {

    public int id;
    public String name;
    public ArrayList<AttributesValue> attributesValue;
    public String createdOn;

    public Attributes() {
        this.id = 0;
        this.name = "";
        this.attributesValue = new ArrayList<>();
        this.createdOn = "";
    }
}
