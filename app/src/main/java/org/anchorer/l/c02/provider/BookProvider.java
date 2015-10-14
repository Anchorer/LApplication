package org.anchorer.l.c02.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by Anchorer/duruixue on 2015/9/30.
 */
public class BookProvider extends ContentProvider {

    private static final String AUTHORITY = "org.anchorer.l.c02.provider.BOOK_PROVIDER";

    private static final String PATH_BOOK = "book";
    private static final String PATH_USER = "user";

//    private static final Uri URI_BOOK = Uri.parse("content://" + AUTHORITY + "/" + PATH_BOOK);
//    private static final Uri URI_USER = Uri.parse("content://" + AUTHORITY + "/" + PATH_USER);

    private static final int CODE_BOOK_URI = 1;
    private static final int CODE_USER_URI = 2;

    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mUriMatcher.addURI(AUTHORITY, PATH_BOOK, CODE_BOOK_URI);
        mUriMatcher.addURI(AUTHORITY, PATH_USER, CODE_USER_URI);
    }

    private Context mContext;
    private SQLiteDatabase mDB;


    @Override
    public boolean onCreate() {
        mContext = getContext();
        initDB();
        return false;
    }

    private void initDB() {
        mDB = new DBHelper(mContext).getWritableDatabase();
        mDB.execSQL("DELETE FROM " + DBHelper.TABLE_BOOK);
        mDB.execSQL("DELETE FROM " + DBHelper.TABLE_USER);
        mDB.execSQL("INSERT INTO book VALUES(3, 'Android');");
        mDB.execSQL("INSERT INTO book VALUES(4, 'iOS');");
        mDB.execSQL("INSERT INTO book VALUES(5, 'Python');");
        mDB.execSQL("INSERT INTO user VALUES(1, 'Anchorer', 1);");
        mDB.execSQL("INSERT INTO user VALUES(2, 'July', 2);");
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String tableName = getTableName(uri);
        if (TextUtils.isEmpty(tableName)) {
            throw new RuntimeException("No Such Table!!");
        }
        return mDB.query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String tableName = getTableName(uri);
        if (TextUtils.isEmpty(tableName)) {
            throw new RuntimeException("No Such Table!!");
        }
        mDB.insert(tableName, null, values);
        mContext.getContentResolver().notifyChange(uri, null);
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String tableName = getTableName(uri);
        if (TextUtils.isEmpty(tableName)) {
            throw new RuntimeException("No Such Table!!");
        }
        int count = mDB.delete(tableName, selection, selectionArgs);
        if (count > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String tableName = getTableName(uri);
        if (TextUtils.isEmpty(tableName)) {
            throw new RuntimeException("No Such Table!!");
        }
        int count = mDB.update(tableName, values, selection, selectionArgs);
        if (count > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return 0;
    }

    /**
     * 根据URI获取到请求的表名
     * @param uri
     * @return
     */
    private String getTableName(Uri uri) {
        int code = mUriMatcher.match(uri);
        switch (code) {
            case CODE_BOOK_URI: {
                return DBHelper.TABLE_BOOK;
            }
            case CODE_USER_URI: {
                return DBHelper.TABLE_USER;
            }
            default: {
                return null;
            }
        }
    }
}
