package com.workspaceit.themallbd.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Transformers.BaseTransformer;
import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.adapter.RelatedProductAdapter;
import com.workspaceit.themallbd.asynctask.GetRelatedProductAsynTask;
import com.workspaceit.themallbd.asynctask.WishListAsynTask;
import com.workspaceit.themallbd.dataModel.Picture;
import com.workspaceit.themallbd.dataModel.Products;
import com.workspaceit.themallbd.dataModel.SelectedAttributes;
import com.workspaceit.themallbd.dataModel.ShoppingCartCell;
import com.workspaceit.themallbd.service.InternetConnection;
import com.workspaceit.themallbd.utility.CustomDialog;
import com.workspaceit.themallbd.utility.CustomSliderView;
import com.workspaceit.themallbd.utility.MakeToast;
import com.workspaceit.themallbd.utility.RelatedProductListView;
import com.workspaceit.themallbd.utility.SessionManager;
import com.workspaceit.themallbd.utility.Utility;

public class ProductDetailsActivity extends BaseActivityWithoutDrawer implements BaseSliderView.OnSliderClickListener, View.OnClickListener, AdapterView.OnItemClickListener {

    private TextView tvProductName, tvProductPrice, tvProductDescription, previousPrictTextView;

    private Spinner productQunatitySpinner;
    private Toolbar toolbar;

    private Button addToCartBtn, addToWishListBtn, buyNowBtn;
    private RatingBar ratingBar;
    private TextView normalRelatedProductTextView;
    private SliderLayout slideShow;
    // private TextSliderView textSliderView;

    private static int position;
    private static int arrayListIndicator = 0;
    private int productsQuantity = 0;
    private Products products;
    SessionManager sessionManager;
    private RelatedProductListView relatedProductListView;
    private RelatedProductAdapter relatedProductAdapter;
    private Button loadMoreButton;
    private ScrollView scrollView;

    private static String productUrl = "/product/general/";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    //private static String productUrl = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);


        previousPrictTextView = (TextView) findViewById(R.id.tv_previous_product_price);
        previousPrictTextView.setPaintFlags(previousPrictTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        ratingBar = (RatingBar) findViewById(R.id.mallBdRatingBar);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#961C1E"), PorterDuff.Mode.SRC_ATOP);
        loadMoreButton = (Button) findViewById(R.id.load_more_button);
        loadMoreButton.setVisibility(View.GONE);
        loadMoreButton.setOnClickListener(this);
        normalRelatedProductTextView=(TextView)findViewById(R.id.related_product_normal_text_view);
        normalRelatedProductTextView.setVisibility(View.GONE);

        scrollView = (ScrollView) findViewById(R.id.produt_details_scroll);


        relatedProductListView = (RelatedProductListView) findViewById(R.id.relatede_product_list__view);

        relatedProductAdapter = new RelatedProductAdapter(this, 1);
        relatedProductListView.setAdapter(relatedProductAdapter);


        relatedProductListView.setOnItemClickListener(this);
        slideShow = (SliderLayout) findViewById(R.id.slider_product_details);
        tvProductName = (TextView) findViewById(R.id.tv_product_name_pd);
        tvProductPrice = (TextView) findViewById(R.id.tv_product_price);
        tvProductDescription = (TextView) findViewById(R.id.tv_product_description);
        productQunatitySpinner = (Spinner) findViewById(R.id.et_product_quantity_pd);


        addToCartBtn = (Button) findViewById(R.id.button_add_to_cart);
        addToCartBtn.setOnClickListener(this);
        addToWishListBtn = (Button) findViewById(R.id.button_add_to_wishlist);
        addToWishListBtn.setOnClickListener(this);
        sessionManager = new SessionManager(this);
        position = getIntent().getIntExtra("position", -1);
        arrayListIndicator = getIntent().getIntExtra("productArray", -1);
        if (arrayListIndicator == 1)
            products = MainActivity.newProductsForHorizontalViewList.get(position);
        else if (arrayListIndicator == 2)
            products = MainActivity.featuredProductsForHorizontalViewList.get(position);
        else if (arrayListIndicator == 3)
            products = MainActivity.allProductsForGridViewList.get(position);
        else if (arrayListIndicator == 4)
            products = ProductFromCategoryActivity.categoryWiseProductsArrayList.get(position);
        else if (arrayListIndicator == 5)
            products = Utility.wishlistProductArrayList.get(position);
        else if (arrayListIndicator == 6)
            products = Utility.relatedProductArryList.get(position);

        initializeSlider();
        initialize();



    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeRelatedProductArrayList();

    }

    private void initializeSlider() {


        slideShow.removeAllSliders();
        slideShow.stopAutoCycle();


        if (products.pictures.size() >= 1) {
            for (Picture picture : products.pictures) {
                CustomSliderView textSliderView = new CustomSliderView(this);
                // initialize a SliderLayout
                textSliderView
                        .description(picture.caption)
                        .image(Utility.IMAGE_URL + productUrl + picture.name)
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);

                slideShow.addSlider(textSliderView);
            }
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

    private void initialize() {
        tvProductName.setText(products.title);

        int size = products.prices.size();
        if (size >= 1) {
            tvProductPrice.setText(String.format("%s", products.prices.get(0).retailPrice + "TK"));
        }
        tvProductDescription.setText(products.productDescriptionMobile);


        addToCartBtn = (Button) findViewById(R.id.button_add_to_cart);
        addToWishListBtn = (Button) findViewById(R.id.button_add_to_wishlist);
        buyNowBtn = (Button) findViewById(R.id.button_buy_now);
        ratingBar.setRating(products.avgRating);



    }


    private void initializeRelatedProductArrayList() {

        Utility.relatedProductArryList.clear();
        relatedProductAdapter.notifyDataSetChanged();
        new GetRelatedProductAsynTask(this,1).execute(String.valueOf(3), String.valueOf(0),
                String.valueOf(products.id), String.valueOf(products.categories.get(0).id));
    }

    public void setDataSetAdapter() {

        relatedProductAdapter.notifyDataSetChanged();

        if(Utility.relatedProductArryList.size()==0){
            normalRelatedProductTextView.setVisibility(View.GONE);
        }else {
            normalRelatedProductTextView.setVisibility(View.VISIBLE);
        }

        if (Utility.relatedProductArryList.size() < 3) {
            loadMoreButton.setVisibility(View.GONE);

        } else {

            loadMoreButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

        // MakeToast.showToast(this,String.valueOf(getApplicationContext().getResources().getConfiguration().orientation));

        CustomDialog.showImageDiolog(this, slider.getUrl(), products.title);


    }


    @Override
    public void onClick(View v) {
        if (v == addToCartBtn) {

            try {
                this.productsQuantity = Integer.parseInt(productQunatitySpinner.getSelectedItem().toString());
            } catch (Exception e) {
                MakeToast.showToast(this, "Invalid Quantity");

            }

            if (this.productsQuantity < 1) {
                MakeToast.showToast(this, "Quantity is not valid");
                return;
            }


            for (int i = 0; i < Utility.shoppingCart.shoppingCartCell.size(); i++) {
                if (Utility.shoppingCart.shoppingCartCell.get(i).id == products.id) {
                    Utility.shoppingCart.shoppingCartCell.get(i).quantity += this.productsQuantity;
                    invalidateOptionsMenu();
                    return;
                }

            }

            ShoppingCartCell shoppingCartCell = new ShoppingCartCell();

            if (products.attributes.size() < 0) {
                SelectedAttributes selectedAttributes = new SelectedAttributes();
                selectedAttributes.setId(products.attributes.get(0).id);
                selectedAttributes.setName(products.attributes.get(0).name);
                selectedAttributes.setValue(products.attributes.get(0).attributesValue.get(0).value);
                shoppingCartCell.addToSelectedAttributes(selectedAttributes);
            }


            shoppingCartCell.setId(products.id);
            shoppingCartCell.setProduct(products);
            shoppingCartCell.setQuantity(this.productsQuantity);


            Utility.shoppingCart.addToShoppingCart(shoppingCartCell);
            MakeToast.showToast(this, "Successfully added to the cart");
            invalidateOptionsMenu();

        } else if (v == addToWishListBtn) {

            new WishListAsynTask(this).execute(String.valueOf(sessionManager.getUid()), String.valueOf(products.id));

        } else if (v == loadMoreButton) {
            Intent intent = new Intent(this, ShowRelatedProduct.class);

            intent.putExtra("product_id",products.id);
            intent.putExtra("category_id",products.categories.get(0).id);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        products = Utility.relatedProductArryList.get(position);

        loadMoreButton.setVisibility(View.GONE);
        normalRelatedProductTextView.setVisibility(View.GONE);

        initializeSlider();
        initialize();

        ValueAnimator realSmoothScrollAnimation =
                ValueAnimator.ofInt(scrollView.getScrollY(), 0);
        realSmoothScrollAnimation.setDuration(500);
        realSmoothScrollAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int scrollTo = (Integer) animation.getAnimatedValue();
                scrollView.scrollTo(0, scrollTo);
            }


        });

        realSmoothScrollAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                initializeRelatedProductArrayList();
            }
        });
        realSmoothScrollAnimation.start();


    }
}
