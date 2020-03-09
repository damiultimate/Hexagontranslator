package com.hexagon.translator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.hexagon.translator.LockDetails;

public class DBHelper extends SQLiteOpenHelper {
    public Context context;
    public boolean successful=false;
    public DBHelper(Context context) {

        super(context, "lockDetails", null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LockDetails.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS lockDetails");
        onCreate(db);
    }
    public void insert(String title,String data){
       try {
           SQLiteDatabase db = this.getWritableDatabase();
           ContentValues values = new ContentValues();
           values.put("title", title);
           values.put("data", data);
           long id = db.insert("lockDetails", null, values);
           db.close();
successful=true;
       }catch(Exception e){
successful=false;
       }
    }
    public LockDetails get(String title){
     LockDetails lockDetails=null;
     try{
       SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor=db.query("lockDetails",new String[]{"title","data"},"title=?",new String[]{title},null,null,null,null);
    if(cursor != null) {
        cursor.moveToFirst();
        lockDetails = new LockDetails(cursor.getInt(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("title")), cursor.getString(cursor.getColumnIndex("data")), cursor.getString(cursor.getColumnIndex("time")));
        cursor.close();
    }
         db.close();

         successful=true;
    }catch(Exception e){
successful=false;
     }
    return lockDetails;
    }
    public void update(String title,String data) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("title", title);
            values.put("data", data);
            db.update("lockDetails", values, "title=?", new String[]{title});
       successful=true;
            db.close();

        }catch(Exception e){
            successful=false;
        }

    }
    public void delete(String title){
       try {
           SQLiteDatabase db = this.getWritableDatabase();
           db.delete("lockDetails", "title=?", new String[]{title});
           db.close();
           successful=true;
       }catch(Exception e){
           successful=false;
       }
    }
}