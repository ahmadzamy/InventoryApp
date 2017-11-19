package com.example.android.inventoryapp.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.inventoryapp.R;

import java.io.File;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.*;
public class AddActivity extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    EditText quantity;
    EditText catagory;
    Button add;
    EditText name;
    EditText price;
    ImageView img;

    String picturePath = "djgiodfj";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        quantity = findViewById(R.id.add_quantity);
        catagory = findViewById(R.id.add_category);
        add = findViewById(R.id.add_button);
        name = findViewById(R.id.add_name);
        price = findViewById(R.id.add_price);
        img = findViewById(R.id.myImage);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put(INVENTORY_NAME, name.getText().toString());
                values.put(INVENTORY_CATEGORY, catagory.getText().toString());
                values.put(INVENTORY_PRICE, price.getText().toString());
                values.put(INVENTORY_QUANTITY, quantity.getText().toString());
                values.put(INVENTORY_IMAGE, picturePath);

                getContentResolver().insert(INVENTORY_URI, values);
                Toast.makeText(AddActivity.this, "You Added An Element", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void addImage(View view) {
        EasyImage.openGallery(AddActivity.this, PICK_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, AddActivity.this, new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                File imFile = imageFiles.get(0);
                picturePath = imFile.getAbsolutePath();

                Glide.with(AddActivity.this).load(new File(picturePath)).into(img);
            }


            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                super.onImagePickerError(e, source, type);
                Toast.makeText(AddActivity.this, "There is an Error", Toast.LENGTH_SHORT).show();

            }

        });

    }
}