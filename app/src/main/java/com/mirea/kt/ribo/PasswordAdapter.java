package com.mirea.kt.ribo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.ViewHolder> {

    public interface OnPasswordClickListener {
        void onPasswordClick(Password password, int position);
    }

    private final OnPasswordClickListener onPasswordClickListener;
    private ArrayList<Password> passwords;

    public PasswordAdapter(ArrayList<Password> passwords, OnPasswordClickListener onPasswordClickListener) {
        this.passwords = passwords;
        this.onPasswordClickListener = onPasswordClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView service;
        private final TextView login;
        private final TextView password;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            service = itemView.findViewById(R.id.service);
            login = itemView.findViewById(R.id.login);
            password = itemView.findViewById(R.id.password);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_password, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Password password = passwords.get(position);
        holder.service.setText(String.format("%s", password.getService()));
        holder.login.setText(String.format("%s", password.getLogin()));
        holder.password.setText(String.format("%s", password.getPassword()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPasswordClickListener.onPasswordClick(password, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return passwords.size();
    }
}