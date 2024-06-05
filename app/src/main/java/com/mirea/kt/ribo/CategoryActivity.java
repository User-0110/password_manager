package com.mirea.kt.ribo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Log.d("start_category", "Экран с категориями запущен");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Log.d("toolbar_display", "Появился toolbar");

        Button bankCategory = findViewById(R.id.btnBankCategory);
        Button mailCategory = findViewById(R.id.btnMailCategory);
        Button socialCategory = findViewById(R.id.btnSocialCategory);
        Button miscCategory = findViewById(R.id.btnMiscCategory);
        Button clearDatabase = findViewById(R.id.clearDatabase);

        bankCategory.setOnClickListener(v -> {
            Log.d("start_new_activity", "Запускается банковская категория");
            startActivity(new Intent(getApplicationContext(), BankCategoryActivity.class));
        });
        mailCategory.setOnClickListener(v -> {
            Log.d("start_new_activity", "Запускается почтовая категория");
            startActivity(new Intent(getApplicationContext(), MailCategoryActivity.class));
        });
        socialCategory.setOnClickListener(v -> {
            Log.d("start_new_activity", "Запускается социальная категория");
            startActivity(new Intent(getApplicationContext(), SocialCategoryActivity.class));
        });
        miscCategory.setOnClickListener(v -> {
            Log.d("start_new_activity", "Запускается категория прочего");
            startActivity(new Intent(getApplicationContext(), OtherCategoryActivity.class));
        });

        clearDatabase.setOnClickListener(v -> {
            Log.d("start_new_dialog", "Запускается диалог очистки всех баз данных");
            ClearDatabaseDialog dialog = new ClearDatabaseDialog();
            dialog.show(getSupportFragmentManager(), "clear_databases");
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("menu_create", "Создано меню");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.simple_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Log.d("menu_button_click", "Нажата кнопка домой");
            finish();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
}