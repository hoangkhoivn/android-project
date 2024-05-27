package com.hoangkhoi.remoteandroiddevice;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public  class ShowDialog {
    private Context context;
    private String title;
    public interface Callback{
        void ActionDialog(String name, String value);
    }
    public Callback callback;
    public void setCallback(Callback callback ){
        this.callback = callback;
    }

    public ShowDialog(Context context, String tile) {
        this.context = context;
        this.title = tile;
    }

    public void showAddRecordDialog(Boolean delete) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_record, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        final EditText editTextName = dialogView.findViewById(R.id.editTextName);
        final EditText editTextValue = dialogView.findViewById(R.id.editTextValue);
        editTextName.setText(title);
        String btnText = "Add";
        if(delete){
            editTextName.setVisibility(View.GONE);
            editTextValue.setText("Delete");
            btnText = "Ok";
        }
        builder.setPositiveButton(btnText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editTextName.getText().toString();
                String value = editTextValue.getText().toString();
                if(callback != null){
                    callback.ActionDialog(name, value);
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
