package com.hoangkhoi.remoteandroiddevice;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity3 extends Activity {
    DatabaseHelper dbHelper;
    GridView gridView;
    GridItemAdapter adapter;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        dbHelper = new DatabaseHelper(this);
        Intent intent = getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {
            String value1 = bundle.getString("key1");
            gridView = findViewById(R.id.gridView);
            ArrayList<String>  items =  dbHelper.readTable("table2",value1);
            adapter = new GridItemAdapter(this, items,value1);
            gridView.setAdapter(adapter);
            ImageButton imageButton = findViewById(R.id.ac3_btn1);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowDialog showDialog = new ShowDialog(MainActivity3.this,value1);
                    showDialog.showAddRecordDialog(false);
                    showDialog.setCallback(new ShowDialog.Callback() {
                        @Override
                        public void ActionDialog(String name, String value) {
                            adapter.add(value);
                            long result = dbHelper.insertIntoTable("table2",name, value);
                            if (result != -1) {
                                Toast.makeText(MainActivity3.this, "Record added", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity3.this, "Error adding record", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            });


        }

    }
    /*
    private void showAddRecordDialog(String title) {
        // Create an instance of LayoutInflater
        LayoutInflater inflater = LayoutInflater.from(this);
        // Inflate the custom layout/dialog_add_record
        View dialogView = inflater.inflate(R.layout.dialog_add_record, null);

        // Create AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        // Get the EditText fields from the dialog
        final EditText editTextName = dialogView.findViewById(R.id.editTextName);
        final EditText editTextValue = dialogView.findViewById(R.id.editTextValue);
        editTextName.setText(title);
        // Set up the dialog buttons
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editTextName.getText().toString();
                String value = editTextValue.getText().toString();
                if(value.contains(",")){
                    // Add the record to the database
                    long result = dbHelper.insertIntoTable3(name, value);

                    if (result != -1) {
                        Toast.makeText(MainActivity3.this, "Record added", Toast.LENGTH_SHORT).show();
                        ArrayList<String>  items =  dbHelper.readTable("table3",bundle.getString("key1"));
                        adapter = new GridItemAdapter(MainActivity3.this, items,title);
                        gridView.setAdapter(adapter);
                    } else {
                        Toast.makeText(MainActivity3.this, "Error adding record", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity3.this, "Error adding record must ','", Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void RemoveItem(String action) {
        Log.d("nghia", "CallBack " +action + "-- " + bundle.getString("key1"));
        dbHelper = new DatabaseHelper(this);
        ArrayList<String>  items =  dbHelper.readTable("table3",bundle.getString("key1"));
        for (String str: items
             ) {
            Log.d("nghia", "CallBack " +str);
        }
       adapter = new GridItemAdapter(MainActivity3.this, items,bundle.getString("key1"));
       gridView.setAdapter(adapter);
        adapter.setCallback(MainActivity3.this);
    }*/
}
