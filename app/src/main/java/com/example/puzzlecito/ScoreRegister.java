package com.example.puzzlecito;

import android.content.ContentValues;
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


//            //initiate database
//            BBDDHelper dbHelper = new BBDDHelper(this);
//            SQLiteDatabase db = dbHelper.getReadableDatabase();
//
//            //query
//            String[] projection = {
//                    BBDDSchema.NAME,
//                    String.valueOf(BBDDSchema.TIME)
//            };
//
//            Cursor cursor = db.query(
//                    BBDDSchema.TABLE,   // Table query
//                    projection,                 // Columns to return
//                    null,
//                    null,
//                    null,
//                    null,
//                    BBDDSchema.TIME + " DESC" //Order by score
//            );


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


                        SQLiteDatabase db = dbHelper.getReadableDatabase();
                        // Insertamos la puntuación en la BBDD
                        ContentValues valores = new ContentValues();
                        valores.put(BBDDSchema.NAME, name);
                        valores.put(String.valueOf(BBDDSchema.TIME), MainActivity.elapsedTime);

                        //Insert in database
                        db.insert(BBDDSchema.TABLE, null, valores);
                    }
                }
            });
            // Esperamos 3 segundos para cargar el siguiente puzzle
            //sc.postDelayed(Score_register.this, 3000);
        }


//        @Override
//        protected void onSaveInstanceState(@NonNull Bundle outState) {
//            super.onSaveInstanceState(outState);
//            outState.putInt("name", BBDDSchema.NAME);
//        }


//    // Este método dispara los acontecimientos que ocurren cuando se completa el puzzle.
//    public void setOnCompleteCallback(OnCompleteCallback onCompleteCallback) {
//        OnCompleteCallback occ = onCompleteCallback;
//    }
//
//    public interface OnCompleteCallback {
//        void onComplete();
//    }
//

    }