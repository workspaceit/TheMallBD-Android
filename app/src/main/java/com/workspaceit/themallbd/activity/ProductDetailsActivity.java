package com.workspaceit.themallbd.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.adapter.RelatedProductAdapter;
import com.workspaceit.themallbd.adapter.ReviewSingleRowAdapter;
import com.workspaceit.themallbd.asynctask.GetRelatedProductAsynTask;
import com.workspaceit.themallbd.asynctask.GetReviewAsynTask;
import com.workspaceit.themallbd.asynctask.GetReviewCountAsynTask;
import com.workspaceit.themallbd.asynctask.WishListAsynTask;
import com.workspaceit.themallbd.dataModel.Picture;
import com.workspaceit.themallbd.dataModel.Products;
import com.workspaceit.themallbd.dataModel.SelectedAttributes;
import com.workspaceit.themallbd.dataModel.ShoppingCartCell;
import com.workspaceit.themallbd.utility.CustomDialog;
import com.workspaceit.themallbd.utility.CustomSliderView;
import com.workspaceit.themallbd.utility.MakeToast;
import com.workspaceit.themallbd.utility.CustomListView;
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
    private CustomListView relatedProductListView;
    private RelatedProductAdapter relatedProductAdapter;
    private ReviewSingleRowAdapter reviewSingleRowAdapter;
    private TextView reviewNormalTextView;

    private CustomListView reviewsListView;
    private Button reviewLoadMoreButon;
    private Button loadMoreButton;
    private ScrollView scrollView;
    private int reviewCount;
    private Button addReviewButton;
    private TextView savePriceTextViw;
    private TextView nomalPreviousPrice;
    private TextView saveNormalTextView;


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
        previousPrictTextView.setVisibility(View.GONE);
        nomalPreviousPrice=(TextView)findViewById(R.id.previous_normal_price);
        nomalPreviousPrice.setVisibility(View.GONE);
        savePriceTextViw=(TextView)findViewById(R.id.tv_save_product_price);
        saveNormalTextView=(TextView)findViewById(R.id.save_normal_text_view);
        saveNormalTextView.setVisibility(View.GONE);
        savePriceTextViw.setVisibility(View.GONE);
        ratingBar = (RatingBar) findViewById(R.id.mallBdRatingBar);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#961C1E"), PorterDuff.Mode.SRC_ATOP);
        loadMoreButton = (Button) findViewById(R.id.load_more_button);
        loadMoreButton.setVisibility(View.GONE);
        loadMoreButton.setOnClickListener(this);
        normalRelatedProductTextView=(TextView)findViewById(R.id.related_product_normal_text_view);
        normalRelatedProductTextView.setVisibility(View.GONE);
        reviewNormalTextView=(TextView)findViewById(R.id.review_normal_text_view);
        scrollView = (ScrollView) findViewById(R.id.produt_details_scroll);
        reviewNormalTextView.setVisibility(View.GONE);
        addReviewButton=(Button)findViewById(R.id.add_review_button);
        addReviewButton.setOnClickListener(this);


        relatedProductListView = (CustomListView) findViewById(R.id.relatede_product_list__view);

        relatedProductAdapter = new RelatedProductAdapter(this, 1);
        relatedProductListView.setAdapter(relatedProductAdapter);

        reviewsListView=(CustomListView)findViewById(R.id.review_list__view);
        reviewSingleRowAdapter=new ReviewSingleRowAdapter(this);
        reviewsListView.setAdapter(reviewSingleRowAdapter);

        reviewLoadMoreButon=(Button)findViewById(R.id.load_more_review_button);
        reviewLoadMoreButon.setOnClickListener(this);
        reviewLoadMoreButon.setVisibility(View.GONE);


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
        initializeReviews();

    }


    private void initializeReviews(){
        Utility.reviews.clear();
        reviewSingleRowAdapter.notifyDataSetChanged();
        new GetReviewAsynTask(this,1).execute(String.valueOf(products.id), String.valueOf(3),
                String.valueOf(0));

        new GetReviewCountAsynTask(this).execute(String.valueOf(products.id));
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


        tvProductDescription.setText(products.productDescriptionMobile);


        addToCartBtn = (Button) findViewById(R.id.button_add_to_cart);
        addToWishListBtn = (Button) findViewById(R.id.button_add_to_wishlist);
        buyNowBtn = (Button) findViewById(R.id.button_buy_now);
        ratingBar.setRating(products.avgRating);

        if(products.discountActiveFlag){
            nomalPreviousPrice.setVisibility(View.VISIBLE);
            previousPrictTextView.setVisibility(View.VISIBLE);
            saveNormalTextView.setVisibility(View.VISIBLE);
            previousPrictTextView.setText(products.prices.get(0).retailPrice+" Tk");
            float currentPrice= (float) (products.prices.get(0).retailPrice-products.discountAmount);
            tvProductPrice.setText(currentPrice + " Tk");
            savePriceTextViw.setVisibility(View.VISIBLE);
            savePriceTextViw.setText(products.discountAmount+" Tk");

        }else {
            nomalPreviousPrice.setVisibility(View.GONE);
            previousPrictTextView.setVisibility(View.GONE);
            savePriceTextViw.setVisibility(View.GONE);
            saveNormalTextView.setVisibility(View.GONE);
            tvProductPrice.setText(products.prices.get(0).retailPrice+" Tk");
        }



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

    public void reviewCountSet(int count){
        this.reviewCount=count;


        if(count>3){
            reviewLoadMoreButon.setText("See all "+count+" reviews (newest first)");
            reviewLoadMoreButon.setVisibility(View.VISIBLE);
        }
    }

    public void setReviewDatasetAdapter(){

        if(Utility.reviews.size()>0){
            reviewNormalTextView.setVisibility(View.VISIBLE);
        }
        reviewSingleRowAdapter.notifyDataSetChanged();
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
        }else if(v==reviewLoadMoreButon){
                Intent intent=new Intent(this,ShowAllReviewActivity.class);
            intent.putExtra("product_id",products.id);
            intent.putExtra("review_count",reviewCount);
            startActivity(intent);
        }
        else if(v==addReviewButton){
            CustomDialog.addReviewCustomDailog(this,products.title,String.valueOf(products.id));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        products = Utility.relatedProductArryList.get(position);

        loadMoreButton.setVisibility(View.GONE);
        normalRelatedProductTextView.setVisibility(View.GONE);
        reviewLoadMoreButon.setVisibility(View.GONE);
        reviewNormalTextView.setVisibility(View.GONE);

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
                initializeReviews();
            }
        });
        realSmoothScrollAnimation.start();


    }
}
