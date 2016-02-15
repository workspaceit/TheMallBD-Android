package com.workspaceit.themallbd.dataModel;

import java.util.ArrayList;

/**
 * Created by rajib on 2/11/16.
 */
public class Quantity {

    public int id;
    public int productId;
    public ArrayList<ProductAttributeCombination> combinations;
    public int quantity;

    public Quantity() {
        this.id = 0;
        this.productId = 0;
        this.combinations = new ArrayList<>();
        this.quantity = 0;
    }
}
