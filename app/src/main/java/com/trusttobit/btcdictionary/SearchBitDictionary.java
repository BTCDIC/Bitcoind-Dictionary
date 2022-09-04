package com.trusttobit.btcdictionary;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.trusttobit.btcdictionary.adapter.DBAdapter;

import java.util.ArrayList;

public class SearchBitDictionary extends AppCompatActivity {

    // List View object
    ListView listView;
    // Define array adapter for ListView
    ArrayAdapter<String> adapter;
    // Define array List for List View data
    ArrayList<String> mylist;
    String []names;
    String searchName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_details);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

       /* SearchView simpleSearchView = (SearchView) findViewById(R.id.simpleSearchView); // inititate a search view
        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchName  = query;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });*/
        //DB
        try {
            DBAdapter db = new DBAdapter(this);
            db.open();
            Cursor c1 = db.getPersianData();

            names = new String[c1.getCount()];
            int i=0;
            if (c1.moveToFirst())
            {
                try {
                    do {
                        names[i]= c1.getString(1);
                        i++;
                    } while (c1.moveToNext());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            db.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
