package com.trusttobit.btcdictionary;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.trusttobit.btcdictionary.adapter.DBAdapter;

public class ShowPersianDetails extends AppCompatActivity {
    TextView tv;
    String[] Arrays;
    String name,description;
    boolean bool;
    private static final int START_LEVEL = 1;
    private int mLevel;
    int i = 0,id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.persian_show_details);
        tv=(TextView)findViewById(R.id.textviewscrollview);
        tv.setGravity(Gravity.RIGHT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = getIntent();
        name = intent.getStringExtra("name").trim();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(name);

        try {
            DBAdapter db = new DBAdapter(this);
            db.open();
            Cursor c1 = db.showPersianData(name);

            Arrays = new String[c1.getCount()];
            if (c1.moveToFirst())
            {
                try {
                    do {
                        description = c1.getString(1);
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
        tv.setText(description.toString());
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        tv.setTypeface(null, Typeface.BOLD);
    }
}
