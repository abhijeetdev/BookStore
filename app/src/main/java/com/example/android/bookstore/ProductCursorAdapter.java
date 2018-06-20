package com.example.android.bookstore;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.android.bookstore.data.BookStoreContract;
import com.example.android.bookstore.data.BookStoreContract.BookEntry;

public class ProductCursorAdapter extends CursorAdapter {

    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.summary);

        int nameColumnIndex = cursor.getColumnIndex(BookEntry.PRODUCT_NAME);
        int supplierColumnIndex = cursor.getColumnIndex(BookEntry.SUPPLIER_NAME);

        String productName = cursor.getString(nameColumnIndex);
        String supplierName= cursor.getString(supplierColumnIndex);

        nameTextView.setText(productName);
        summaryTextView.setText(supplierName);

    }
}
