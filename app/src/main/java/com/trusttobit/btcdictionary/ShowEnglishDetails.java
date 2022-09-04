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
import com.trusttobit.btcdictionary.R;
import com.trusttobit.btcdictionary.adapter.DBAdapter;

import java.util.Locale;

public class ShowEnglishDetails extends AppCompatActivity {
    TextView tv;
    String[] Arrays;
    String name,description;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_details);
        tv=(TextView)findViewById(R.id.textviewscrollview);
        tv.setGravity(Gravity.RIGHT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = getIntent();
        name =intent.getStringExtra("name").trim();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(name);

        try {
            DBAdapter db = new DBAdapter(this);
            db.open();
            Cursor c1 = db.showEnglishData(name);

            Arrays = new String[c1.getCount()];
            if (c1.moveToFirst())
            {
                try {
                    do {
                        description = c1.getString(2);
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
