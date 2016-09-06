package com.themallbd.workspaceit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.themallbd.workspaceit.dataModel.ProductCell;
import com.themallbd.workspaceit.dataModel.SelectedAttributes;
import com.themallbd.workspaceit.preferences.LocalShoppintCart;
import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;
import com.themallbd.workspaceit.adapter.WishInListViewAdapter;
import com.themallbd.workspaceit.asynctask.GetWishListProductAsynTask;

public class WishListActivity extends BaseActivityWithoutDrawer implements AdapterView.OnItemClickListener {

    ListView wishListListView;
    WishInListViewAdapter wishInListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);


        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar);



        wishListListView=(ListView)findViewById(R.id.wish_list_listview);
        wishInListViewAdapter=new WishInListViewAdapter(this);
        wishListListView.setAdapter(wishInListViewAdapter);
        wishListListView.setOnItemClickListener(this);
        new GetWishListProductAsynTask(this).execute();
    }


    public Boolean addProductTotheCart(int position){
        for(int i=0; i< Utility.shoppingCart.productCell.size();i++){
            if(Utility.shoppingCart.productCell.get(i).id==Utility.wishlistProductArrayList.get(position).id){
                Utility.shoppingCart.productCell.get(i).quantity+=1;
                invalidateOptionsMenu();
                updateCart();
                return false;
            }

        }

        ProductCell productCell=new ProductCell();

        if(Utility.wishlistProductArrayList.get(position).attributes.size()<0) {
            SelectedAttributes selectedAttributes = new SelectedAttributes();
            selectedAttributes.setId(Utility.wishlistProductArrayList.get(position).attributes.get(0).id);
            selectedAttributes.setName(Utility.wishlistProductArrayList.get(position).attributes.get(0).name);
            selectedAttributes.setValue(Utility.wishlistProductArrayList.get(position).attributes.get(0).attributesValue.get(0).value);
            productCell.addToSelectedAttributes(selectedAttributes);
        }


        productCell.setId(Utility.wishlistProductArrayList.get(position).id);
        productCell.setProduct(Utility.wishlistProductArrayList.get(position));
        productCell.setQuantity(1);



        Utility.shoppingCart.productCell.add(productCell);
        invalidateOptionsMenu();
        updateCart();
        return true;
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

    public void changeAdapterState(){


            wishInListViewAdapter.notifyDataSetChanged();

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,ProductDetailsActivity.class);
        intent.putExtra("position",position);
        intent.putExtra("productArray",5);
        startActivity(intent);

    }
}
