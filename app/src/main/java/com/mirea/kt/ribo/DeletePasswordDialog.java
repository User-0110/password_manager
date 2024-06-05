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

public class DeletePasswordDialog extends DialogFragment {

    private DBManager dbManager;

    private String service;

    public DeletePasswordDialog(DBManager dbManager, String service) {
        this.dbManager = dbManager;
        this.service = service;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Вы уверены, что хотите удалить пароль?")
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbManager.deleteService(service);
                        Log.d("delete_service", "Удаление пароля от конкретного сервиса");
                        Toast.makeText(getContext(), R.string.successful_delete_password, Toast.LENGTH_LONG).show();
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