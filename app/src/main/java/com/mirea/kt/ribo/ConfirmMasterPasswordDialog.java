package com.mirea.kt.ribo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ConfirmMasterPasswordDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.confirm_master_password_dialog, null);
        builder.setView(dialogView);
        EditText et_master_password = dialogView.findViewById(R.id.confirm_master_password);
        builder.setMessage(R.string.confirm_master_password_dialog)
                .setPositiveButton(R.string.confirm, (dialog, which) -> {
                    String master_password = et_master_password.getText().toString();
                    PrefManager manager = new PrefManager(getContext());
                    if (!master_password.isEmpty()) {
                        if (manager.getMasterPassword().equals(master_password)) {
                            Log.d("start_category", "Запускается экран с категориями");
                            startActivity(new Intent(getContext(), CategoryActivity.class));
                        } else {
                            Toast.makeText(getContext(), R.string.invalid_master_password, Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(getContext(), R.string.enter_master_password, Toast.LENGTH_LONG).show();
                    }
                })
                .setNeutralButton(R.string.forgotKey, (dialog, which) -> {
                    Log.d("start_dialog", "Запускается диалог удаления мастер пароля");
                    DeleteMasterPasswordDialog deleteMasterPasswordDialog = new DeleteMasterPasswordDialog();
                    deleteMasterPasswordDialog.show(getParentFragmentManager(), "delete_master_password");
                });
        return builder.create();
    }
}