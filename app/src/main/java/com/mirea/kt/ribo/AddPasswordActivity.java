package com.mirea.kt.ribo;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddPasswordActivity extends AppCompatActivity {

    private DBManager dbManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String database_name = bundle.getString("database_name");

        dbManager = new DBManager(new MyAppSQLiteHelper(this, database_name, null,  1));
        PrefManager prefManager = new PrefManager(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Log.d("toolbar_display", "Появился toolbar");

        Button addPassword = findViewById(R.id.addPassword);
        Button generatePassword = findViewById(R.id.generatePassword);

        EditText etService = findViewById(R.id.etService);
        EditText etLogin = findViewById(R.id.etLogin);
        EditText etPassword = findViewById(R.id.etPassword);


        addPassword.setOnClickListener(v -> {
            Log.d("button_click", "Нажата кнопка добавления пароля");
            if (this.dbManager != null) {
                String service = etService.getText().toString();
                String login = etLogin.getText().toString();
                String password = etPassword.getText().toString();
                if (!service.isEmpty() && !login.isEmpty() && !password.isEmpty()) {
                    boolean result = dbManager.savePasswordToDatabase(new Password(service, login, password), prefManager);
                    if (result) {
                        Toast.makeText(this, R.string.entry_saved, Toast.LENGTH_LONG).show();
                        Log.d("generate_password", "Пароль был сохранён");
                        finish();
                    }
                    else {
                        Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(this, R.string.invalid_value, Toast.LENGTH_LONG).show();
                }
            }
        });
        generatePassword.setOnClickListener(v -> {
            Log.d("button_click", "Нажата кнопка генерации пароля");
            if (this.dbManager != null) {
                String service = etService.getText().toString();
                String login = etLogin.getText().toString();
                String password = PasswordGenerator.generatePassword(12);
                if (!service.isEmpty() && !login.isEmpty()) {
                    boolean result = dbManager.savePasswordToDatabase(new Password(service, login, password), prefManager);
                    if (result) {
                        Toast.makeText(this, R.string.entry_saved, Toast.LENGTH_LONG).show();
                        Log.d("generate_password", "Пароль был сгенерирован и сохранён");
                        finish();
                    }
                    else {
                        Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(this, R.string.fill_in_the_fields, Toast.LENGTH_LONG).show();
                }
            }
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
        return super.onOptionsItemSelected(item);
    }
}