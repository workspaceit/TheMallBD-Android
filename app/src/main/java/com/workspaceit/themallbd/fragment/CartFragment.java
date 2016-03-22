package com.workspaceit.themallbd.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.adapter.CartInListViewAdapter;
import com.workspaceit.themallbd.adapter.CategoryInListViewAdapter;
import com.workspaceit.themallbd.utility.Utility;

public class CartFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView cartListView;

    private CartInListViewAdapter cartInListViewAdapter;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        cartListView = (ListView) view.findViewById(R.id.cart_lv);
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
}
