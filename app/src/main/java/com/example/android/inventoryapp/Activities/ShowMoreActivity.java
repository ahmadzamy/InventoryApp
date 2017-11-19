package com.example.android.inventoryapp.Activities;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.inventoryapp.R;

import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.ID;
import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.INVENTORY_CATEGORY;
import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.INVENTORY_IMAGE;
import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.INVENTORY_NAME;
import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.INVENTORY_PRICE;
import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.INVENTORY_QUANTITY;
import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.INVENTORY_URI;

public class ShowMoreActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    TextView name;
    TextView quantity;
    TextView category;
    TextView price;
    Button add;
    Button remove;
    Button order;
    Button goEdit;
    Button delete;
    ImageView img;
    long myID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_more);

        name = findViewById(R.id.name);
        quantity = findViewById(R.id.quantity);
        category = findViewById(R.id.category);
        price = findViewById(R.id.price);
        add = findViewById(R.id.add_quan);
        remove = findViewById(R.id.remove_quan);
        order = findViewById(R.id.order);
        goEdit = findViewById(R.id.go_to_edit);
        delete = findViewById(R.id.delete);
        img = findViewById(R.id.image);

        myID = getIntent().getLongExtra(ID, -1);

        getSupportLoaderManager().initLoader(0, null, this);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int inventoryQuantity = Integer.parseInt(quantity.getText().toString()) + 1;
                quantity.setText(String.valueOf(inventoryQuantity));
                ContentValues values = new ContentValues();
                values.put(INVENTORY_QUANTITY, inventoryQuantity);

                String[] args = {String.valueOf(myID)};
                ShowMoreActivity.this.getContentResolver().update(INVENTORY_URI, values, ID + "= ?", args);
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int inventoryQuantity = Integer.parseInt(quantity.getText().toString()) - 1;
                if (inventoryQuantity >= 0) {

                    quantity.setText(String.valueOf(inventoryQuantity));
                    ContentValues values = new ContentValues();
                    values.put(INVENTORY_QUANTITY, inventoryQuantity);

                    String[] args = {String.valueOf(myID)};
                    ShowMoreActivity.this.getContentResolver().update(INVENTORY_URI, values, ID + "= ?", args);
                } else {
                    remove.setEnabled(false);
                    Toast.makeText(ShowMoreActivity.this, "Nothing More", Toast.LENGTH_SHORT).show();
                }
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, "ahmadsaifaddin43@gamil.com");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Order");
                intent.putExtra(Intent.EXTRA_TEXT, INVENTORY_NAME + " " + INVENTORY_CATEGORY);
                startActivity(intent);
            }
        });

        goEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowMoreActivity.this, editActivity.class);
                intent.putExtra(ID, myID);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(ShowMoreActivity.this).setTitle("Are you sure").setMessage("it is delete the item completly").setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String[] args = {String.valueOf(myID)};
                        ShowMoreActivity.this.getContentResolver().delete(INVENTORY_URI, ID + "= ?", args);
                        finish();
                    }
                }).setNegativeButton("No", null).show();


            }
        });
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        Uri uri = Uri.withAppendedPath(INVENTORY_URI, String.valueOf(myID));
        return new CursorLoader(this, uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToNext()) {
            String Name = cursor.getString(cursor.getColumnIndex(INVENTORY_NAME));
            String Category = cursor.getString(cursor.getColumnIndex(INVENTORY_CATEGORY));
            int Quantity = cursor.getInt(cursor.getColumnIndex(INVENTORY_QUANTITY));
            int Price = cursor.getInt(cursor.getColumnIndex(INVENTORY_PRICE));


            name.setText(Name);
            category.setText(Category);
            quantity.setText(String.valueOf(Quantity));
            price.setText(String.valueOf(Price));
            Glide.with(this).load(cursor.getString(cursor.getColumnIndex(INVENTORY_IMAGE))).into(img);

        }

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
