package com.mirea.kt.ribo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ClearDatabaseDialog extends DialogFragment {

    private static String[] categories = new String[] {"banks.db", "mails.db", "socials.db", "others.db"};

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.clearAllDatabases)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBManager dbManager;
                        for (String database_name : categories) {
                            dbManager = new DBManager(new MyAppSQLiteHelper(getContext(), database_name, null, 1));
                            dbManager.clearDatabase();
                        }
                        Log.d("button_click", "Были очищены все базы данных");
                        Toast.makeText(getContext(), R.string.successful_delete_all, Toast.LENGTH_LONG).show();
                        dismiss();
                    }
                })
                .setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("button_click", "Нажата кнопка назад в диалоге");
                        dismiss();
                    }
                });
        return builder.create();
    }
}