package com.example.puzzlecito;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Score extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);


        //            //initiate database
        BBDDHelper dbHelper = new BBDDHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        String selectQuery = "SELECT " + BBDDSchema.NAME + ", " + BBDDSchema.TIME + " FROM " + BBDDSchema.TABLE + " ORDER BY " + BBDDSchema.TIME + " ASC";


        TableLayout ranking = findViewById(R.id.RankingTable);
//        TableRow row = findViewById(R.id.row);
//        TextView column1 = findViewById(R.id.column1);
//        TextView column2 = findViewById(R.id.column2);


        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                TableRow row = new TableRow(this);
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
            }

        }
    }

    @Override
    public void onBackPressed() {

        Intent intent_main = new Intent(getApplicationContext(), MainActivity.class);
        intent_main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent_main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent_main);
    }


}
