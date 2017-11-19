package com.example.android.inventoryapp.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.example.android.inventoryapp.Database.InventoryContract.AUTHORITY;
import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.ID;
import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.INVENTORY_CATEGORY;
import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.INVENTORY_IMAGE;
import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.INVENTORY_NAME;
import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.INVENTORY_PRICE;
import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.INVENTORY_QUANTITY;
import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.TABLE_NAME;

/**
 * Created by Ahmad Siafaddin on 11/11/2017.
 */

public class InventoryProvider extends ContentProvider {
    InvertoryHelper helper;

    public static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        matcher.addURI(AUTHORITY, TABLE_NAME, 1);
        matcher.addURI(AUTHORITY, TABLE_NAME + "/#", 2);
    }

    @Override
    public boolean onCreate() {
        helper = new InvertoryHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        int code = matcher.match(uri);
        Cursor cursor = null;
        String[] coumns = {ID, INVENTORY_NAME, INVENTORY_CATEGORY, INVENTORY_PRICE, INVENTORY_QUANTITY, INVENTORY_IMAGE};
        if (code == 1) {
            cursor = helper.getReadableDatabase().query(TABLE_NAME, coumns, null, null, null, null, null);
        } else {
            long id = ContentUris.parseId(uri);
            cursor = helper.getReadableDatabase().query(TABLE_NAME, coumns, ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        helper.getWritableDatabase().insert(TABLE_NAME, null, contentValues);
        getContext().getContentResolver().notifyChange(uri, null);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        helper.getWritableDatabase().delete(TABLE_NAME, s, strings);
        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        helper.getWritableDatabase().update(TABLE_NAME, contentValues, s, strings);
        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }
}
