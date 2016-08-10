package com.themallbd.workspaceit.dataModel;

/**
 * Created by Tomal on 8/8/2016.
 */
public class FinishOrderSummary {
    public int order_id;
    public String unique_code;
    public String invoice_no;
    public float order_grand_total;
    public float bkash_charge;
    public float total_bkash_pay;
    public float paypal_pay_amount;

    public FinishOrderSummary(){
        this.order_id=0;
        this.unique_code="";
        this.invoice_no="";
        this.order_grand_total=0;
        this.bkash_charge=0;
        this.total_bkash_pay=0;
        this.paypal_pay_amount=0;
    }
}
