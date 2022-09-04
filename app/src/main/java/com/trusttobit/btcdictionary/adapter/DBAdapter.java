package com.trusttobit.btcdictionary.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_ENGNAME = "engname";
    public static final String KEY_FANAME = "faname";
    public static final String KEY_DESCRIPTION = "description";

    private static final String TAG = "DBAdapter";

    private static final String DATABASE_NAME = "bitcoind";
    private static final String DATABASE_TABLE = "maintbl";
    private static final int DATABASE_VERSION = 1;


    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
        		 Log.w(TAG,"1!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getReadableDatabase();
        return this;
    }

    public Cursor showPersianData(String rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {
                        KEY_FANAME,KEY_DESCRIPTION}, KEY_FANAME + " like '%" + rowId + "%'", null,null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor showEnglishData(String rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                        KEY_ENGNAME,KEY_DESCRIPTION}, KEY_ENGNAME + " like '%" + rowId + "%'", null,null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor searchInPersian(String rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                        KEY_FANAME}, KEY_FANAME + " like '%" + rowId + "%'", null,
                        null, null, KEY_FANAME, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    public Cursor searchInEnglish(String rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                                KEY_ENGNAME}, KEY_ENGNAME + " like '%" + rowId + "%'", null,
                        null, null, KEY_ENGNAME, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    public Cursor getEnglishData() throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                                KEY_ENGNAME}, null, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    public Cursor getPersianData() throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                        KEY_FANAME}, null, null,null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    public void close()
    {
        DBHelper.close();
    }
}

