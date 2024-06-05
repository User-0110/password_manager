package com.mirea.kt.ribo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyAppSQLiteHelper extends SQLiteOpenHelper {

    public MyAppSQLiteHelper(Context c, String name, SQLiteDatabase.CursorFactory f, int version) {
        super(c, name, f, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + "TABLE_PASSWORDS" + " (" +
                "_id INTEGER primary key autoincrement," +
                "service TEXT," +
                "login TEXT," +
                "password TEXT" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}