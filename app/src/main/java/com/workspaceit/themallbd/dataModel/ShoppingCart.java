package com.workspaceit.themallbd.dataModel;

import java.util.ArrayList;

/**
 * Created by rajib on 2/25/16.
 */
public class ShoppingCart {

    public ArrayList<ShoppingCartCell> shoppingCartCell;
    public double totalPrice;
    public double totalDiscount;
    public double totalTax;

    public ShoppingCart() {
        this.shoppingCartCell = new ArrayList<>();
        this.totalPrice = 0.0;
        this.totalDiscount = 0.0;
        this.totalTax = 0.0;
    }

    public void setShoppingCartCell(ArrayList<ShoppingCartCell> shoppingCartCell) {
        this.shoppingCartCell = shoppingCartCell;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public void setTotalTax(double totalTax) {
        this.totalTax = totalTax;
    }

    public void addToShoppingCart(ShoppingCartCell shoppingCartCell)
    {
        this.shoppingCartCell.add(shoppingCartCell);
    }
}
