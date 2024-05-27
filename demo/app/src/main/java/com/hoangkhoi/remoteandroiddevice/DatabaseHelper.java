package com.hoangkhoi.remoteandroiddevice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

   private static final int DATABASE_VERSION = 1;
   private static final String DATABASE_NAME = "database_remote.db";
   public static final String TABLE_1 = "table1";
   public static final String TABLE_2 = "table2";
   public static final String COLUMN_ID = "_id";
   public static final String COLUMN_NAME = "name";
   public static final String COLUMN_DATA = "value";
   // SQL to create tables
   private static final String TABLE_1_CREATE =
           "CREATE TABLE " + TABLE_1 + " (" +
                   COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                   COLUMN_NAME + " TEXT, " +
                   COLUMN_DATA + " TEXT);";

   private static final String TABLE_2_CREATE =
           "CREATE TABLE " + TABLE_2 + " (" +
                   COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                   COLUMN_NAME + " TEXT, " +
                   COLUMN_DATA + " TEXT);";
   public DatabaseHelper(Context context) {
      super(context, DATABASE_NAME, null, DATABASE_VERSION);
   }
   @Override
   public void onCreate(SQLiteDatabase db) {
      db.execSQL(TABLE_1_CREATE);
      db.execSQL(TABLE_2_CREATE);
   }
   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("DROP TABLE IF EXISTS " + TABLE_1);
      db.execSQL("DROP TABLE IF EXISTS " + TABLE_2);
      onCreate(db);
   }
   public long insertIntoTable(String table,String name, String value) {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = new ContentValues();
      values.put(COLUMN_NAME, name);
      values.put(COLUMN_DATA, value);
      return db.insert(table, null, values);
   }
   public ArrayList<String> readTable(String table,String item_name) {
      SQLiteDatabase db = this.getReadableDatabase();
      String[] columns = { COLUMN_ID, COLUMN_NAME, COLUMN_DATA };
      ArrayList<String> arrayList = new ArrayList<>();
      Cursor cursor = db.query(table, columns, null, null, null, null, null);
      if (cursor != null) {
         while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));
            String value = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATA));
            if (item_name.contains(name)){
               arrayList.add(value);
            }
         }
         cursor.close();
      }
      return  arrayList;
   }
   public void deleteFromTable(String table, String name, String value) {
      SQLiteDatabase db = this.getWritableDatabase();
      String[] columns = { COLUMN_ID, COLUMN_NAME, COLUMN_DATA };
      String selection = COLUMN_NAME + "=? AND " + COLUMN_DATA + "=?";
      String[] selectionArgs = { name, value };

      Cursor cursor = db.query(table, columns, selection, selectionArgs, null, null, null);
      if (cursor != null && cursor.moveToFirst()) {
         long id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID));
         cursor.close();
         db.delete(table, COLUMN_ID + "=" + id, null);
         return;
      }
      if (cursor != null) {
         cursor.close();
      }
   }

}
