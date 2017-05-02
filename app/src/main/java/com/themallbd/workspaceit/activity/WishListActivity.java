package com.themallbd.workspaceit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;
import com.themallbd.workspaceit.asynctask.WishListDeleteProductAsynTask;
import com.themallbd.workspaceit.dataModel.ProductCell;
import com.themallbd.workspaceit.dataModel.SelectedAttributes;
import com.themallbd.workspaceit.preferences.LocalShoppintCart;
import com.themallbd.workspaceit.utility.MakeToast;
import com.themallbd.workspaceit.utility.Utility;
import com.workspaceit.themall.R;
import com.themallbd.workspaceit.adapter.WishInListViewAdapter;
import com.themallbd.workspaceit.asynctask.GetWishListProductAsynTask;

public class WishListActivity extends BaseActivityWithoutDrawer implements AdapterView.OnItemClickListener {

    private SwipeMenuListView wishListListView;
    private WishInListViewAdapter wishInListViewAdapter;
    private int arrayPosition=0;
    private View root;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);


        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar);
        this.root=findViewById(R.id.activity_wish_list);



        wishListListView=(SwipeMenuListView) findViewById(R.id.wish_list_listview);
        wishInListViewAdapter=new WishInListViewAdapter(this);
        wishListListView.setAdapter(wishInListViewAdapter);
        wishListListView.setOnItemClickListener(this);
        new GetWishListProductAsynTask(this).execute();


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {




                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                deleteItem.setBackground(R.color.colorPrimaryDark);
                // set item width
                deleteItem.setWidth(100);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        wishListListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        wishListListView.setMenuCreator(creator);

        wishListListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index){
                    case 0:
                        arrayPosition=position;
                        new WishListDeleteProductAsynTask(WishListActivity.this).execute(
                                String.valueOf(Utility.wishlistProductArrayList.get(position).id));

                        break;

                }

                return true;
            }
        });
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

    public void deleteProductNotify(boolean flag){
        if (flag){
            MakeToast.snackBarShow(root,"Product Deleted Successfully from your Wishlist");
            Utility.wishlistProductArrayList.remove(arrayPosition);
            wishInListViewAdapter.notifyDataSetChanged();

        }else {
            MakeToast.snackBarShow(root,"Try Again Later...");
        }

    }
}
