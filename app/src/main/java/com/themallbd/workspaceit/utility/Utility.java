package com.themallbd.workspaceit.utility;


import com.themallbd.workspaceit.dataModel.Banner;
import com.themallbd.workspaceit.dataModel.CustomerPurchaseDiscount;
import com.themallbd.workspaceit.dataModel.DeliveryMethod;
import com.themallbd.workspaceit.dataModel.FinishOrderSummary;
import com.themallbd.workspaceit.dataModel.Manufacturer;
import com.themallbd.workspaceit.dataModel.PaymentMethods;
import com.themallbd.workspaceit.dataModel.Review;
import com.themallbd.workspaceit.dataModel.SearchResult;
import com.themallbd.workspaceit.dataModel.ShoppingCart;
import com.themallbd.workspaceit.dataModel.AppCredential;
import com.themallbd.workspaceit.dataModel.Category;
import com.themallbd.workspaceit.dataModel.Products;
import com.themallbd.workspaceit.dataModel.ResponseStat;
import com.themallbd.workspaceit.dataModel.Voucher;
import com.themallbd.workspaceit.dataModel.VoucherDiscount;

import java.util.ArrayList;

/**
 * Created by rajib on 2/15/16.
 */
public class Utility {

    public static ResponseStat responseStat = new ResponseStat();
    public static AppCredential loggedInUser = new AppCredential();
    public static String IMAGE_URL="http://52.34.34.82/mallbackoffice/resources/images/";
    public static ShoppingCart shoppingCart = new ShoppingCart();
    public static ArrayList<Category> parentsCategoryArraylist = new ArrayList<>();
    public static ArrayList<Products>wishlistProductArrayList=new ArrayList<>();
    public static ArrayList<SearchResult>searchResults=new ArrayList<>();
    public static ArrayList<String>searchProductTitle=new ArrayList<>();
    public static ArrayList<Products> relatedProductArryList=new ArrayList<>();
    public static ArrayList<Review>reviews=new ArrayList<>();
    public static ArrayList<Banner>banners=new ArrayList<>();
    public static FinishOrderSummary finishOrderSummary=new FinishOrderSummary();
    public static ArrayList<Manufacturer>manufacturers=new ArrayList<>();
    public static CustomerPurchaseDiscount customerPurchaseDiscount=new CustomerPurchaseDiscount();
    public static ArrayList<VoucherDiscount>voucherDiscounts=new ArrayList<>();


    public static boolean isLoggedInFlag=false;

    public static ArrayList<PaymentMethods>paymentMethodses=new ArrayList<>();
    public static ArrayList<DeliveryMethod>deliveryMethods=new ArrayList<>();
    public static String CURRENCY_CODE ="BDT";



    public static void resetShoppingCart(){
        shoppingCart.mallBdPackageCell.clear();
        shoppingCart.productCell.clear();
        shoppingCart.orderTotal=0;
        shoppingCart.productDiscountTotal=0;
        shoppingCart.shippingCost=0;
        shoppingCart.totalDiscount=0;
        shoppingCart.totalPrice=0;
        shoppingCart.totalTax=0;
        shoppingCart.voucherDiscount=0;

    }



}
