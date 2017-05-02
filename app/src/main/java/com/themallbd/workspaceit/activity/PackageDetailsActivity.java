package com.themallbd.workspaceit.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.gson.Gson;
import com.themallbd.workspaceit.adapter.ProductsInPackageAdapter;
import com.themallbd.workspaceit.dataModel.MallBdPackage;
import com.themallbd.workspaceit.dataModel.MallBdPackageCell;
import com.themallbd.workspaceit.utility.CustomDialog;
import com.themallbd.workspaceit.view.CustomListView;
import com.themallbd.workspaceit.view.CustomSliderView;
import com.themallbd.workspaceit.preferences.LocalShoppintCart;
import com.themallbd.workspaceit.utility.MakeToast;
import com.themallbd.workspaceit.preferences.SessionManager;
import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;

public class PackageDetailsActivity extends BaseActivityWithoutDrawer implements View.OnClickListener, AdapterView.OnItemClickListener {
    private TextView tvPackageName, tvPackagePrice, tvPackageDescription, prevoiusPackagePrice,savePackagePrice;
    private Spinner packageQunatitySpinner;
    private Button addToCart,buyNow;
    private static int position;
    private static int arrayListIndicator = 0;

    private MallBdPackage mallBdPackage;
    SessionManager sessionManager;
    private CustomListView packageProductListView;
    private SliderLayout slideShow;
    private ProductsInPackageAdapter productsInPackageAdapter;
    private int packageQuantity;
    private String packageUrl = "package/general/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_details);

        tvPackageName=(TextView)findViewById(R.id.tv_package_name_pd);
        tvPackagePrice =(TextView)findViewById(R.id.tv_package_price);
        tvPackageDescription=(TextView)findViewById(R.id.tv_package_description);
        prevoiusPackagePrice =(TextView)findViewById(R.id.tv_previous_package_price);
        prevoiusPackagePrice.setPaintFlags(prevoiusPackagePrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        savePackagePrice=(TextView)findViewById(R.id.tv_package_save_product_price);

        slideShow=(SliderLayout)findViewById(R.id.slider_package_details);
        packageQunatitySpinner=(Spinner)findViewById(R.id.et_package_quantity_pd);
        addToCart=(Button)findViewById(R.id.button_add_package_to_cart);
        packageProductListView=(CustomListView)findViewById(R.id.package_product_list_view);

        buyNow=(Button)findViewById(R.id.button_package_buy_now);
        buyNow.setOnClickListener(this);
        sessionManager=new SessionManager(this);

        position = getIntent().getIntExtra("position", -1);
        arrayListIndicator = getIntent().getIntExtra("productArray", -1);

        if(arrayListIndicator==1){
            mallBdPackage=MainActivity.packgeProductForHorizontalList.get(position);
        }

        initializeSlider();
        initialize();

    }


    private void initialize(){
        tvPackageName.setText(mallBdPackage.packageTitle);
        tvPackageDescription.setText(mallBdPackage.description);
        prevoiusPackagePrice.setText(mallBdPackage.originalPriceTotal+" "+Utility.CURRENCY_CODE);
        tvPackagePrice.setText(mallBdPackage.packagePriceTotal + " "+Utility.CURRENCY_CODE);
        savePackagePrice.setText((mallBdPackage.originalPriceTotal-mallBdPackage.packagePriceTotal)+" "+Utility.CURRENCY_CODE);
        addToCart.setOnClickListener(this);



        productsInPackageAdapter=new ProductsInPackageAdapter(this,mallBdPackage.packageProduct);
        packageProductListView.setAdapter(productsInPackageAdapter);
        packageProductListView.setOnItemClickListener(this);
        productsInPackageAdapter.notifyDataSetChanged();

    }


    private void initializeSlider() {


        slideShow.removeAllSliders();
        slideShow.stopAutoCycle();


        if (mallBdPackage.image!="") {

                CustomSliderView textSliderView = new CustomSliderView(this);
                // initialize a SliderLayout
                textSliderView

                        .image(Utility.IMAGE_URL + packageUrl + mallBdPackage.image)
                        .setScaleType(BaseSliderView.ScaleType.Fit);

                slideShow.addSlider(textSliderView);

        } else {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description("Image not found")
                    .image(R.drawable.image_not_found)
                    .setScaleType(BaseSliderView.ScaleType.Fit);


            slideShow.addSlider(textSliderView);
        }


        slideShow.setPresetTransformer(SliderLayout.Transformer.Foreground2Background);
        slideShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slideShow.setCustomAnimation(new DescriptionAnimation());


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void onClick(View v) {

        if(v==addToCart){
            try {
                this.packageQuantity = Integer.parseInt(packageQunatitySpinner.getSelectedItem().toString());
            } catch (Exception e) {
                MakeToast.showToast(this, "Invalid Quantity");

            }

            if (this.packageQuantity < 1) {
                MakeToast.showToast(this, "Quantity is not valid");
                return;
            }

            for (int i = 0; i < Utility.shoppingCart.mallBdPackageCell.size(); i++) {
                if (Utility.shoppingCart.mallBdPackageCell.get(i).mallBdPackage.id == mallBdPackage.id) {
                    Utility.shoppingCart.mallBdPackageCell.get(i).quantity += this.packageQuantity;
                    this.updateCart();
                    invalidateOptionsMenu();
                    CustomDialog.goToCheckOutDailog(this, "Checkout", "This Package already exist in your cart. Quantity Updated ");

                    return;
                }

            }


            MallBdPackageCell mallBdPackageCell=new MallBdPackageCell();
            mallBdPackageCell.setId(mallBdPackage.id);
            mallBdPackageCell.setQuantity(this.packageQuantity);
            mallBdPackageCell.setMallBdPackage(mallBdPackage);

            Utility.shoppingCart.mallBdPackageCell.add(mallBdPackageCell);

            this.updateCart();
            invalidateOptionsMenu();
            CustomDialog.goToCheckOutDailog(this, "Checkout", "Package has been successfully added to the cart");

        }else if (v==buyNow){

            try {
                this.packageQuantity = Integer.parseInt(packageQunatitySpinner.getSelectedItem().toString());
            } catch (Exception e) {
                MakeToast.showToast(this, "Invalid Quantity");
                return;

            }

            if (this.packageQuantity < 1) {
                MakeToast.showToast(this, "Quantity is not valid");
                return;
            }


            boolean flag=true;
            for (int i = 0; i < Utility.shoppingCart.mallBdPackageCell.size(); i++) {
                if (Utility.shoppingCart.mallBdPackageCell.get(i).mallBdPackage.id == mallBdPackage.id) {
                   flag=false;
                    break;
                }

            }

            if (flag){
                MallBdPackageCell mallBdPackageCell=new MallBdPackageCell();
                mallBdPackageCell.setId(mallBdPackage.id);
                mallBdPackageCell.setQuantity(this.packageQuantity);
                mallBdPackageCell.setMallBdPackage(mallBdPackage);

                Utility.shoppingCart.mallBdPackageCell.add(mallBdPackageCell);

                this.updateCart();
                invalidateOptionsMenu();
            }





            Intent intent=new Intent(this,CheckoutActivity.class);
            startActivity(intent);

        }

    }



    private void updateCart(){
        Gson gson=new Gson();
        String cart=gson.toJson(Utility.shoppingCart.mallBdPackageCell);
        LocalShoppintCart localShoppintCart=new LocalShoppintCart(this);
        localShoppintCart.setPackageCart(cart);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,ProductDetailsActivity.class);
        intent.putExtra("position",position);
        intent.putExtra("productArray",12);
        intent.putExtra("packageArray",PackageDetailsActivity.position);
        startActivity(intent);
    }
}
