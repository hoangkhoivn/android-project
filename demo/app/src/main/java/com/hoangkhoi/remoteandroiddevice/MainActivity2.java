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

}
