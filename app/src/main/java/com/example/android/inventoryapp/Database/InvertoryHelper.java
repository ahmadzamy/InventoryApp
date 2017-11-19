package com.example.android.inventoryapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.*;

/**
 * Created by Ahmad Siafaddin on 11/11/2017.
 */

public class InvertoryHelper extends SQLiteOpenHelper {
    public InvertoryHelper(Context context) {
       super(context,"Inventory.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY, " + INVENTORY_NAME + " TEXT," + INVENTORY_CATEGORY + " TEXT, " + INVENTORY_PRICE + " INTEGER," + INVENTORY_QUANTITY + " INTEGER ," + INVENTORY_IMAGE + " TEXT " +")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
