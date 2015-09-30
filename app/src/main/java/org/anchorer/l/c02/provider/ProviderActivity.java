package org.anchorer.l.c02.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.anchorer.l.c.Const;
import org.anchorer.l.c02.aidl.Book;

/**
 * Created by Anchorer/duruixue on 2015/9/30.
 */
public class ProviderActivity extends AppCompatActivity {

    private final String FIELD_ID = "_id";
    private final String FIELD_NAME = "_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri bookUri = Uri.parse("content://org.anchorer.l.c02.provider.BOOK_PROVIDER/book");

        // query books
        Cursor cursor = getContentResolver().query(bookUri, new String[]{FIELD_ID, FIELD_NAME}, null, null, null);
        while (cursor.moveToNext()) {
            Book book = new Book(cursor.getInt(cursor.getColumnIndex(FIELD_ID)), cursor.getString(cursor.getColumnIndex(FIELD_NAME)));
            Log.d(Const.LOG, "Book Queried: " + book);
        }

        // insert book
        ContentValues values = new ContentValues();
        values.put(FIELD_ID, 6);
        values.put(FIELD_NAME, "Hahahaha");
        getContentResolver().insert(bookUri, values);

        // query books
        cursor = getContentResolver().query(bookUri, new String[]{FIELD_ID, FIELD_NAME}, null, null, null);
        while (cursor.moveToNext()) {
            Book book = new Book(cursor.getInt(cursor.getColumnIndex(FIELD_ID)), cursor.getString(cursor.getColumnIndex(FIELD_NAME)));
            Log.d(Const.LOG, "Book Queried: " + book);
        }

        cursor.close();
    }
}
