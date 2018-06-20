package com.example.android.bookstore;

import android.app.LoaderManager;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.android.bookstore.data.BookStoreContract.BookEntry;
import com.example.android.bookstore.data.BookStoreDbHelper;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int PRODUCTS_LOADER = 0;
    private BookStoreDbHelper mDbHelper;
    private ProductCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new BookStoreDbHelper(this);

        ListView productListView = (ListView) findViewById(R.id.list_view);
        mAdapter = new ProductCursorAdapter(this, null);
        productListView.setAdapter(mAdapter);

        View emptyView = findViewById(R.id.empty_view);
        productListView.setEmptyView(emptyView);
        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                Uri currentProductUri = ContentUris.withAppendedId(BookEntry.CONTENT_URI, id);
                intent.setData(currentProductUri);
                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertData();
                return true;
            case R.id.action_delete_all_entries:
                deleteData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertData(){

        ContentValues values = new ContentValues();
        values.put(BookEntry.PRODUCT_NAME, "Mobile");
        values.put(BookEntry.PRICE, 250);
        values.put(BookEntry.QUANTITY, 100);
        values.put(BookEntry.SUPPLIER_NAME, "MOTO");
        values.put(BookEntry.SUPPLIER_PHONE_NUMBER, "123456789");

        Uri newUri = getContentResolver().insert(BookEntry.CONTENT_URI, values);

        if (newUri == null) {
            Toast.makeText(this, getString(R.string.insert_book_failed),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.insert_book_successful),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteData() {
        getContentResolver().delete(BookEntry.CONTENT_URI, null, null);
    }

    private Cursor queryData(){

        String[] projection = {BookEntry._ID, BookEntry.PRODUCT_NAME, BookEntry.PRICE, BookEntry.QUANTITY, BookEntry.SUPPLIER_NAME, BookEntry.SUPPLIER_PHONE_NUMBER};

        Cursor cursor = getContentResolver().query(BookEntry.CONTENT_URI, projection, null, null, null);
        return cursor;
    }

    private void show(Cursor cursor) {

        ListView productListView = (ListView) findViewById(R.id.list_view);
        mAdapter = new ProductCursorAdapter(this, cursor);
        productListView.setAdapter(mAdapter);

        View emptyView = findViewById(R.id.empty_view);
        productListView.setEmptyView(emptyView);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {BookEntry._ID, BookEntry.PRODUCT_NAME, BookEntry.PRICE, BookEntry.QUANTITY, BookEntry.SUPPLIER_NAME, BookEntry.SUPPLIER_PHONE_NUMBER};

         return new CursorLoader(this,
                 BookEntry.CONTENT_URI,
                 projection, null,
                 null,
                 null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}