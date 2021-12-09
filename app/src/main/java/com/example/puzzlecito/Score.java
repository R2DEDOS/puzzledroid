package com.example.puzzlecito;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.CalendarContract;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Calendar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Score extends AppCompatActivity {

    private int rows_score = 15;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        BBDDHelper dbHelper = new BBDDHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " + BBDDSchema.NAME + ", " + BBDDSchema.TIME + " FROM " + BBDDSchema.TABLE + " ORDER BY " + BBDDSchema.TIME + " ASC";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);

        TableLayout ranking = findViewById(R.id.RankingTable);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int x=0; x < rows_score && x < cursor.getCount() ; x++) {

                TableRow row = new TableRow(this);
                row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT));
                row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));
                TextView column1 = new TextView(this);
                TextView column2 = new TextView(this);

                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    TextView text = new TextView(this);
                    text.setText(cursor.getString(i));
                    if(i==1){
                        column1.setText(text.getText());
                    }else{
                        column2.setText(text.getText());
                    }
                }
                row.addView(column2);
                row.addView(column1);
                ranking.addView(row);
                cursor.moveToNext();
            }
        }

        Button backToMenu = findViewById(R.id.backToMenu);
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_main = new Intent(getApplicationContext(), MainActivity.class);
                intent_main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent_main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent_main);

            }
        });
        MainActivity.difficulty = 0;
    }



}
