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

public class ClearCurrentDatabaseDialog extends DialogFragment {
    private DBManager dbManager;

    public ClearCurrentDatabaseDialog(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.clearAllDatabases)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbManager.clearDatabase();
                        Log.d("button_click", "Была очищена конкретная база данных");
                        Toast.makeText(getContext(), R.string.successful_delete_all, Toast.LENGTH_LONG).show();
                        getActivity().finish();
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