package com.themallbd.workspaceit.dataModel;

/**
 * Created by Tomal on 8/5/2016.
 */
public class Voucher {
    public String voucher_code;
    public float discount;
    public float percentage;
    public float discountAmount;

 public Voucher(){
     this.voucher_code="";
     this.discount=0;
     this.percentage=0;
     this.discountAmount=0;
 }

}
