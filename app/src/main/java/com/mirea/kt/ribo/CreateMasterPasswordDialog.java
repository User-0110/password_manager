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

public class CreateMasterPasswordDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.activity_create_master_password, null);
        builder.setView(dialogView);
        EditText et_master_password = dialogView.findViewById(R.id.editTextPassword);
        EditText et_confirm_master_password = dialogView.findViewById(R.id.editTextConfirmPassword);
        PrefManager prefManager = new PrefManager(getContext());

        if (prefManager.getOldMasterPassword().isEmpty()) {
            prefManager.saveOldMasterPassword(prefManager.getMasterPassword());
        }

        builder.setMessage("Давайте установим мастер пароль, чтоб Вы могли пользоваться этим приложением!")
                .setPositiveButton("Установить пароль", ((dialog, which) -> {
                    String master_password = et_master_password.getText().toString();
                    String confirm_master_password = et_confirm_master_password.getText().toString();
                    if (!master_password.isEmpty() && !confirm_master_password.isEmpty()) {
                        if (master_password.equals(confirm_master_password)) {
                            prefManager.saveMasterPassword(master_password);
                            prefManager.saveKey(PasswordGenerator.generatePassword(6).replace("&", "$"));

                            Log.d("start_dialog", "Запускается диалог показа мастер ключа");
                            ShowMasterKeyDialog masterKeyDialog = new ShowMasterKeyDialog();
                            masterKeyDialog.show(getParentFragmentManager(), "master_key");
                        }
                        else {
                            Toast.makeText(getContext(), R.string.password_mismatch, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getContext(), R.string.enter_master_password, Toast.LENGTH_LONG).show();
                    }
                }))
                .setNegativeButton(R.string.back, ((dialog, which) -> {
                    Log.d("button_click", "Нажата кнопка назад в диалоге");
                    dismiss();
                }));
        return builder.create();
    }
}