package com.themallbd.workspaceit.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;
import com.themallbd.workspaceit.activity.CheckoutActivity;
import com.themallbd.workspaceit.utility.CheckOutInfoSession;
import com.themallbd.workspaceit.utility.MakeToast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CheckoutViewFragment extends Fragment implements View.OnClickListener {
    private Button confrimPayemtBtn;
    private CheckOutInfoSession checkOutInfoSession;
    private EditText emailText;
    private EditText fnameText;
    private EditText lnameText;
    private Spinner citySpinner;
    private EditText addressText;
    private EditText telephoneText;
    private Button cartInfoExpandButton,expandNewCustomerInfoButton,backToCart;
    private LinearLayout checkoutInfoLayout,newCustomerInfoLayout;
    private int imageUpArrow;
    private int imageDownArrow;
    private TextView subTotalTextView,productDiscountTextView,voucherDiscountTextView,shipmentFeeTextView,totalPaybleTextView;





    public CheckoutViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_checkout_view, container, false);

        confrimPayemtBtn=(Button)view.findViewById(R.id.confrim_payemt_method);
        emailText=(EditText)view.findViewById(R.id.checkout_email);
        fnameText=(EditText)view.findViewById(R.id.checkout_fname);
        lnameText=(EditText)view.findViewById(R.id.checkout_lname);
        citySpinner =(Spinner)view.findViewById(R.id.city_spinner);
        addressText=(EditText)view.findViewById(R.id.checkout_address);
        telephoneText=(EditText)view.findViewById(R.id.checkout_telephone);


        cartInfoExpandButton=(Button)view.findViewById(R.id.cart_info_expand_button);
        expandNewCustomerInfoButton=(Button)view.findViewById(R.id.add_shipping_infromation_expand_button);

        checkoutInfoLayout=(LinearLayout)view.findViewById(R.id.checkout_info_layout);
        newCustomerInfoLayout=(LinearLayout)view.findViewById(R.id.new_customer_info_layout);


        expandNewCustomerInfoButton.setOnClickListener(this);
        cartInfoExpandButton.setOnClickListener(this);


        imageDownArrow = R.drawable.arrow_down;
        imageUpArrow = R.drawable.arrow_up;

        cartInfoExpandButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, imageUpArrow, 0);
        expandNewCustomerInfoButton.setCompoundDrawablesWithIntrinsicBounds(0,0,imageUpArrow,0);


        confrimPayemtBtn.setOnClickListener(this);
        checkOutInfoSession=new CheckOutInfoSession(getActivity());

        this.initializeCheckoutInfo(view);


        return view;


    }

    @Override
    public void onResume() {
        super.onResume();
        this.initializeValue();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
            this.initializeValue();
    }

    private void initializeCheckoutInfo(View view){
        subTotalTextView=(TextView)view.findViewById(R.id.subtotal_checkout_text_view);
        productDiscountTextView=(TextView)view.findViewById(R.id.total_discount_text_view);
        voucherDiscountTextView=(TextView)view.findViewById(R.id.voucher_discount_text_view);
        shipmentFeeTextView=(TextView)view.findViewById(R.id.shipment_fee_text_view);
        totalPaybleTextView=(TextView)view.findViewById(R.id.total_payable_text_view);
        backToCart=(Button)view.findViewById(R.id.edit_cart_back_buttom);
        backToCart.setOnClickListener(this);



        if (Utility.isLoggedInFlag){
            expandNewCustomerInfoButton.setText("Your Shipping Info");
            emailText.setText(Utility.loggedInUser.email);
            fnameText.setText(Utility.loggedInUser.user.firstName);
            lnameText.setText(Utility.loggedInUser.user.lastName);
            citySpinner.setSelection(0);
            telephoneText.setText(Utility.loggedInUser.user.phone);
            addressText.setText(Utility.loggedInUser.user.userDetails.shippingAddress.shippingAddress);



        }else {
            expandNewCustomerInfoButton.setText("New Customer Shipping Info");
        }


    }


    private void initializeValue(){
        double subTotal=0,discount=0,shipmentFee=40,total=0,voucher=0;
        for (int i=0;i< Utility.shoppingCart.productCell.size();i++){
            double productPrie=Utility.shoppingCart.productCell.get(i).quantity*Utility.shoppingCart.productCell.get(i).product.prices.get(0).retailPrice;
            subTotal+=productPrie;
            if(Utility.shoppingCart.productCell.get(i).product.discountActiveFlag){
                discount+=Utility.shoppingCart.productCell.get(i).product.discountAmount;
            }
        }

        for (int i=0;i<Utility.shoppingCart.mallBdPackageCell.size();i++){
            double packagePrice=Utility.shoppingCart.mallBdPackageCell.get(i).quantity*Utility.shoppingCart.mallBdPackageCell.get(i).mallBdPackage.packagePriceTotal;
            subTotal+=packagePrice;
        }



        subTotalTextView.setText(subTotal+" BDT");
        productDiscountTextView.setText(discount + " BDT");
        total=subTotal-discount;
        totalPaybleTextView.setText(total + " BDT");
        shipmentFeeTextView.setText(shipmentFee+" BDT");
        voucherDiscountTextView.setText(voucher + " BDT");
        Utility.shoppingCart.orderTotal=(float)subTotal;
        Utility.shoppingCart.totalPrice=(float)total;
        Utility.shoppingCart.productDiscountTotal=(float)discount;
    }

    @Override
    public void onClick(View v) {


        if(v==cartInfoExpandButton){
            if (checkoutInfoLayout.getVisibility() == View.VISIBLE) {

                cartInfoExpandButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, imageDownArrow, 0);
                this.hideLayout(checkoutInfoLayout, 500);


            } else {

                cartInfoExpandButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, imageUpArrow, 0);

                if (newCustomerInfoLayout.getVisibility() == View.VISIBLE) {
                    this.hideLayout(newCustomerInfoLayout, 100);
                    expandNewCustomerInfoButton.setCompoundDrawablesWithIntrinsicBounds(0,0,imageDownArrow,0);
                }
                this.showLayout(checkoutInfoLayout, 500);
            }


        }
        else if(v==expandNewCustomerInfoButton){

            if (newCustomerInfoLayout.getVisibility() == View.VISIBLE) {
                expandNewCustomerInfoButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, imageDownArrow, 0);
                this.hideLayout(newCustomerInfoLayout, 500);

            } else {
                expandNewCustomerInfoButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, imageUpArrow, 0);
                if (checkoutInfoLayout.getVisibility() == View.VISIBLE) {
                    this.hideLayout(checkoutInfoLayout, 100);
                    expandNewCustomerInfoButton.setCompoundDrawablesWithIntrinsicBounds(0,0,imageDownArrow,0);
                }

                this.showLayout(newCustomerInfoLayout, 500);

            }

        }
        else if(v==confrimPayemtBtn) {
            if (checkInputValidity()) {
                this.saveInformation();

                CheckoutActivity.tabFlag = 2;
                CheckoutActivity.mViewPager.setCurrentItem(2);

            }
        }else if (v==backToCart){
            CheckoutActivity.tabFlag = 0;
            CheckoutActivity.mViewPager.setCurrentItem(0);

        }

    }


    private void hideLayout(final LinearLayout linearLayout, int duration) {

        linearLayout.animate()
                .alpha(0.0F)
                .translationY(-linearLayout.getHeight())
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        linearLayout.setVisibility(View.GONE);
                    }
                });

    }


    private void showLayout(final LinearLayout linearLayout, int duration) {
        linearLayout.animate()
                .alphaBy(1.0F)
                .translationYBy(linearLayout.getHeight())
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                });


    }



    private void saveInformation(){

        checkOutInfoSession.saveDataInPreference(emailText.getText().toString(), fnameText.getText().toString(),
                lnameText.getText().toString(), citySpinner.getSelectedItem().toString(), citySpinner.getSelectedItemPosition(),
                addressText.getText().toString(), telephoneText.getText().toString());


    }

    private boolean checkInputValidity(){
        String email=emailText.getText().toString();
        CharSequence input=email;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        String country= citySpinner.getSelectedItem().toString();

        if(!matcher.matches()){
            MakeToast.showToast(getActivity(),"Please Enter a valid Email ID");
            emailText.requestFocus();
            return false;

        }else if(fnameText.getText().toString().equals("")){
            MakeToast.showToast(getActivity(), "First Name is Required");
            fnameText.requestFocus();
            return false;
        }else if(lnameText.getText().toString().equals("")){
            MakeToast.showToast(getActivity(), "Last Name is Required");
            lnameText.requestFocus();
            return false;
        }else if(country.equals("Country")){
            MakeToast.showToast(getActivity(), "Please select a country");
            citySpinner.requestFocus();
            return false;
        }else if(addressText.getText().toString().equals("")){
            MakeToast.showToast(getActivity(), "Delivery Address is required");
            addressText.requestFocus();
            return false;
        } else if (telephoneText.getText().toString().equals("")){
            MakeToast.showToast(getActivity(),"Telephone Number is required");
            telephoneText.requestFocus();
            return false;
        }


        return true;
    }
}
