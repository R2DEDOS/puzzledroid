package com.example.puzzlecito;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Score extends AppCompatActivity {

    private int rows_score = 15;

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
            for (int x=0; x <= rows_score && cursor.moveToNext(); x++) {

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
    }


    @Override
    public void onBackPressed() {
        Intent intent_main = new Intent(getApplicationContext(), MainActivity.class);
        intent_main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent_main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent_main);
    }


}
