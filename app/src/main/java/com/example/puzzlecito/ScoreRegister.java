package com.example.puzzlecito;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ScoreRegister extends AppCompatActivity {

    long minutes = 0;
    long seconds = 0;
    long countScore;

        public static String score;
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_score_register);

            TextView myname = findViewById(R.id.editTextTextPersonName);
            TextView myscore = findViewById(R.id.myscore);
            minutes = (MainActivity.elapsedTime ) / 60;
            seconds = (MainActivity.elapsedTime ) % 60;
            String time_score = minutes + ":" + seconds;
            myscore.setText(time_score);

            Button buttonAccept = findViewById(R.id.confirm);
            buttonAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (v.getId() == R.id.confirm) {

                        score = myscore.getText().toString();
                        String name = myname.getText().toString();

                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = firebaseDatabase.getReference();
                        DatabaseReference scorePointReference = databaseReference.child("Score");
                        scorePointReference.child(name).setValue(MainActivity.elapsedTime);


                        Intent intent_score = new Intent(getApplicationContext(), Score.class);
                        intent_score.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent_score.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent_score);

                    }
                }
            });

        }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference childReference = databaseReference.child("Score");
        childReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                countScore = snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }


    @Override
    public void onBackPressed() {
        MainActivity.difficulty = 0;
        Intent intent_main = new Intent(getApplicationContext(), MainActivity.class);
        intent_main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent_main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent_main);

    }


}