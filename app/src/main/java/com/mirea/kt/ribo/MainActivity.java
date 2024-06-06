package com.mirea.kt.ribo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PrefManager prefManager = new PrefManager(getApplicationContext());

        String group = "RIBO-04-22";
        String server = "https://android-for-students.ru";
        String path = "/coursework/login.php";

        Log.d("start_app", "Стартовый экран запущен");

        EditText etLogin = findViewById(R.id.etLogin);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnEnter = findViewById(R.id.btnEnter);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lgn = etLogin.getText().toString();
                String pwd = etPassword.getText().toString();
                if (!lgn.isEmpty() && !pwd.isEmpty()) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("lgn", lgn);
                    map.put("pwd", pwd);
                    map.put("g", group);
                    HTTPRunnable httpRunnable = new HTTPRunnable(server + path, map);
                    Thread th = new Thread(httpRunnable);
                    th.start();
                    try {
                        th.join();
                    } catch (InterruptedException exception) {
                        throw new RuntimeException(exception);
                    } finally {
                        try {
                            JSONObject jsonObject = new JSONObject(httpRunnable.getResponseBody());
                            int result = jsonObject.getInt("result_code");
                            Log.i("title", "Title: " + jsonObject.getString("title"));
                            Log.i("task", "Task: " + jsonObject.getString("task"));
                            Log.i("variant", "Variant: " + jsonObject.getString("variant"));
                            if (result == 1) {
                                if (prefManager.getMasterPassword().isEmpty()) {
                                    CreateMasterPasswordDialog dialog = new CreateMasterPasswordDialog();
                                    dialog.show(getSupportFragmentManager(), "create_master");
                                }
                                else {
                                    ConfirmMasterPasswordDialog dialog = new ConfirmMasterPasswordDialog();
                                    dialog.show(getSupportFragmentManager(), "confirm_master");
                                }
                            } else if (result == -1) {
                                Toast.makeText(getApplicationContext(), R.string.invalid_value, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException | NullPointerException exception) {
                            Toast.makeText(getApplicationContext(), R.string.server_not_answer, Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), R.string.enter_login_password, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}