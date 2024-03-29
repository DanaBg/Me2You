package com.example.me2you.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.widget.Toast;


public class AlertDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("This is my Alert!!!");
        builder.setMessage("This is the content of nmy alert");
        builder.setPositiveButton("OK", (DialogInterface dialogInterface, int i)->{
            Toast.makeText(getContext(),"Alert is OK",Toast.LENGTH_LONG).show();
        });
        return builder.create();
    }
}