package com.themallbd.workspaceit.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.themallbd.workspaceit.asynctask.GetVoucherDiscountAsynTask;
import com.themallbd.workspaceit.dataModel.Voucher;
import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;
import com.themallbd.workspaceit.activity.CheckoutActivity;
import com.themallbd.workspaceit.utility.CheckOutInfoSession;
import com.themallbd.workspaceit.utility.MakeToast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CheckoutViewFragment extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private Button confrimPayemtBtn;
    private CheckOutInfoSession checkOutInfoSession;
    private EditText emailText;
    private EditText fnameText;
    private EditText lnameText;
    private Spinner citySpinner;
    private EditText addressText;
    private EditText telephoneText;
    private Button cartInfoExpandButton, expandNewCustomerInfoButton, backToCart;
    private LinearLayout checkoutInfoLayout, newCustomerInfoLayout;
    private int imageUpArrow;
    private int imageDownArrow;
    private TextView subTotalTextView, productDiscountTextView, voucherDiscountTextView, shipmentFeeTextView, totalPaybleTextView;
    private TextView firstDeliveryPrice, seocndDeliveryPrice;
    private RadioGroup radioDeliveryMethodGroup;
    private double subTotal = 0, discount = 0, shipmentFee, total = 0, voucherTotal = 0;
    public static int DELIVEY_METHOD;
    private Button addVoucherButton;
    private LinearLayout voucherLayout;
    private EditText voucherCodeTextView;
    private ArrayList<Voucher> voucherArrayList;


    public CheckoutViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_checkout_view, container, false);

        confrimPayemtBtn = (Button) view.findViewById(R.id.confrim_payemt_method);
        emailText = (EditText) view.findViewById(R.id.checkout_email);
        fnameText = (EditText) view.findViewById(R.id.checkout_fname);
        lnameText = (EditText) view.findViewById(R.id.checkout_lname);
        citySpinner = (Spinner) view.findViewById(R.id.city_spinner);
        addressText = (EditText) view.findViewById(R.id.checkout_address);
        telephoneText = (EditText) view.findViewById(R.id.checkout_telephone);
        voucherCodeTextView = (EditText) view.findViewById(R.id.voucher_code_text_view);

        firstDeliveryPrice = (TextView) view.findViewById(R.id.delivery_price_first);
        seocndDeliveryPrice = (TextView) view.findViewById(R.id.dekivery_payment_second);

        radioDeliveryMethodGroup = (RadioGroup) view.findViewById(R.id.radio_delivery_method_group);
        voucherArrayList=new ArrayList<>();
        voucherLayout = (LinearLayout) view.findViewById(R.id.voucher_layout);
        cartInfoExpandButton = (Button) view.findViewById(R.id.cart_info_expand_button);
        expandNewCustomerInfoButton = (Button) view.findViewById(R.id.add_shipping_infromation_expand_button);

        checkoutInfoLayout = (LinearLayout) view.findViewById(R.id.checkout_info_layout);
        newCustomerInfoLayout = (LinearLayout) view.findViewById(R.id.new_customer_info_layout);
        addVoucherButton = (Button) view.findViewById(R.id.add_voucher_button);
        addVoucherButton.setOnClickListener(this);

        expandNewCustomerInfoButton.setOnClickListener(this);
        cartInfoExpandButton.setOnClickListener(this);
        DELIVEY_METHOD = Utility.deliveryMethods.get(0).id;

        imageDownArrow = R.drawable.arrow_down;
        imageUpArrow = R.drawable.arrow_up;

        cartInfoExpandButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, imageUpArrow, 0);
        expandNewCustomerInfoButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, imageUpArrow, 0);

        shipmentFee = Utility.deliveryMethods.get(0).deliveryPrice;

        confrimPayemtBtn.setOnClickListener(this);
        checkOutInfoSession = new CheckOutInfoSession(getActivity());

        this.initializeCheckoutInfo(view);

        initializeRadioButton();
        return view;


    }

    private void initializeRadioButton() {
        for (int i = 0; i < Utility.deliveryMethods.size(); i++) {
            RadioButton radioButton = new RadioButton(getActivity());
            radioButton.setId(Utility.deliveryMethods.get(i).id);
            radioButton.setText(Utility.deliveryMethods.get(i).title);
            radioDeliveryMethodGroup.addView(radioButton);
        }

        radioDeliveryMethodGroup.check(Utility.deliveryMethods.get(0).id);
        firstDeliveryPrice.setText(Utility.deliveryMethods.get(0).deliveryPrice + " BDT");
        seocndDeliveryPrice.setText(Utility.deliveryMethods.get(1).deliveryPrice + " BDT");

        radioDeliveryMethodGroup.setOnCheckedChangeListener(this);
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

    private void initializeCheckoutInfo(View view) {
        subTotalTextView = (TextView) view.findViewById(R.id.subtotal_checkout_text_view);
        productDiscountTextView = (TextView) view.findViewById(R.id.total_discount_text_view);
        voucherDiscountTextView = (TextView) view.findViewById(R.id.voucher_discount_text_view);
        shipmentFeeTextView = (TextView) view.findViewById(R.id.shipment_fee_text_view);
        totalPaybleTextView = (TextView) view.findViewById(R.id.total_payable_text_view);
        backToCart = (Button) view.findViewById(R.id.edit_cart_back_buttom);
        backToCart.setOnClickListener(this);


        if (Utility.isLoggedInFlag) {
            expandNewCustomerInfoButton.setText("Your Shipping Info");
            emailText.setText(Utility.loggedInUser.email);
            fnameText.setText(Utility.loggedInUser.user.firstName);
            lnameText.setText(Utility.loggedInUser.user.lastName);
            citySpinner.setSelection(0);
            telephoneText.setText(Utility.loggedInUser.user.phone);
            addressText.setText(Utility.loggedInUser.user.userDetails.shippingAddress.shippingAddress);


        } else {
            expandNewCustomerInfoButton.setText("New Customer Shipping Info");
        }


    }


    private void initializeValue() {

        subTotal = 0;
        discount = 0;
        float voucher=0;

        for (Voucher v:voucherArrayList){
            v.discountAmount=Utility.shoppingCart.orderTotal*v.percentage;
            voucher+=v.discountAmount;
        }

        this.voucherTotal=voucher;

        for (int i = 0; i < Utility.shoppingCart.productCell.size(); i++) {
            double productPrie = Utility.shoppingCart.productCell.get(i).quantity * Utility.shoppingCart.productCell.get(i).product.prices.get(0).retailPrice;
            subTotal += productPrie;
            if (Utility.shoppingCart.productCell.get(i).product.discountActiveFlag) {
                discount += Utility.shoppingCart.productCell.get(i).product.discountAmount;
            }
        }

        for (int i = 0; i < Utility.shoppingCart.mallBdPackageCell.size(); i++) {
            double packagePrice = Utility.shoppingCart.mallBdPackageCell.get(i).quantity * Utility.shoppingCart.mallBdPackageCell.get(i).mallBdPackage.packagePriceTotal;
            subTotal += packagePrice;
        }

        if (subTotal > Utility.deliveryMethods.get(0).delivery_price_limit)
            shipmentFee = 0;
        else
            shipmentFee = Utility.deliveryMethods.get(0).deliveryPrice;

        total = (subTotal + shipmentFee) - (discount + voucherTotal);


        subTotalTextView.setText(subTotal + " BDT");
        productDiscountTextView.setText(discount + " BDT");

        totalPaybleTextView.setText(total + " BDT");
        shipmentFeeTextView.setText(shipmentFee + " BDT");
        voucherDiscountTextView.setText(voucherTotal + " BDT");
        voucherDiscountTextView.setText(voucherTotal+" BDT");


        Utility.shoppingCart.orderTotal = (float) subTotal;
        Utility.shoppingCart.totalPrice = (float) total;
        Utility.shoppingCart.productDiscountTotal = (float) discount;
        Utility.shoppingCart.shippingCost = (float) shipmentFee;
        Utility.shoppingCart.voucherDiscount= (float) voucherTotal;
    }

    @Override
    public void onClick(View v) {


        if (v == cartInfoExpandButton) {
            if (checkoutInfoLayout.getVisibility() == View.VISIBLE) {

                cartInfoExpandButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, imageDownArrow, 0);
                this.hideLayout(checkoutInfoLayout, 500);


            } else {

                cartInfoExpandButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, imageUpArrow, 0);

                if (newCustomerInfoLayout.getVisibility() == View.VISIBLE) {
                    this.hideLayout(newCustomerInfoLayout, 100);
                    expandNewCustomerInfoButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, imageDownArrow, 0);
                }
                this.showLayout(checkoutInfoLayout, 500);
            }


        } else if (v == expandNewCustomerInfoButton) {

            if (newCustomerInfoLayout.getVisibility() == View.VISIBLE) {
                expandNewCustomerInfoButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, imageDownArrow, 0);
                this.hideLayout(newCustomerInfoLayout, 500);

            } else {
                expandNewCustomerInfoButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, imageUpArrow, 0);
                if (checkoutInfoLayout.getVisibility() == View.VISIBLE) {
                    this.hideLayout(checkoutInfoLayout, 100);
                    expandNewCustomerInfoButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, imageDownArrow, 0);
                }

                this.showLayout(newCustomerInfoLayout, 500);

            }

        } else if (v == confrimPayemtBtn) {
            if (checkInputValidity()) {
                this.saveInformation();

                CheckoutActivity.tabFlag = 2;
                CheckoutActivity.mViewPager.setCurrentItem(2);

            }
        } else if (v == backToCart) {
            CheckoutActivity.tabFlag = 0;
            CheckoutActivity.mViewPager.setCurrentItem(0);

        } else if (v == addVoucherButton) {



            String voucherCode = voucherCodeTextView.getText().toString();
            if (voucherCode.isEmpty()) {
                MakeToast.showToast(getActivity(), "Voucher Code is empty!");

            } else {

                for (Voucher vou:voucherArrayList){
                    if(vou.voucher_code.equals(voucherCode)){
                        MakeToast.showToast(getActivity(),"You have already added this voucher code");
                        return;
                    }
                }


                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                new GetVoucherDiscountAsynTask(getActivity(),this).execute(voucherCode);
            }
        }
    }


    public void doAllModificationOnValidVoucherCode(Voucher voucher){

        voucherCodeTextView.setText("");
        voucher.percentage=voucher.discount/100;
        voucher.discountAmount=Utility.shoppingCart.orderTotal*voucher.percentage;
        voucherArrayList.add(voucher);

        TextView textView=new TextView(getActivity());
        textView.setText("Voucher: " + voucher.voucher_code + " Amout: " + voucher.discountAmount);
        voucherLayout.addView(textView);

        float voucherSum=0;
        for ( Voucher v:voucherArrayList){
            voucherSum+=v.discountAmount;
        }

        this.voucherTotal =voucherSum;
        voucherDiscountTextView.setText(voucherTotal+" BDT");
        total = (subTotal + shipmentFee) - (discount + voucherTotal);
        totalPaybleTextView.setText(total+"");

        Utility.shoppingCart.voucherDiscount=(float)voucherTotal;
        Utility.shoppingCart.totalPrice= (float) total;


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


    private void saveInformation() {

        checkOutInfoSession.saveDataInPreference(emailText.getText().toString(), fnameText.getText().toString(),
                lnameText.getText().toString(), citySpinner.getSelectedItem().toString(), citySpinner.getSelectedItemPosition(),
                addressText.getText().toString(), telephoneText.getText().toString());


    }

    private boolean checkInputValidity() {
        String email = emailText.getText().toString();
        CharSequence input = email;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        String country = citySpinner.getSelectedItem().toString();

        if (!matcher.matches()) {
            MakeToast.showToast(getActivity(), "Please Enter a valid Email ID");
            emailText.requestFocus();
            return false;

        } else if (fnameText.getText().toString().equals("")) {
            MakeToast.showToast(getActivity(), "First Name is Required");
            fnameText.requestFocus();
            return false;
        } else if (lnameText.getText().toString().equals("")) {
            MakeToast.showToast(getActivity(), "Last Name is Required");
            lnameText.requestFocus();
            return false;
        } else if (country.equals("Country")) {
            MakeToast.showToast(getActivity(), "Please select a country");
            citySpinner.requestFocus();
            return false;
        } else if (addressText.getText().toString().equals("")) {
            MakeToast.showToast(getActivity(), "Delivery Address is required");
            addressText.requestFocus();
            return false;
        } else if (telephoneText.getText().toString().equals("")) {
            MakeToast.showToast(getActivity(), "Telephone Number is required");
            telephoneText.requestFocus();
            return false;
        }


        return true;
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == Utility.deliveryMethods.get(0).id) {
            if (subTotal > Utility.deliveryMethods.get(0).delivery_price_limit) {
                shipmentFee = 0;
            } else {
                shipmentFee = Utility.deliveryMethods.get(0).deliveryPrice;
            }

            total = (subTotal + shipmentFee) - (discount + voucherTotal);
            shipmentFeeTextView.setText(shipmentFee + " BDT");
            totalPaybleTextView.setText(total + " BDT");

            Utility.shoppingCart.orderTotal = (float) subTotal;
            Utility.shoppingCart.shippingCost = (float) shipmentFee;
            DELIVEY_METHOD = checkedId;


        } else if (checkedId == Utility.deliveryMethods.get(1).id) {

            if (subTotal > Utility.deliveryMethods.get(1).delivery_price_limit) {
                shipmentFee = 0;
            } else {
                shipmentFee = Utility.deliveryMethods.get(1).deliveryPrice;
            }

            total = (subTotal + shipmentFee) - (discount + voucherTotal);
            shipmentFeeTextView.setText(shipmentFee + " BDT");
            totalPaybleTextView.setText(total + " BDT");
            Utility.shoppingCart.orderTotal = (float) subTotal;
            Utility.shoppingCart.shippingCost = (float) shipmentFee;
            DELIVEY_METHOD = checkedId;

        }

    }
}
