package com.example.sqlitedbpractice;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper{
	
	private static final int DATABASE_VERSION = 1;
	 
    // Database Name
    private static final String DATABASE_NAME = "salesReport";
 
    // Salestable name
    private static final String TABLE_SALES = "sales";
    
   // Sales Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_TOP = "top";
    
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SALES_TABLE = "CREATE TABLE " + TABLE_SALES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTO INCREMENT," + KEY_AMOUNT + " TEXT,"
                + KEY_TOP + " INTEGER" + ")";
        db.execSQL(CREATE_SALES_TABLE);
    }
    
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALES);
 
        // Create tables again
        onCreate(db);
    }
    
    public void addContact(Sales sales) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
       // values.put(KEY_ID, sales.getId()); // Contact Name
        values.put(KEY_AMOUNT, sales.getAmount()); // Contact Name
        values.put(KEY_TOP, sales.getTop()); // Contact Phone Number
     
        // Inserting Row
        db.insert(TABLE_SALES, null, values);
        db.close(); // Closing database connection
    }
    
 // Getting All Contacts
    public List<Sales> getAllSales() {
        List<Sales> salesList = new ArrayList<Sales>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SALES;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if(cursor != null)
        if (cursor.moveToFirst()) {
            do {
                Sales sales = new Sales();
                sales.setId(Integer.parseInt(cursor.getString(0)));
                sales.setAmount(cursor.getString(1));
                sales.setTop(Integer.parseInt(cursor.getString(2))); 
                // Adding contact to list
                salesList.add(sales);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return salesList;
    }

}
