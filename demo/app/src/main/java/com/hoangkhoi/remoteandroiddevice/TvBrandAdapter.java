package com.hoangkhoi.remoteandroiddevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TvBrandAdapter extends ArrayAdapter<String> {
    private  Context context;
    private String tag;
    public TvBrandAdapter(Context context, ArrayList<String> brands, String tag) {
        super(context, 0, brands);
        this.context = context;
        this.tag = tag;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tv_brand_item, parent, false);
        }

        String brand = getItem(position);
        TextView tvBrandTextView = convertView.findViewById(R.id.tvBrandTextView);
        tvBrandTextView.setText(brand);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context,brand,Toast.LENGTH_LONG).show();
                if(tag == "Main") {
                    Intent intent = new Intent(context, MainActivity2.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("key1", brand);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }else{
                    Intent intent = new Intent(context, MainActivity3.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("key1",tag +"/"+ brand);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ShowDialog showDialog = new ShowDialog(context,tag);
                showDialog.showAddRecordDialog(true);
                showDialog.setCallback(new ShowDialog.Callback() {
                    @Override
                    public void ActionDialog(String name, String value) {
                        Log.d("nghia",name+ " dialog "+ value);
                        TvBrandAdapter.this.remove(brand);
                        DatabaseHelper dbHelper = new DatabaseHelper(context);
                        if(tag == "Main") {
                            dbHelper.deleteFromTable("table1","activity_main", brand);
                        }else{
                            dbHelper.deleteFromTable("table1", name,brand);
                        }
                    }
                });
                return false;
            }
        });
        return convertView;
    }
    @Override
    public int getCount() {
        return super.getCount();
    }
}
