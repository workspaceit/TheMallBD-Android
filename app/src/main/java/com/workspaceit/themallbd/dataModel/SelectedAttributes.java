package com.workspaceit.themallbd.dataModel;

/**
 * Created by rajib on 2/25/16.
 */
public class SelectedAttributes {

    public int id;
    public String name;
    public String value;

    public SelectedAttributes() {
        this.id = 0;
        this.name = "";
        this.value = "";
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
