package org.anchorer.l.c02.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Anchorer/duruixue on 2015/9/30.
 */
public class DBHelper extends SQLiteOpenHelper {

    // database name
    private static final String DB_NAME = "book_provider.db";

    // table name
    public static final String TABLE_BOOK = "book";
    public static final String TABLE_USER = "user";

    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_BOOK + "(_id INTEGER PRIMARY KEY, _name TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USER + "(_id INTEGER PRIMARY KEY, _name TEXT, sex INT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
