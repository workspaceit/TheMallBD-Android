package com.workspaceit.themallbd.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.dataModel.Picture;
import com.workspaceit.themallbd.dataModel.Products;
import com.workspaceit.themallbd.utility.Utility;

public class ProductDetailsActivity extends BaseActivityWithoutDrawer implements BaseSliderView.OnSliderClickListener {

    private TextView tvProductName,tvProductPrice,tvProductDescription;
    private EditText etProductQuantity;
    private Toolbar toolbar;

    private SliderLayout slideShow;

    private static int position;
    private static int arrayListIndicator = 0;
    private Products products;

    private static String productUrl = "/product/thumbnail/";
    //private static String productUrl = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        position = getIntent().getIntExtra("position", -1);
        arrayListIndicator = getIntent().getIntExtra("productArray",-1);
        if (arrayListIndicator==1)
            products = MainActivity.newProductsForHorizontalViewList.get(position);
        else if (arrayListIndicator==2)
            products = MainActivity.featuredProductsForHorizontalViewList.get(position);
        else if (arrayListIndicator==3)
            products = MainActivity.allProductsForGridViewList.get(position);

        initializeSlider();
        initialize();


    }

    private void initializeSlider() {

        slideShow = (SliderLayout) findViewById(R.id.slider_product_details);

        if (products.pictures.size()>=1) {
            for (Picture picture : products.pictures) {
                TextSliderView textSliderView = new TextSliderView(this);
                // initialize a SliderLayout
                textSliderView
                        .description(picture.caption)
                        .image(Utility.IMAGE_URL + productUrl + picture.name)
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);

                slideShow.addSlider(textSliderView);
            }
        }
        else
        {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description("Image not found")
                    .image(R.drawable.image_not_found)
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            slideShow.addSlider(textSliderView);
        }
        slideShow.setPresetTransformer(SliderLayout.Transformer.Accordion);
        slideShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slideShow.setCustomAnimation(new DescriptionAnimation());
        slideShow.setDuration(4000);
    }

    private void initialize() {
        tvProductName = (TextView) findViewById(R.id.tv_product_name_pd);
        tvProductPrice = (TextView) findViewById(R.id.tv_product_price);
        tvProductDescription = (TextView) findViewById(R.id.tv_product_description);

        tvProductName.setText(products.title);
        int size = products.prices.size();
        if (size>=1)
        {
            tvProductPrice.setText(String.format("%s", products.prices.get(0).retailPrice+"TK"));
        }
        tvProductDescription.setText(products.description);


    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }


}
