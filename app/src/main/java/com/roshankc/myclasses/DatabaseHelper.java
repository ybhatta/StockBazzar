package com.roshankc.myclasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="stock.db";
    public static final String TABLE_NAME="user_details";
    public static final String coloum1="ID";
    public static final String coloum2="firstName";
    public static final String coloum3="LastName";
    public static final String coloum4="emailAddress";
    public static final String coloum5="password";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,firstName TEXT NOT NULL,lastName TEXT NOT NULL,emailAddress TEXT NOT NULL, password TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+ TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String firstName, String lastName, String emailAddress, String password){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues contentValue= new ContentValues();
        contentValue.put("firstName",firstName);
        contentValue.put("LastName",lastName);
        contentValue.put("emailAddress",emailAddress);
        contentValue.put("password",password);
        if(sqLiteDatabase.insert(TABLE_NAME,null,contentValue)!=-1){
            return true;
        }else{
            return false;
        }

    }
    public User validateUser(String emailAddress, String password){
        User user= null;
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME+" where emailAddress=? AND password=?",new String[]{emailAddress, password});
        if(cursor.getCount()==0){
            return user;
        }else if(cursor.getCount()==1) {
            cursor.moveToFirst();
            int index;
            index=cursor.getColumnIndex("ID");
            String ID= cursor.getString(index);
            index=cursor.getColumnIndex("firstName");
            String firstName= cursor.getString(index);
            index=cursor.getColumnIndex("lastName");
            String lastName= cursor.getString(index);
            user= new User(firstName,lastName,emailAddress,password,Integer.parseInt(ID));
            return user;
        }else {
            return user;
        }
    }

    public boolean emailAdreadyExists(String emailAddress){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from "+TABLE_NAME+" where emailAddress=?",new String[]{emailAddress});
        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

}
