package com.example.puzzlecito;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreRegister extends AppCompatActivity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_score_register);

            TextView myname = findViewById(R.id.editTextTextPersonName);
            TextView myscore = findViewById(R.id.myscore);
            long minutes = (MainActivity.elapsedTime ) / 60;
            long seconds = (MainActivity.elapsedTime ) % 60;
            String time_score = minutes + ":" + seconds;
            myscore.setText(time_score);

            BBDDHelper dbHelper = new BBDDHelper(this);


            Button buttonAccept = findViewById(R.id.confirm);
            buttonAccept.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (v.getId() == R.id.confirm) {
                        String name = myname.getText().toString();

                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        ContentValues valores = new ContentValues();
                        valores.put(BBDDSchema.NAME, name);
                        valores.put(String.valueOf(BBDDSchema.TIME), MainActivity.elapsedTime);

                        //Insert in database
                        db.insert(BBDDSchema.TABLE, null, valores);

                        Intent intent_score = new Intent(getApplicationContext(), Score.class);
                        intent_score.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent_score.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent_score);

                    }
                }
            });
        }

    }