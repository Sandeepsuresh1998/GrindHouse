package com.example.beanandleaf;

import androidx.appcompat.app.AppCompatDialogFragment;
import android.app.Dialog;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.os.Bundle;

public class ExampleDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        System.out.println("creating dialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        System.out.println("creating dialog 2");
        builder.setTitle("Location Information")
                .setMessage("You have been detected in X location.")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.out.println("creating dialog 3");

                    }
                });

        return builder.create();
    }
}
