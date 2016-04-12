package com.workspaceit.themallbd.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.adapter.WishInListViewAdapter;
import com.workspaceit.themallbd.asynctask.GetNewProductsAsyncTask;
import com.workspaceit.themallbd.asynctask.GetWishListProductAsynTask;
import com.workspaceit.themallbd.dataModel.SelectedAttributes;
import com.workspaceit.themallbd.dataModel.ShoppingCartCell;
import com.workspaceit.themallbd.utility.MakeToast;
import com.workspaceit.themallbd.utility.Utility;

public class WishListActivity extends BaseActivityWithoutDrawer implements AdapterView.OnItemClickListener {

    ListView wishListListView;
    WishInListViewAdapter wishInListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Wishlist");


        wishListListView=(ListView)findViewById(R.id.wish_list_listview);
        wishInListViewAdapter=new WishInListViewAdapter(this);
        wishListListView.setAdapter(wishInListViewAdapter);
        wishListListView.setOnItemClickListener(this);
        new GetWishListProductAsynTask(this).execute();
    }


    public Boolean addProductTotheCart(int position){
        for(int i=0; i<Utility.shoppingCart.shoppingCartCell.size();i++){
            if(Utility.shoppingCart.shoppingCartCell.get(i).id==Utility.wishlistProductArrayList.get(position).id){
                Utility.shoppingCart.shoppingCartCell.get(i).quantity+=1;
                invalidateOptionsMenu();
                return false;
            }

        }

        ShoppingCartCell shoppingCartCell = new ShoppingCartCell();

        if(Utility.wishlistProductArrayList.get(position).attributes.size()<0) {
            SelectedAttributes selectedAttributes = new SelectedAttributes();
            selectedAttributes.setId(Utility.wishlistProductArrayList.get(position).attributes.get(0).id);
            selectedAttributes.setName(Utility.wishlistProductArrayList.get(position).attributes.get(0).name);
            selectedAttributes.setValue(Utility.wishlistProductArrayList.get(position).attributes.get(0).attributesValue.get(0).value);
            shoppingCartCell.addToSelectedAttributes(selectedAttributes);
        }


        shoppingCartCell.setId(Utility.wishlistProductArrayList.get(position).id);
        shoppingCartCell.setProduct(Utility.wishlistProductArrayList.get(position));
        shoppingCartCell.setQuantity(1);



        Utility.shoppingCart.addToShoppingCart(shoppingCartCell);
        invalidateOptionsMenu();
        return true;
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
