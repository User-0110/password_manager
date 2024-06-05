package com.mirea.kt.ribo;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DeletePasswordActivity extends AppCompatActivity {

    private DBManager dbManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_password);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String database_name = bundle.getString("database_name");

        Log.d("start_new_activity", "Экран для удаления пароля запущен");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Log.d("toolbar_display", "Появился toolbar");

        dbManager = new DBManager(new MyAppSQLiteHelper(this, database_name, null,  1));

        EditText text = findViewById(R.id.service);

        Button confirm = findViewById(R.id.confirm);
        Button clearDatabase = findViewById(R.id.clearDatabase);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String service = text.getText().toString().trim();
                if (!service.isEmpty()) {
                    Log.d("start_new_dialog", "Запускается диалог удаления пароля");
                    DeletePasswordDialog dialog = new DeletePasswordDialog(dbManager, service);
                    dialog.show(getSupportFragmentManager(), "delete_confirm");
                } else {
                    Toast.makeText(getApplicationContext(), R.string.enter_service, Toast.LENGTH_LONG).show();
                }
            }
        });
        clearDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearCurrentDatabaseDialog dialog = new ClearCurrentDatabaseDialog(dbManager);
                dialog.show(getSupportFragmentManager(), "clear_databases");
                Log.d("clear_database", "Очищена база данных");
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
        else {
            return super.onOptionsItemSelected(item);
        }
    }
}