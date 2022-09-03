package com.trusttobit.btcdictionary;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.view.View;

import com.trusttobit.btcdictionary.adapter.DBAdapter;

import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    SharedPreferences credit, firstrun;
    String DB_NAME = "bitcoind";
    LinearLayout persianBitDictionary,englishBitDictionary,sourceitDictionary;
    int firstrunint = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        firstrun = getApplicationContext().getSharedPreferences("fr", MODE_PRIVATE);
        firstrunint = firstrun.getInt("MyPref", 0);
        if (firstrunint == 0) {
            File dbFile = getBaseContext().getDatabasePath(DB_NAME);
            if (!dbFile.exists()) {
                try {
                    copyDatabase2(dbFile);
                } catch (IOException e) {
                    throw new RuntimeException("Error creating source database", e);
                }
            } else {
                // Toast.makeText(getBaseContext(), "db  exist", Toast.LENGTH_LONG).show();
            }
        }

        persianBitDictionary = (LinearLayout) findViewById(R.id.persianBitDictionary);
        persianBitDictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent();
                i.setClass(getBaseContext(), PersianBitDictionary.class);
                startActivity(i);

            }
        });
        englishBitDictionary = (LinearLayout) findViewById(R.id.englishBitDictionary);
        englishBitDictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();

                i.setClass(getBaseContext(), EnglishBitDictionary.class);
                startActivity(i);
            }
        });

        sourceitDictionary = (LinearLayout) findViewById(R.id.sourceitDictionary);
        sourceitDictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "https://bitcoind.me/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }
    public void copyDatabase2(File dbFile) throws IOException {
        InputStream is = getBaseContext().getAssets().open(DB_NAME);
        OutputStream os = new FileOutputStream(dbFile);

        byte[] buffer = new byte[1024];
        while (is.read(buffer) > 0) {
            os.write(buffer);
        }

        os.flush();
        os.close();
        is.close();
    }
}