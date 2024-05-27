package com.hoangkhoi.remoteandroiddevice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity2 extends Activity {
    DatabaseHelper dbHelper;
    ListView tvBrandListView;
    TvBrandAdapter adapter;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        dbHelper = new DatabaseHelper(this);
        Intent intent = getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {
            String value1 = bundle.getString("key1");
            TextView textView = findViewById(R.id.tv2);
            textView.setText(value1);
            tvBrandListView = findViewById(R.id.tvBrandListView);
            ArrayList<String>  tvBrands = dbHelper.readTable("table1",value1);
            adapter = new TvBrandAdapter(this, tvBrands,value1);
            tvBrandListView.setAdapter(adapter);
            ImageButton imageButton = findViewById(R.id.ac2_btn1);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowDialog showDialog = new ShowDialog(MainActivity2.this,value1);
                    showDialog.showAddRecordDialog(false);
                    showDialog.setCallback(new ShowDialog.Callback() {
                        @Override
                        public void ActionDialog(String name, String value) {
                            adapter.add(value);
                            long result = dbHelper.insertIntoTable("table1",name, value);
                            if (result != -1) {
                                Toast.makeText(MainActivity2.this, "Record added", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity2.this, "Error adding record", Toast.LENGTH_SHORT).show();
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
                // Add the record to the database
                long result = dbHelper.insertIntoTable2(name, value);
                if (result != -1) {
                    Toast.makeText(MainActivity2.this, "Record added", Toast.LENGTH_SHORT).show();
                    // Tạo TvBrandAdapter tùy chỉnh
                    ArrayList<String>  tvBrands = dbHelper.readTable("table2","");
                    adapter = new TvBrandAdapter(MainActivity2.this, tvBrands,bundle.getString("key1"));
                    // Thiết lập adapter cho ListView
                    tvBrandListView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity2.this, "Error adding record", Toast.LENGTH_SHORT).show();
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
    }*/
}