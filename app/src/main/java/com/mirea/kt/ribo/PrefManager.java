package com.mirea.kt.ribo;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class PrefManager {
    private SharedPreferences sharedPreferences;

    public PrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences("my_master", Context.MODE_PRIVATE);
    }

    public void saveMasterPassword(String password) {
        Log.d("save_master_password", "Сохранение мастер пароля");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("master_password", password);
        editor.apply();
    }

    public String getMasterPassword() {
        Log.d("get_master_password", "Получение мастер пароля");
        return sharedPreferences.getString("master_password", "");
    }

    public void saveOldMasterPassword(String password) {
        Log.d("save_master_password", "Сохранение старого мастер пароля");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("old_master_password", password);
        editor.apply();
    }

    public String getOldMasterPassword() {
        Log.d("get_master_password", "Получение старого мастер пароля");
        return sharedPreferences.getString("old_master_password", "");
    }

    public void saveKey(String key) {
        Log.d("save_master_key", "Сохранение мастер ключа");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("master_key", key);
        editor.apply();
    }

    public String getKey() {
        Log.d("get_master_key", "Получение мастер ключа");
        return sharedPreferences.getString("master_key", "");
    }
}