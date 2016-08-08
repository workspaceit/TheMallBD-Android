package com.themallbd.workspaceit.dataModel;

/**
 * Created by Tomal on 8/8/2016.
 */
public class FinishOrderSummary {
    public int order_id;
    public String unique_code;
    public String invoice_no;
    public float order_grand_total;

    public FinishOrderSummary(){
        this.order_id=0;
        this.unique_code="";
        this.invoice_no="";
        this.order_grand_total=0;
    }
}
