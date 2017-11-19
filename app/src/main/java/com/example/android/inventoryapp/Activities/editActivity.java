package com.example.android.inventoryapp.Activities;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.inventoryapp.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.ID;
import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.INVENTORY_CATEGORY;
import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.INVENTORY_IMAGE;
import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.INVENTORY_NAME;
import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.INVENTORY_PRICE;
import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.INVENTORY_QUANTITY;
import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.INVENTORY_URI;

public class editActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final int PICK_IMAGE = 1;
    EditText name;
    EditText category;
    EditText quantity;
    EditText price;
    Button edit;
    ImageView editImage;
    long invetoryID;
    String picturePath = "djgiodfj";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        name = findViewById(R.id.edit_name);
        category = findViewById(R.id.edit_category);
        quantity = findViewById(R.id.edit_quantity);
        price = findViewById(R.id.edit_price);
        edit = findViewById(R.id.edit_button);
        editImage = findViewById(R.id.edit_myImage);

        final long invetoryID = getIntent().getLongExtra(ID, -1);

        Cursor cursor = getContentResolver().query(ContentUris.withAppendedId(INVENTORY_URI, invetoryID), null, null, null, null);
        if (cursor.moveToNext()) {
            String Name = cursor.getString(cursor.getColumnIndex(INVENTORY_NAME));
            String Category = cursor.getString(cursor.getColumnIndex(INVENTORY_CATEGORY));
            int Quantity = cursor.getInt(cursor.getColumnIndex(INVENTORY_QUANTITY));
            int Price = cursor.getInt(cursor.getColumnIndex(INVENTORY_PRICE));


            name.setText(Name);
            category.setText(Category);
            quantity.setText(String.valueOf(Quantity));
            price.setText(String.valueOf(Price));
            Log.v("Path", cursor.getString(cursor.getColumnIndex(INVENTORY_IMAGE)));
            Glide.with(editActivity.this).load(String.valueOf(cursor.getString(cursor.getColumnIndex(INVENTORY_IMAGE)))).into(editImage);

        }

        cursor.close();
        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(INVENTORY_NAME, name.getText().toString());
                contentValues.put(INVENTORY_CATEGORY, category.getText().toString());
                contentValues.put(INVENTORY_QUANTITY, quantity.getText().toString());
                contentValues.put(INVENTORY_PRICE, price.getText().toString());
                contentValues.put(INVENTORY_IMAGE, picturePath);
                String[] args = {String.valueOf(invetoryID)};
                getContentResolver().update(INVENTORY_URI, contentValues, ID + "= ?", args);
                Toast.makeText(editActivity.this, "The element  " + "(" + invetoryID + ")" + " is" + " UpDated", Toast.LENGTH_SHORT).show();
                editActivity.this.getContentResolver().notifyChange(INVENTORY_URI, null);
                finish();

            }

        });

        getSupportLoaderManager().initLoader(0, null, this);

    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        Uri uri = Uri.withAppendedPath(INVENTORY_URI, String.valueOf(invetoryID));
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
            Picasso.with(this).load(cursor.getString(cursor.getColumnIndex(INVENTORY_IMAGE))).into(editImage);

        }

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    public void addImage(View view) {
        EasyImage.openGallery(editActivity.this, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, editActivity.this, new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                File imFile = imageFiles.get(0);
                picturePath = imFile.getAbsolutePath();

                Picasso.with(editActivity.this).load(new File(picturePath)).into(editImage);
            }


            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                super.onImagePickerError(e, source, type);
                Toast.makeText(editActivity.this, "There is an Error", Toast.LENGTH_SHORT).show();

            }

        });

    }
}
