package com.themallbd.workspaceit.dataModel;

/**
 * Created by rajib on 2/11/16.
 */
public class ProductAttributeCombination {

    public int id;
    public int productId;
    public int productAttributesId;
    public int attributeValueId;
    public int combinationKey;
    public String createdOn;

    public ProductAttributeCombination() {
        this.id = 0;
        this.productId = 0;
        this.productAttributesId = 0;
        this.attributeValueId = 0;
        this.combinationKey = 0;
        this.createdOn = "";
    }
}
