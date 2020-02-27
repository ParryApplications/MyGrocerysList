package com.nested.paras.mygroc.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nested.paras.mygroc.Model.Grocery;
import com.nested.paras.mygroc.Util.Constants;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBase extends SQLiteOpenHelper
{

    private Context ctx;

    public DataBase(Context context) {
        super(context, Constants.DB_NAME,null,Constants.DB_VERSION);
        this.ctx=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String GROCERY_LIST = " CREATE TABLE " + Constants.TABLE_NAME + "(" + Constants.KEY_ID + " INTEGER PRIMARY KEY ,"
                + Constants.KEY_ITEM + " TEXT ," + Constants.KEY_QTY + " TEXT ," + Constants.KEY_DATE + " LONG );";
        db.execSQL(GROCERY_LIST);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+Constants.TABLE_NAME);
        onCreate(db);
    }

    //add the grocery item
    public void addgrocery(Grocery grocery)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_ITEM,grocery.getGrocery_item());
        values.put(Constants.KEY_QTY,grocery.getGetGrocery_qty());
        values.put(Constants.KEY_DATE,java.lang.System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME,null,values);
        Log.d("Saved!","save to DB");
    }
    //get grocery
    public Grocery getgrocery(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME,new String[]
                {Constants.KEY_ID,Constants.KEY_ITEM,Constants.KEY_QTY,Constants.KEY_DATE},Constants.KEY_ID + "=?",new String[]{
                                String.valueOf(id)},null,null,null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
        }
            Grocery grocery =new Grocery();
            grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
            grocery.setGrocery_item(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM)));
            grocery.setGetGrocery_qty(cursor.getString(cursor.getColumnIndex(Constants.KEY_QTY)));

            DateFormat dateFormat = DateFormat.getDateInstance();
            String df = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE))).getTime());
            grocery.setDate(df);

            return grocery;


    }
    //get all grocery list
    public List<Grocery> getallgrocery()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Grocery> groceries = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[] {
                Constants.KEY_ID, Constants.KEY_ITEM, Constants.KEY_QTY,
        Constants.KEY_DATE},null,null,null,null,Constants.KEY_DATE + " DESC");

        if(cursor.moveToFirst())
        {
            do {
                Grocery grocery = new Grocery();
                grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                grocery.setGrocery_item(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM)));
                grocery.setGetGrocery_qty(cursor.getString(cursor.getColumnIndex(Constants.KEY_QTY)));

                DateFormat dateFormat = DateFormat.getDateInstance();
                String df = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE))).getTime());
                grocery.setDate(df);

                groceries.add(grocery);

            }while (cursor.moveToNext());
        }

        return groceries;
    }
    //update grocery
    public int updategrocery(Grocery grocery)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_ITEM,grocery.grocery_item);
        values.put(Constants.KEY_QTY,grocery.getGrocery_qty);
        values.put(Constants.KEY_DATE,java.lang.System.currentTimeMillis());

        return db.update(Constants.TABLE_NAME,values,Constants.KEY_ID +"=?",new String[]{String.valueOf(grocery.getId())});
    }
    //delete grocery items
    public void deletegrocery(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Constants.TABLE_NAME,Constants.KEY_ID + "=?",new String[]{String.valueOf(id)});
            db.close();
    }
    //count
    public int getgrocerycount()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String countquerry = "SELECT * FROM " + Constants.TABLE_NAME;

        Cursor cursor = db.rawQuery(countquerry,null);

        return cursor.getCount();
    }
}
