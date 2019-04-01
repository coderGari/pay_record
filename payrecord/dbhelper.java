package com.android.garima.payrecord;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class dbhelper  extends SQLiteOpenHelper {

    private static final String DB_NAME="PRDB";
    private static final int DB_VER= 1;
    private static final String DB_TABLE="payment";
    private static final String DB_COLUMN="payment_record";
    private static final String DB_COLUMN1="total_pay";
    private static final String id="ID";


    public dbhelper(Context context) {
        super(context, DB_NAME,null,DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    String query=String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY , %s TEXT NOT NULL, %s NUMBER);",id,DB_TABLE,DB_COLUMN,DB_COLUMN1);
    db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    String query=String.format("DELETE TABLE IF EXISTS %s",DB_TABLE);
    db.execSQL(query);
    onCreate(db);

    }
    public void insertnewrecord(String record,int total)
    {

        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues  values=new ContentValues();
        values.put(DB_COLUMN,record);
        values.put(DB_COLUMN1,total);
        db.insertWithOnConflict(DB_TABLE,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }


    public void deleterecord(String record)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(DB_TABLE,DB_COLUMN + " = ?",new String[]{record});
        db.close();
    }

    public ArrayList<String> getTaskList()
    {
        ArrayList<String> recordlist=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(DB_TABLE,new String[]{DB_COLUMN,DB_COLUMN1},null,null,null,null,null);
        while(cursor.moveToNext())
        {
            int index=cursor.getColumnIndex(DB_COLUMN);
            int index1=cursor.getColumnIndex(DB_COLUMN1);
            recordlist.add(cursor.getString(index));
            recordlist.add(cursor.getString(index1));

        }
        cursor.close();
        db.close();
        return recordlist;
    }

    public int get_total(int i)
    {
        int total = 0;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(DB_TABLE,new String[]{DB_COLUMN1},null,null,null,null,null);
        while (cursor.move(i+1)) {
            int index1 = cursor.getColumnIndex(DB_COLUMN1);
            total = cursor.getInt(index1);

        }
       return total;
    }

}
