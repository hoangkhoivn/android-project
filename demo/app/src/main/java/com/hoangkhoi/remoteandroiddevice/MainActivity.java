package com.hoangkhoi.remoteandroiddevice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {
    DatabaseHelper dbHelper;
    TvBrandAdapter adapter;
    ListView tvBrandListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this);
        tvBrandListView = findViewById(R.id.tvBrandListView);
        ArrayList<String> tvBrands = dbHelper.readTable("table1","activity_main");
        adapter = new TvBrandAdapter(this, tvBrands,"Main");
        tvBrandListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        ImageButton imageButton = findViewById(R.id.ac1_btn1);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialog showDialog = new ShowDialog(MainActivity.this,"Main");
                showDialog.showAddRecordDialog(false);
                showDialog.setCallback(new ShowDialog.Callback() {
                    @Override
                    public void ActionDialog(String name, String value) {
                        adapter.add(value);
                        long result = dbHelper.insertIntoTable("table1","activity_main", value);
                        if (result != -1) {
                            Toast.makeText(MainActivity.this, "Record added", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Error adding record", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }
    /*
        private void showAddRecordDialog() {
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
            editTextName.setText("Main");
            // Set up the dialog buttons
            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name = editTextName.getText().toString();
                    String value = editTextValue.getText().toString();
                    // Add the record to the database
                    long result = dbHelper.insertIntoTable1(name, value);

                    if (result != -1) {
                        Toast.makeText(MainActivity.this, "Record added", Toast.LENGTH_SHORT).show();
                        ArrayList<String> tvBrands = dbHelper.readTable("table1","");
                        // Tạo TvBrandAdapter tùy chỉnh
                        adapter = new TvBrandAdapter(MainActivity.this, tvBrands,"Main");
                        // Thiết lập adapter cho ListView
                        tvBrandListView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(MainActivity.this, "Error adding record", Toast.LENGTH_SHORT).show();
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
    */
}