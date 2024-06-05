package com.mirea.kt.ribo;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SocialCategoryActivity extends AppCompatActivity implements PasswordAdapter.OnPasswordClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_category);

        Log.d("start_category", "Экран с социальными категориями запущен");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Log.d("toolbar_display", "Появился toolbar");

        updatePasswords();

        Button addPassword = findViewById(R.id.addPassword);
        Button deletePassword = findViewById(R.id.deletePassword);

        addPassword.setOnClickListener(v -> {
            Log.d("start_new_activity", "Запускается добавление пароля");
            Intent intent = new Intent(getApplicationContext(), AddPasswordActivity.class);
            intent.putExtra("database_name", "socials.db");
            startActivity(intent);
        });
        deletePassword.setOnClickListener(v -> {
            Log.d("start_new_activity", "Запускается удаление пароля");
            Intent intent = new Intent(getApplicationContext(), DeletePasswordActivity.class);
            intent.putExtra("database_name", "socials.db");
            startActivity(intent);
        });
    }

    /**
     * Когда действие переходит в состояние возобновления, оно выходит на передний план, и система вызывает обратный вызов onResume().
     * Это состояние, в котором приложение взаимодействует с пользователем.
     * Приложение остается в этом состоянии до тех пор, пока не произойдет что-то, что отвлечет его внимание,
     * например, на устройство поступит телефонный звонок, пользователь не перейдет к другому действию или экран устройства не погаснет.*/

    @Override
    protected void onResume() {
        super.onResume();
        updatePasswords();
    }

    private void updatePasswords() {
        DBManager dbManager = new DBManager(new MyAppSQLiteHelper(this, "socials.db", null, 1));
        PrefManager prefManager = new PrefManager(getApplicationContext());

        ArrayList<Password> passwords;
        if (!prefManager.getOldMasterPassword().isEmpty()) {
            passwords = dbManager.loadAllPasswordsFromDatabaseWithOldMasterPassword(prefManager);
        } else {
            passwords = dbManager.loadAllPasswordsFromDatabase(prefManager);
        }
        Log.d("database_read", "Прочитана база данных");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        PasswordAdapter adapter = new PasswordAdapter(passwords, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        Log.d("recyclerView_display", "Отображение recyclerView");
    }

    @Override
    public void onPasswordClick(Password password, int position) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("text", password.getPassword());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getApplicationContext(), R.string.password_copied, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("menu_create", "Создано меню");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.category_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Log.d("menu_button_click", "Нажата кнопка домой");
            finish();
            return true;
        }
        else if (item.getItemId() == R.id.action_refresh) {
            Log.d("menu_button_click", "Нажата кнопка обновить страницу");
            finish();
            startActivity(getIntent());
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
}