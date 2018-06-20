package com.example.android.bookstore.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.bookstore.data.BookStoreContract.BookEntry;

public final class BookStoreDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bookstore.db";
    private static final int DATABASE_VERSION = 1;

    public BookStoreDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_BOOK_STORE_TABLE = "CREATE TABLE " + BookEntry.TABLE_NAME + "("
                + BookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + BookEntry.PRODUCT_NAME + " TEXT NOT NULL,"
                + BookEntry.PRICE + " INTEGER NOT NULL,"
                + BookEntry.QUANTITY + " INTEGER NOT NULL DEFAULT 0,"
                + BookEntry.SUPPLIER_NAME + " TEXT NOT NULL,"
                + BookEntry.SUPPLIER_PHONE_NUMBER + " TEXT);";
        db.execSQL(SQL_CREATE_BOOK_STORE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase dp, int i, int i1) {

    }
}
