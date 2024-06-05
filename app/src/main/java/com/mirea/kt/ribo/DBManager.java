package com.mirea.kt.ribo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBManager {
    private SQLiteOpenHelper sqLiteOpenHelper;

    public DBManager(SQLiteOpenHelper sqLiteOpenHelper) {
        this.sqLiteOpenHelper = sqLiteOpenHelper;
    }

    public boolean savePasswordToDatabase(Password password, PrefManager prefManager) {
        SQLiteDatabase database = sqLiteOpenHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("service", password.getService());
        values.put("login", password.getLogin());


        Log.d("save", prefManager.getMasterPassword() + " " + prefManager.getOldMasterPassword());
        if (prefManager.getOldMasterPassword().isEmpty()) {
            values.put("password", Cryptographer.encrypt(password.getPassword(), prefManager.getMasterPassword()));
        } else{
            values.put("password", Cryptographer.encrypt(password.getPassword(), prefManager.getOldMasterPassword()));
        }
        Log.d("save", prefManager.getMasterPassword() + " " + prefManager.getOldMasterPassword());


        long rowId = database.insert("TABLE_PASSWORDS", null, values);

        values.clear();
        database.close();
        return rowId != -1;
    }

    public ArrayList<Password> loadAllPasswordsFromDatabase(PrefManager prefManager) {
        ArrayList<Password> passwords = new ArrayList<>();
        SQLiteDatabase database = sqLiteOpenHelper.getWritableDatabase();
        Cursor cursor = database.query("TABLE_PASSWORDS",
                null, null, null,
                null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String service = cursor.getString(cursor.getColumnIndexOrThrow("service"));
                String login = cursor.getString(cursor.getColumnIndexOrThrow("login"));
                String password = Cryptographer.decrypt(cursor.getString(cursor.getColumnIndexOrThrow("password")),
                        prefManager.getMasterPassword());
                passwords.add(new Password(service, login, password));
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        return passwords;
    }

    public ArrayList<Password> loadAllPasswordsFromDatabaseWithOldMasterPassword(PrefManager prefManager) {
        ArrayList<Password> passwords = new ArrayList<>();
        SQLiteDatabase database = sqLiteOpenHelper.getWritableDatabase();
        Cursor cursor = database.query("TABLE_PASSWORDS",
                null, null, null,
                null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String service = cursor.getString(cursor.getColumnIndexOrThrow("service"));
                String login = cursor.getString(cursor.getColumnIndexOrThrow("login"));
                Log.d("load", prefManager.getMasterPassword() + " " + prefManager.getOldMasterPassword());
                String password = Cryptographer.decrypt(cursor.getString(cursor.getColumnIndexOrThrow("password")),
                        prefManager.getOldMasterPassword());
                Log.d("load", prefManager.getMasterPassword() + " " + prefManager.getOldMasterPassword());
                passwords.add(new Password(service, login, password));
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        return passwords;
    }

    public void clearDatabase() {
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + "TABLE_PASSWORDS");
        db.close();
    }

    public boolean deleteService(String name) {
        SQLiteDatabase database = sqLiteOpenHelper.getWritableDatabase();
        return database.delete("TABLE_PASSWORDS", "service" + "=?", new String[]{name}) > 0;
    }

}