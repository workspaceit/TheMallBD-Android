package com.themallbd.workspaceit.fragment;

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
import com.themallbd.workspaceit.asynctask.SubmitChectoutAsyntask;
import com.themallbd.workspaceit.utility.CheckOutInfoSession;
import com.themallbd.workspaceit.utility.MakeToast;
import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;

public class PaymentFragment extends Fragment implements View.OnClickListener {
    private CheckOutInfoSession checkOutInfoSession;
    private RadioGroup radioDeliveryMethodGroup,radioPaymentGroup;
    private TextView firstDeliveryPrice,seocndDeliveryPrice;
    private Button confrimOrderButton;
    private Gson gson;




    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        // Inflate the layout for this fragment
        checkOutInfoSession=new CheckOutInfoSession(getActivity());
        radioDeliveryMethodGroup=(RadioGroup)view.findViewById(R.id.radio_delivery_method_group);
        firstDeliveryPrice=(TextView)view.findViewById(R.id.delivery_price_first);
        seocndDeliveryPrice=(TextView)view.findViewById(R.id.dekivery_payment_second);
        confrimOrderButton=(Button)view.findViewById(R.id.confrim_final_order_button);

        confrimOrderButton.setOnClickListener(this);
        gson=new Gson();
        radioPaymentGroup=(RadioGroup)view.findViewById(R.id.radio_payment_method_group);


        initializeRadioButton();
        return view;
    }

    private void initializeRadioButton(){
        for (int i=0; i<Utility.deliveryMethods.size(); i++) {
            RadioButton radioButton = new RadioButton(getActivity());
            radioButton.setId(Utility.deliveryMethods.get(i).id);
            radioButton.setText(Utility.deliveryMethods.get(i).title);
            radioDeliveryMethodGroup.addView(radioButton);
        }

        radioDeliveryMethodGroup.check(Utility.deliveryMethods.get(0).id);
        firstDeliveryPrice.setText(Utility.deliveryMethods.get(0).deliveryPrice + "");
        seocndDeliveryPrice.setText(Utility.deliveryMethods.get(1).deliveryPrice+"");

        for (int i=0; i<Utility.paymentMethodses.size(); i++){

            RadioButton radioButton=new RadioButton(getActivity());
            radioButton.setId(Utility.paymentMethodses.get(i).id);
            radioButton.setText(Utility.paymentMethodses.get(i).methodTitle);
            radioPaymentGroup.addView(radioButton);

        }

        radioPaymentGroup.check(Utility.paymentMethodses.get(0).id);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            MakeToast.showToast(getActivity(), Utility.deliveryMethods.size()+" "+Utility.paymentMethodses.size());
        }

    }

    @Override
    public void onClick(View v) {
            if (v==confrimOrderButton){
                new SubmitChectoutAsyntask(getActivity()).execute(checkOutInfoSession.getFname(),checkOutInfoSession.getLname(),
                        checkOutInfoSession.getEmail(),checkOutInfoSession.getTelephone(),checkOutInfoSession.getAddress(),
                        checkOutInfoSession.getCity(), "ANDROID",checkOutInfoSession.getAddress(),"Bangladesh", "",
                        checkOutInfoSession.getCity(),String.valueOf(radioDeliveryMethodGroup.getCheckedRadioButtonId()),
                        String.valueOf(radioPaymentGroup.getCheckedRadioButtonId()),"1",gson.toJson(Utility.shoppingCart));


            }


    }



}
