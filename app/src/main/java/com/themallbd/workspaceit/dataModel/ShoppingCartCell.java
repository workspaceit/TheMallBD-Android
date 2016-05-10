package com.themallbd.workspaceit.dataModel;

import java.util.ArrayList;

/**
 * Created by rajib on 2/25/16.
 */
public class ShoppingCartCell {

    public int id;
    public int quantity;
    public Products product; // Array of Product class
    public ArrayList<SelectedAttributes> selectedAttributes;

    public ShoppingCartCell() {
        this.id = 0;
        this.quantity = 0;
        this.product = new Products();
        this.selectedAttributes = new ArrayList<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public void setSelectedAttributes(ArrayList<SelectedAttributes> selectedAttributes) {
        this.selectedAttributes = selectedAttributes;
    }

    public void addToSelectedAttributes(SelectedAttributes selectedAttributes)
    {
        this.selectedAttributes.add(selectedAttributes);
    }
}
