package com.trusttobit.btcdictionary;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.trusttobit.btcdictionary.adapter.DBAdapter;
import com.trusttobit.btcdictionary.adapter.PersianCustomAdapter;
import com.trusttobit.btcdictionary.model.DataModel;
import java.util.ArrayList;


public class PersianBitDictionary extends AppCompatActivity {

    ListView lv;
    String []names,sql_id;
    String searchname;
    ArrayList<DataModel> dataModels;
    private static PersianCustomAdapter adapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bit_dictionaty_list);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        lv=(ListView)findViewById(R.id.list);
        searchView = (SearchView) findViewById(R.id.simpleSearchView);

        dataModels= new ArrayList<>();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchname = query;
                dataModels.clear();
                lv.setAdapter(adapter);
                searchInPersian();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchname = newText;
                dataModels.clear();
                lv.setAdapter(adapter);
                searchInPersian();
                return false;
            }
        });
        //DB
        try {
            DBAdapter db = new DBAdapter(this);
            db.open();
            Cursor c1 = db.getPersianData();

            names = new String[c1.getCount()];
            int i = 0;
            if (c1.moveToFirst()) {
                try {
                    do {
                        names[i] = c1.getString(1);
                        dataModels.add(new DataModel(names[i]));
                        i++;
                    } while (c1.moveToNext());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //
        adapter= new PersianCustomAdapter(dataModels,getApplicationContext());
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                DataModel dataModel= dataModels.get(position);
                Intent i = new Intent();
                i.putExtra("name", dataModel.getName());
                i.setClass(PersianBitDictionary.this, ShowPersianDetails.class);
                startActivity(i);
            }
        });
    }
    public void searchInPersian(){
        //search_in_db
        try {
            DBAdapter db = new DBAdapter(this);
            db.open();
            Cursor c1 = db.searchInPersian(searchname);

            names = new String[c1.getCount()];
            int i = 0;
            if (c1.moveToFirst()) {
                try {
                    do {
                        names[i] = c1.getString(1);
                        dataModels.add(new DataModel(names[i]));
                        i++;
                    } while (c1.moveToNext());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        adapter= new PersianCustomAdapter(dataModels,getApplicationContext());
        lv.setAdapter(adapter);
    }

}
