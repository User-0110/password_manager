package com.mirea.kt.ribo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ShowMasterKeyDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.show_master_key, null);
        builder.setView(dialogView);
        TextView master_key = dialogView.findViewById(R.id.master_key);

        PrefManager manager = new PrefManager(getContext());
        master_key.setText(manager.getKey());

        builder.setMessage("Запомните этот ключ!\nС его помощью вы сможете сбросить мастер пароль!")
                .setPositiveButton("Запомнил!", ((dialog, which) -> {
                    startActivity(new Intent(getContext(), CategoryActivity.class));
                }))
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        startActivity(new Intent(getContext(), CategoryActivity.class));
                    }
                });
        return builder.create();
    }
}