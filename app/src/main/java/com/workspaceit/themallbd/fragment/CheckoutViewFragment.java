package com.workspaceit.themallbd.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.activity.CheckoutActivity;
import com.workspaceit.themallbd.utility.MakeToast;


public class CheckoutViewFragment extends Fragment {

    public CheckoutViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        CheckoutActivity.continueShoppingButton.setVisibility(View.GONE);
        CheckoutActivity.checkOutButton.setVisibility(View.GONE);
        MakeToast.showToast(getActivity(),"de");


        return inflater.inflate(R.layout.fragment_checkout_view, container, false);


    }



}
