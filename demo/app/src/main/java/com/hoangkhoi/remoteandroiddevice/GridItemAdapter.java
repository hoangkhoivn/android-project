package com.hoangkhoi.remoteandroiddevice;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GridItemAdapter extends ArrayAdapter<String> {
   private  Context context;
   private String tag;
   public GridItemAdapter(Context context, ArrayList<String> items, String tag) {
      super(context, 0, items);
      this.context = context;
      this.tag = tag;
   }
   @Override
   public View getView(int position, View convertView, ViewGroup parent) {
      // Kiểm tra xem view có được tái sử dụng, nếu không thì inflate view
      if (convertView == null) {
         convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item, parent, false);
      }
      String item = getItem(position);
      String press = item.split(",")[0];
      String press_key = item.split(",")[1];
      TextView itemTextView = convertView.findViewById(R.id.itemTextView);

      itemTextView.setText(press);
      convertView.setOnLongClickListener(new View.OnLongClickListener() {
         @Override
         public boolean onLongClick(View view) {
            ShowDialog showDialog = new ShowDialog(context,tag);
            showDialog.showAddRecordDialog(true);
            showDialog.setCallback(new ShowDialog.Callback() {
               @Override
               public void ActionDialog(String name, String value) {
                  GridItemAdapter.this.remove(item);
                  DatabaseHelper dbHelper = new DatabaseHelper(context);
                  dbHelper.deleteFromTable("table2",name, item);
               }
            });
            return false;
         }
      });
      convertView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Toast.makeText(context,press_key,Toast.LENGTH_LONG).show();
         }
      });
      return convertView;
   }
}
