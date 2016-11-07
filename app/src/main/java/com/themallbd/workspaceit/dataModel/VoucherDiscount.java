package com.themallbd.workspaceit.dataModel;

/**
 * Created by Tomal on 11/7/2016.
 */

public class VoucherDiscount {
    private String voucher_code;
    private float discount;

    public VoucherDiscount(){
        voucher_code="";
        discount=0;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getVoucher_code() {
        return voucher_code;
    }

    public void setVoucher_code(String voucher_code) {
        this.voucher_code = voucher_code;
    }
}
