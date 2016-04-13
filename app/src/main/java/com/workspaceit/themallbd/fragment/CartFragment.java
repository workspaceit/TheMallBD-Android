package com.workspaceit.themallbd.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.activity.CheckoutActivity;
import com.workspaceit.themallbd.activity.MainActivity;
import com.workspaceit.themallbd.adapter.CartInListViewAdapter;
import com.workspaceit.themallbd.adapter.CategoryInListViewAdapter;
import com.workspaceit.themallbd.utility.MakeToast;
import com.workspaceit.themallbd.utility.Utility;

public class CartFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener{

    private ListView cartListView;

    private CartInListViewAdapter cartInListViewAdapter;
    public Button checkOutButton;
    public Button continueShoppingButton;



    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        cartListView = (ListView) view.findViewById(R.id.cart_lv);

        checkOutButton=(Button)view.findViewById(R.id.checkOutButton);
        checkOutButton.setOnClickListener(this);
        continueShoppingButton=(Button)view.findViewById(R.id.continueShoppingButton);
        continueShoppingButton.setOnClickListener(this);




        cartListView.setOnItemClickListener(this);
        if (Utility.shoppingCart.shoppingCartCell.size()>0) {
            cartInListViewAdapter = new CartInListViewAdapter(getActivity(), Utility.shoppingCart);
            cartListView.setAdapter(cartInListViewAdapter);
        }
        return view;




    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    @Override
    public void onClick(View v) {
        if(v == continueShoppingButton)
        {
            getActivity().finish();
            Intent intent=new Intent(getActivity(),MainActivity.class);
            startActivity(intent);


        }
        else if(v == checkOutButton)
        {

            if(Utility.shoppingCart.shoppingCartCell.size()<1){
                MakeToast.showToast(getActivity(),"Your shopping cart is empty!");

            }else {

                CheckoutActivity.tabFlag = 1;
                CheckoutActivity.mViewPager.setCurrentItem(1);
            }
        }
    }
}
