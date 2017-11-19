package com.example.android.inventoryapp.Database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Ahmad Siafaddin on 11/11/2017.
 */

public class InventoryContract {

    public InventoryContract() {
    }
    public static final String AUTHORITY = "com.example.android.inventoryapp";


    public static class InventoryContractDatas implements BaseColumns {

        public static final String TABLE_NAME = "Inventories";
        public static final String ID = BaseColumns._ID;
        public static final String INVENTORY_NAME = "name";
        public static final String INVENTORY_QUANTITY = "quantity";
        public static final String INVENTORY_IMAGE = "image";
        public static final String INVENTORY_CATEGORY = "category";
        public static final String INVENTORY_PRICE = "price";
        public static final Uri INVENTORY_URI=Uri.parse("content://"+AUTHORITY+"/"+ TABLE_NAME);
    }
}
