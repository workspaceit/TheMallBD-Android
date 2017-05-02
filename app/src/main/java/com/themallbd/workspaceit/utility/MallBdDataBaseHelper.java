package com.themallbd.workspaceit.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tomal on 7/21/2016.
 */
public class MallBdDataBaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "mallbd";
    private static final int DB_VERSION = 1;


    private static final String TABLE_NAME="cart";
    private static final String PRODUCT_NAME_COL="product_name";
    private static final String QUANTITY_COL="quantity";
    private static final String PRICE_COL="price";
    private static final String IMAGE_NAME_COL="image_name";



   public MallBdDataBaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PRODUCT_NAME_COL+" TEXT, "
                + QUANTITY_COL+" INTEGER, "
                + PRICE_COL+" REAL,"
                +IMAGE_NAME_COL+ " TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertNewData(String pname,int quntity,double price, String image_name){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(PRODUCT_NAME_COL,pname);
        contentValues.put(QUANTITY_COL, quntity);
        contentValues.put(PRICE_COL,price);
        contentValues.put(IMAGE_NAME_COL, image_name);
        long result=sqLiteDatabase.insert(TABLE_NAME,null,contentValues);

        if(result==-1)
            return false;
        else
            return true;


    }
}
