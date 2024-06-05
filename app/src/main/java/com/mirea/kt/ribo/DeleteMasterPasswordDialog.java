package com.mirea.kt.ribo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DeleteMasterPasswordDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.delete_master_password_dialog, null);
        builder.setView(dialogView);
        EditText et_master_key = dialogView.findViewById(R.id.delete_master_password);
        builder.setMessage(R.string.confirm_master_key_dialog)
                .setPositiveButton(R.string.confirm, (dialog, which) -> {
                    String master_key = et_master_key.getText().toString();
                    PrefManager manager = new PrefManager(getContext());
                    if (!master_key.isEmpty()) {
                        if (manager.getKey().equals(master_key)) {
                            Log.d("start_dialog", "Запускается диалог создания мастер пароля");
                            CreateMasterPasswordDialog createMasterPasswordDialog = new CreateMasterPasswordDialog();
                            createMasterPasswordDialog.show(getParentFragmentManager(), "create_master_password");
                        } else {
                            Toast.makeText(getContext(), R.string.invalid_master_key, Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(getContext(), R.string.enter_master_key, Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton(R.string.back, (dialog, which) -> {
                    Log.d("button_click", "Нажата кнопка назад в диалоге");
                    dismiss();
                });
        return builder.create();
    }
}