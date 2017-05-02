package com.themallbd.workspaceit.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.themallbd.workspaceit.asynctask.GetAllPaymentMethodAsynTask;
import com.themallbd.workspaceit.asynctask.SubmitChectoutAsyntask;
import com.themallbd.workspaceit.preferences.CheckOutInfoSession;
import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;

public class PaymentFragment extends Fragment implements View.OnClickListener,RadioGroup.OnCheckedChangeListener {
    private CheckOutInfoSession checkOutInfoSession;
    private RadioGroup radioPaymentGroup;

    private Button confrimOrderButton;
    private Gson gson;
    public static int PAYMENT_ID;
    private final String orderForm="ANDROID";
    private TextView textView;





    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        // Inflate the layout for this fragment
        checkOutInfoSession=new CheckOutInfoSession(getActivity());


        confrimOrderButton=(Button)view.findViewById(R.id.confrim_final_order_button);

        confrimOrderButton.setOnClickListener(this);
        gson=new Gson();
        radioPaymentGroup=(RadioGroup)view.findViewById(R.id.radio_payment_method_group);
        radioPaymentGroup.setOnCheckedChangeListener(this);

        if (Utility.paymentMethodses.size() < 1) {
            new GetAllPaymentMethodAsynTask(this).execute();
        }else {
            initializeRadioButton();
        }

        textView=(TextView)view.findViewById(R.id.debug);
        return view;
    }



    private void initializeRadioButton(){


        for (int i=0; i<Utility.paymentMethodses.size(); i++){

            RadioButton radioButton=new RadioButton(getActivity());
            radioButton.setId(Utility.paymentMethodses.get(i).id);
            radioButton.setText(Utility.paymentMethodses.get(i).methodTitle);
            radioPaymentGroup.addView(radioButton);


        }

        radioPaymentGroup.check(Utility.paymentMethodses.get(0).id);
        PAYMENT_ID=1;
    }




    @Override
    public void onClick(View v) {




            if (v==confrimOrderButton){

                String message="";
                int id=radioPaymentGroup.getCheckedRadioButtonId();
                if (id==1){
                    message="Are you Confirming Cash On Delivery?";
                }else if(id==2){
                    message="Are you sure to pay with Bkash?";
                }else if (id==3){
                    message="Are you sure to pay with Paypal?";
                }else if (id==4){
                    message="Are you sure to pay with Walletmix?";
                }


                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Light_Dialog_Alert);
                alertDialogBuilder.setTitle("Payment");
                alertDialogBuilder
                        .setMessage(message)
                        .setCancelable(false)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                SubmitChectoutAsyntask submitChectoutAsyntask=new SubmitChectoutAsyntask(PaymentFragment.this,getActivity(),
                                        checkOutInfoSession.getFname(),checkOutInfoSession.getLname(),checkOutInfoSession.getEmail(),
                                        checkOutInfoSession.getTelephone(),checkOutInfoSession.getAddress(),checkOutInfoSession.getCity(),
                                        orderForm,String.valueOf(checkOutInfoSession.checkGiftIsset()),checkOutInfoSession.getGiftFname(),
                                        checkOutInfoSession.getPrefGiftLastName(),checkOutInfoSession.getPrefGiftTelephone(),
                                        checkOutInfoSession.getPrefGiftAddressIsset(),checkOutInfoSession.getPrefGiftCity(),
                                        String.valueOf(CheckoutViewFragment.DELIVEY_METHOD),String.valueOf(radioPaymentGroup.getCheckedRadioButtonId()),
                                        "1",gson.toJson(Utility.voucherDiscounts),
                                        String.valueOf(CheckoutViewFragment.purchaseDiscountAmount),
                                        gson.toJson(Utility.customerPurchaseDiscount),gson.toJson(Utility.shoppingCart));

                                submitChectoutAsyntask.execute();


                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                    }
                });

                android.app.AlertDialog alertDialog = alertDialogBuilder.create();


                alertDialog.show();







            }


    }

    public void setRadioButton(){
        initializeRadioButton();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        PAYMENT_ID=checkedId;

    }
}
