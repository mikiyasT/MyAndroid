package com.example.sqlitedbpractice;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        DatabaseHandler db = new DatabaseHandler(this);
        
        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        Log.d("Insert: ", "Inserting .."); 
        db.addContact(new Sales("05", 4));        
        db.addContact(new Sales("205", 6));
        db.addContact(new Sales("500", 6));
        db.addContact(new Sales("10",9));
         
        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts.."); 
        List<Sales> sales = db.getAllSales();       
         
        String log = null;
        for (Sales s : sales) {
            log = "Id: "+s.getId()+" ,Name: " + s.getAmount() + " ,Phone: " + s.getTop();
            Log.d("Name: ", log);
        }
                // Writing Contacts to log
       
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
