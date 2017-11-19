package com.example.android.inventoryapp.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.inventoryapp.LoaderAdapter;
import com.example.android.inventoryapp.R;

import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.ID;
import static com.example.android.inventoryapp.Database.InventoryContract.InventoryContractDatas.INVENTORY_URI;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    LoaderAdapter adapter;
    ListView listView;
    FloatingActionButton goToaddActivity;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goToaddActivity = findViewById(R.id.goto_add_Activity);
        listView = findViewById(R.id.list_item);

        adapter = new LoaderAdapter(this);
        listView.setAdapter(adapter);

        goToaddActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ShowMoreActivity.class);
                intent.putExtra(ID, l);
                startActivity(intent);
            }
        });
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(this, INVENTORY_URI, null, null, null, null);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
