package com.themallbd.workspaceit.activity;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.gson.Gson;
import com.themallbd.workspaceit.asynctask.GetProductByProductIdAsynctask;
import com.themallbd.workspaceit.dataModel.Picture;
import com.themallbd.workspaceit.dataModel.ProductCell;
import com.themallbd.workspaceit.dataModel.Products;
import com.themallbd.workspaceit.dataModel.SelectedAttributes;
import com.themallbd.workspaceit.utility.CustomDialog;
import com.themallbd.workspaceit.view.CustomSliderView;
import com.themallbd.workspaceit.preferences.LocalShoppintCart;
import com.themallbd.workspaceit.preferences.SessionManager;
import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;
import com.themallbd.workspaceit.adapter.RelatedProductAdapter;
import com.themallbd.workspaceit.adapter.ReviewSingleRowAdapter;
import com.themallbd.workspaceit.asynctask.GetRelatedProductAsynTask;
import com.themallbd.workspaceit.asynctask.GetReviewAsynTask;
import com.themallbd.workspaceit.asynctask.GetReviewCountAsynTask;
import com.themallbd.workspaceit.asynctask.WishListAsynTask;
import com.themallbd.workspaceit.utility.MakeToast;
import com.themallbd.workspaceit.view.CustomListView;

public class ProductDetailsActivity extends BaseActivityWithoutDrawer implements BaseSliderView.OnSliderClickListener, View.OnClickListener, AdapterView.OnItemClickListener {

    private TextView tvProductName, tvProductPrice, tvProductDescription, previousPrictTextView;

    private Spinner productQunatitySpinner;
    private Toolbar toolbar;

    private Button addToCartBtn, addToWishListBtn,buyNow;
    private RatingBar ratingBar;
    private TextView normalRelatedProductTextView;
    private SliderLayout slideShow;
    // private TextSliderView textSliderView;

    private static int position;
    private static int arrayListIndicator = 0;
    private int productsQuantity = 0;
    private Products products;
    private SessionManager sessionManager;
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
    private boolean loadFlag;
    private TextView quantityPlainTextView, outOfStockTextView;


    private static String productUrl = "/product/general/";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);


        try {




            loadFlag = true;

            previousPrictTextView = (TextView) findViewById(R.id.tv_previous_product_price);
            previousPrictTextView.setPaintFlags(previousPrictTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            previousPrictTextView.setVisibility(View.GONE);
            nomalPreviousPrice = (TextView) findViewById(R.id.previous_normal_price);
            nomalPreviousPrice.setVisibility(View.GONE);
            savePriceTextViw = (TextView) findViewById(R.id.tv_save_product_price);
            saveNormalTextView = (TextView) findViewById(R.id.save_normal_text_view);
            saveNormalTextView.setVisibility(View.GONE);
            savePriceTextViw.setVisibility(View.GONE);
            ratingBar = (RatingBar) findViewById(R.id.mallBdRatingBar);
            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(Color.parseColor("#961C1E"), PorterDuff.Mode.SRC_ATOP);
            loadMoreButton = (Button) findViewById(R.id.load_more_button);
            loadMoreButton.setVisibility(View.GONE);
            loadMoreButton.setOnClickListener(this);
            normalRelatedProductTextView = (TextView) findViewById(R.id.related_product_normal_text_view);
            normalRelatedProductTextView.setVisibility(View.GONE);
            reviewNormalTextView = (TextView) findViewById(R.id.review_normal_text_view);
            scrollView = (ScrollView) findViewById(R.id.produt_details_scroll);
            reviewNormalTextView.setVisibility(View.GONE);
            addReviewButton = (Button) findViewById(R.id.add_review_button);
            addReviewButton.setOnClickListener(this);
            buyNow=(Button)findViewById(R.id.button_product_buy_now);
            buyNow.setOnClickListener(this);
            quantityPlainTextView=(TextView)findViewById(R.id.quantity_plain_textview);
            outOfStockTextView =(TextView)findViewById(R.id.out_of_stock_text_view);


            relatedProductListView = (CustomListView) findViewById(R.id.relatede_product_list__view);

            relatedProductAdapter = new RelatedProductAdapter(this, 1);
            relatedProductListView.setAdapter(relatedProductAdapter);

            reviewsListView = (CustomListView) findViewById(R.id.review_list__view);
            reviewSingleRowAdapter = new ReviewSingleRowAdapter(this);
            reviewsListView.setAdapter(reviewSingleRowAdapter);

            reviewLoadMoreButon = (Button) findViewById(R.id.load_more_review_button);
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
            else if (arrayListIndicator == 7) {
                int productId = getIntent().getIntExtra("productId", -1);
                new GetProductByProductIdAsynctask(this).execute(String.valueOf(productId));
                loadFlag = false;
            } else if (arrayListIndicator == 8) {
                products = SearchProductListActivity.searchProductArrayList.get(position);
            } else if (arrayListIndicator == 9) {
                products = MainActivity.discountProductForHorizontalList.get(position);
            } else if (arrayListIndicator == 10) {
                products = ProductFromCategoryActivity.categoryWiseProductsArrayList.get(position);
            }else if (arrayListIndicator==11){
                products=ProductByManufracturerActivity.productListByMenufracturer.get(position);
            }



            if (arrayListIndicator != 7) {
                initializeSlider();
                initialize();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            if (loadFlag) {
                initializeRelatedProductArrayList();
                initializeReviews();
                loadFlag = true;
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }



    }


    private void initializeReviews() {
        try {
            Utility.reviews.clear();
            reviewSingleRowAdapter.notifyDataSetChanged();
            new GetReviewAsynTask(this, 1).execute(String.valueOf(products.id), String.valueOf(3),
                    String.valueOf(0));

            new GetReviewCountAsynTask(this).execute(String.valueOf(products.id));
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }


    private void initializeSlider() {


        try {


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
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public void setProductForSearch(Products product) {
        try {
            this.products = product;
            initializeSlider();
            initialize();
            initializeRelatedProductArrayList();
            initializeReviews();
        }catch (Exception ex){
            ex.printStackTrace();
        }



    }

    private void initialize() {

        if (products.quantity<1){
            quantityPlainTextView.setVisibility(View.GONE);
            productQunatitySpinner.setVisibility(View.GONE);
            addToCartBtn.setVisibility(View.GONE);
            outOfStockTextView.setVisibility(View.VISIBLE);
        }else {
            quantityPlainTextView.setVisibility(View.VISIBLE);
            productQunatitySpinner.setVisibility(View.VISIBLE);
            addToCartBtn.setVisibility(View.VISIBLE);
            outOfStockTextView.setVisibility(View.GONE);
        }

        Integer[]qunatityArray=new Integer[products.quantity];
        for (int i=0; i<products.quantity; i++){
            qunatityArray[i]=i+1;
        }


        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, qunatityArray);
        productQunatitySpinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        tvProductName.setText(products.title);


        tvProductDescription.setText(products.productDescriptionMobile);


        addToCartBtn = (Button) findViewById(R.id.button_add_to_cart);
        addToWishListBtn = (Button) findViewById(R.id.button_add_to_wishlist);



        ratingBar.setRating(products.avgRating);

        if (products.discountActiveFlag) {
            nomalPreviousPrice.setVisibility(View.VISIBLE);
            previousPrictTextView.setVisibility(View.VISIBLE);
            saveNormalTextView.setVisibility(View.VISIBLE);
            previousPrictTextView.setText(products.prices.get(0).retailPrice + " "+Utility.CURRENCY_CODE);
            float currentPrice = (float) (products.prices.get(0).retailPrice - products.discountAmount);
            tvProductPrice.setText(currentPrice + " "+Utility.CURRENCY_CODE);
            savePriceTextViw.setVisibility(View.VISIBLE);
            savePriceTextViw.setText(products.discountAmount + " "+Utility.CURRENCY_CODE);

        } else {
            nomalPreviousPrice.setVisibility(View.GONE);
            previousPrictTextView.setVisibility(View.GONE);
            savePriceTextViw.setVisibility(View.GONE);
            saveNormalTextView.setVisibility(View.GONE);
            tvProductPrice.setText(products.prices.get(0).retailPrice + " "+Utility.CURRENCY_CODE);
        }


    }


    private void initializeRelatedProductArrayList() {

        Utility.relatedProductArryList.clear();
        relatedProductAdapter.notifyDataSetChanged();
        if (products.categories.size()>0) {
            new GetRelatedProductAsynTask(this, 1).execute(String.valueOf(3), String.valueOf(0),
                    String.valueOf(products.id), String.valueOf(products.categories.get(0).id));
        }
    }

    public void setDataSetAdapter() {

        relatedProductAdapter.notifyDataSetChanged();

        if (Utility.relatedProductArryList.size() == 0) {
            normalRelatedProductTextView.setVisibility(View.GONE);
        } else {
            normalRelatedProductTextView.setVisibility(View.VISIBLE);
        }

        if (Utility.relatedProductArryList.size() < 3) {
            loadMoreButton.setVisibility(View.GONE);

        } else {

            loadMoreButton.setVisibility(View.VISIBLE);
        }
    }

    public void reviewCountSet(int count) {
        this.reviewCount = count;


        if (count > 3) {
            reviewLoadMoreButon.setText("See all " + count + " reviews (newest first)");
            reviewLoadMoreButon.setVisibility(View.VISIBLE);
        }
    }

    public void setReviewDatasetAdapter() {

        if (Utility.reviews.size() > 0) {
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

        try {


            if (v == addToCartBtn) {

                try {
                    this.productsQuantity = Integer.parseInt(productQunatitySpinner.getSelectedItem().toString());
                } catch (Exception e) {
                    MakeToast.showToast(this, "Invalid Quantity");
                    return;

                }

                if (this.productsQuantity < 1) {
                    MakeToast.showToast(this, "Quantity is not valid");
                    return;
                }


                for (int i = 0; i < Utility.shoppingCart.productCell.size(); i++) {
                    if (Utility.shoppingCart.productCell.get(i).id == products.id) {
                        int newQunatity=this.productsQuantity+Utility.shoppingCart.productCell.get(i).quantity;
                        if (newQunatity<=products.quantity) {
                            Utility.shoppingCart.productCell.get(i).quantity += this.productsQuantity;
                            this.updateCart();
                            invalidateOptionsMenu();
                            CustomDialog.goToCheckOutDailog(this, "Checkout", "This Product already exist in your cart. Quantity Updated ");

                        }else {
                            MakeToast.showToast(this,"This much quantity is not available in the stock");
                        }
                        return;
                    }

                }

                ProductCell productCell = new ProductCell();

                if (products.attributes.size() < 0) {
                    SelectedAttributes selectedAttributes = new SelectedAttributes();
                    selectedAttributes.setId(products.attributes.get(0).id);
                    selectedAttributes.setName(products.attributes.get(0).name);
                    selectedAttributes.setValue(products.attributes.get(0).attributesValue.get(0).value);
                    productCell.addToSelectedAttributes(selectedAttributes);
                }

                productCell.setId(products.id);
                productCell.setProduct(products);
                productCell.setQuantity(this.productsQuantity);


                Utility.shoppingCart.productCell.add(productCell);


                this.updateCart();
                invalidateOptionsMenu();
                CustomDialog.goToCheckOutDailog(this, "Checkout", "Product has been successfully added to the cart");


            } else if (v == addToWishListBtn) {

                new WishListAsynTask(this).execute(String.valueOf(sessionManager.getUid()), String.valueOf(products.id));

            } else if (v == loadMoreButton) {
                Intent intent = new Intent(this, ShowRelatedProduct.class);

                intent.putExtra("product_id", products.id);
                intent.putExtra("category_id", products.categories.get(0).id);
                startActivity(intent);
            } else if (v == reviewLoadMoreButon) {
                Intent intent = new Intent(this, ShowAllReviewActivity.class);
                intent.putExtra("product_id", products.id);
                intent.putExtra("review_count", reviewCount);
                startActivity(intent);
            } else if (v == addReviewButton) {
                CustomDialog.addReviewCustomDailog(this, products.title, String.valueOf(products.id));
            }else if (v==buyNow){
                try {
                    this.productsQuantity = Integer.parseInt(productQunatitySpinner.getSelectedItem().toString());
                } catch (Exception e) {
                    MakeToast.showToast(this, "Invalid Quantity");
                    return;

                }

                if (this.productsQuantity < 1) {
                    MakeToast.showToast(this, "Quantity is not valid");
                    return;
                }

                boolean flag=true;

                for (int i = 0; i < Utility.shoppingCart.productCell.size(); i++) {
                    if (Utility.shoppingCart.productCell.get(i).id == products.id) {
                        flag=false;
                        break;

                    }

                }

                if (flag){
                    ProductCell productCell = new ProductCell();

                    if (products.attributes.size() < 0) {
                        SelectedAttributes selectedAttributes = new SelectedAttributes();
                        selectedAttributes.setId(products.attributes.get(0).id);
                        selectedAttributes.setName(products.attributes.get(0).name);
                        selectedAttributes.setValue(products.attributes.get(0).attributesValue.get(0).value);
                        productCell.addToSelectedAttributes(selectedAttributes);
                    }

                    productCell.setId(products.id);
                    productCell.setProduct(products);
                    productCell.setQuantity(this.productsQuantity);


                    Utility.shoppingCart.productCell.add(productCell);


                    this.updateCart();
                    invalidateOptionsMenu();

                }



                Intent intent=new Intent(this,CheckoutActivity.class);
                startActivity(intent);
                //finish();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }



    private void updateCart(){
        try {
            Gson gson=new Gson();
            String cart=gson.toJson(Utility.shoppingCart.productCell);
            LocalShoppintCart localShoppintCart=new LocalShoppintCart(this);
            localShoppintCart.setProductCart(cart);
        }catch (Exception ex){
            ex.printStackTrace();
        }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {


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

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
