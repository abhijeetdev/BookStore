package com.example.android.bookstore;

import android.content.Intent;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import com.example.android.bookstore.data.BookStoreContract.BookEntry;

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_PRODUCT_LOADER = 1;
    Uri mCurrentProductUri;

    private EditText mName;
    private EditText mPrice;
    private EditText mQuntity;
    private EditText mSupplierName;
    private EditText mSupplierPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mName = findViewById(R.id.edit_product_name);
        mPrice = findViewById(R.id.edit_product_price);
        mQuntity = findViewById(R.id.edit_product_quantity);
        mSupplierName = findViewById(R.id.edit_supplier_name);
        mSupplierPhone = findViewById(R.id.edit_supplier_phone);


        Intent intent = getIntent();
        mCurrentProductUri = intent.getData();

        if (mCurrentProductUri == null) {
            setTitle(getString(R.string.editor_activity_title_add_product));
        }else  {
            setTitle(getString(R.string.editor_activity_title_edit_product));
            getLoaderManager().initLoader(EXISTING_PRODUCT_LOADER, null, this);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.editor_action_save:

                return true;
            case  R.id.editor_action_delete:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {BookEntry._ID, BookEntry.PRODUCT_NAME, BookEntry.PRICE, BookEntry.QUANTITY, BookEntry.SUPPLIER_NAME, BookEntry.SUPPLIER_PHONE_NUMBER};
        return new CursorLoader(this, mCurrentProductUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex(BookEntry.PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(BookEntry.PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(BookEntry.QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(BookEntry.SUPPLIER_NAME);
            int supplierPhoneNumberColumnIndex = cursor.getColumnIndex(BookEntry.SUPPLIER_PHONE_NUMBER);

            String name = cursor.getString(nameColumnIndex);
            String price = cursor.getString(priceColumnIndex);
            String quntity = cursor.getString(quantityColumnIndex);
            String supplierName = cursor.getString(supplierNameColumnIndex);
            String supplierPhone = cursor.getColumnName(supplierPhoneNumberColumnIndex);

            mName.setText(name);
            mPrice.setText(price);
            mQuntity.setText(quntity);
            mSupplierName.setText(supplierName);
            mSupplierPhone.setText(supplierPhone);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
