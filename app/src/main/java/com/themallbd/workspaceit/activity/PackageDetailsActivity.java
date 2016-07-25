package com.themallbd.workspaceit.activity;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.themallbd.workspaceit.adapter.ProductsInPackageAdapter;
import com.themallbd.workspaceit.dataModel.MallBdPackage;
import com.themallbd.workspaceit.dataModel.Picture;
import com.themallbd.workspaceit.utility.CustomListView;
import com.themallbd.workspaceit.utility.CustomSliderView;
import com.themallbd.workspaceit.utility.MakeToast;
import com.themallbd.workspaceit.utility.SessionManager;
import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;

public class PackageDetailsActivity extends BaseActivityWithoutDrawer {
    private TextView tvPackageName, tvPackagePrice, tvPackageDescription, prevoiusPackagePrice;
    private Spinner packageQunatitySpinner;
    private Button addToCart,BuyNow;
    private static int position;
    private static int arrayListIndicator = 0;
    private int productsQuantity = 0;
    private MallBdPackage mallBdPackage;
    SessionManager sessionManager;
    private CustomListView packageProductListView;
    private SliderLayout slideShow;
    private ProductsInPackageAdapter productsInPackageAdapter;

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

        slideShow=(SliderLayout)findViewById(R.id.slider_package_details);
        packageQunatitySpinner=(Spinner)findViewById(R.id.et_package_quantity_pd);
        addToCart=(Button)findViewById(R.id.button_add_package_to_cart);
        packageProductListView=(CustomListView)findViewById(R.id.package_product_list_view);

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
        MakeToast.showToast(this,mallBdPackage.mallBdPackageProduct.size()+"");
      /*  productsInPackageAdapter=new ProductsInPackageAdapter(this,mallBdPackage.mallBdPackageProduct);
        packageProductListView.setAdapter(productsInPackageAdapter);
        productsInPackageAdapter.notifyDataSetChanged();*/

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
}
