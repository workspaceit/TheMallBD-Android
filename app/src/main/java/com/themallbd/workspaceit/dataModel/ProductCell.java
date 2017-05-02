package com.themallbd.workspaceit.dataModel;

import java.util.ArrayList;

/**
 * Created by Tomal on 7/22/2016.
 */
public class ProductCell {
    public int id;
    public int quantity;
    public Products product;
    public ArrayList<SelectedAttributes> selectedAttributes;

    public ProductCell(){
        this.id=0;
        this.quantity=0;
        this.product=new Products();
        this.selectedAttributes=new ArrayList<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setSelectedAttributes(ArrayList<SelectedAttributes> selectedAttributes) {
        this.selectedAttributes = selectedAttributes;
    }

    public void addToSelectedAttributes(SelectedAttributes selectedAttributes){
        this.selectedAttributes.add(selectedAttributes);

    }
}
