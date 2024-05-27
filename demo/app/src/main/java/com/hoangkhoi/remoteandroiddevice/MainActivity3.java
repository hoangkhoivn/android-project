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
   
}
