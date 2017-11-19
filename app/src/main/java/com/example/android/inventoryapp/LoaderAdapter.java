package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.ID;
import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.INVENTORY_CATEGORY;
import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.INVENTORY_IMAGE;
import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.INVENTORY_NAME;
import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.INVENTORY_QUANTITY;
import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.INVENTORY_URI;

/**
 * Created by Ahmad Siafaddin on 11/11/2017.
 */

public class LoaderAdapter extends CursorAdapter {

    int inventoryQuantity;

    public LoaderAdapter(Context context) {
        super(context, null, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.item_inventory, null);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        TextView name = view.findViewById(R.id.inventory_name);
        final TextView quantity = view.findViewById(R.id.inventory_quantity);
        TextView category = view.findViewById(R.id.inventory_category);
        ImageView img = view.findViewById(R.id.inventory_img);
        final ImageButton imgButton = view.findViewById(R.id.sale);

        imgButton.setFocusable(false);
        final long inventoryID = cursor.getInt(cursor.getColumnIndex(ID));


        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inventoryQuantity = Integer.parseInt(quantity.getText().toString()) - 1;
                if (inventoryQuantity >= 0) {

                    quantity.setText(String.valueOf(inventoryQuantity));
                    ContentValues values = new ContentValues();
                    values.put(INVENTORY_QUANTITY, inventoryQuantity);

                    String[] args = {String.valueOf(inventoryID)};
                    context.getContentResolver().update(INVENTORY_URI, values, ID + "= ?", args);
                } else {
                    imgButton.setEnabled(false);
                    Toast.makeText(context, "Nothing More", Toast.LENGTH_SHORT).show();
                }
            }
        });

        name.setText(cursor.getString(cursor.getColumnIndex(INVENTORY_NAME)));
        category.setText(cursor.getString(cursor.getColumnIndex(INVENTORY_CATEGORY)));
        quantity.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(INVENTORY_QUANTITY))));
        Glide.with(context).load(String.valueOf(cursor.getString(cursor.getColumnIndex(INVENTORY_IMAGE)))).into(img);
    }

}
