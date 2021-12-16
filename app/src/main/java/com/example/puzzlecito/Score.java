package com.example.puzzlecito;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Score extends AppCompatActivity {

    private final int rows_score = 10;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

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

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference childReference = databaseReference.child("Score");

        Query query = childReference.orderByValue();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TableLayout ranking = (TableLayout) findViewById(R.id.RankingTable);
                int i = 0;
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    if (i < rows_score) {
                        String name = userSnapshot.getKey();
                        assert name != null;
                        Long score = (Long) userSnapshot.getValue();
                        TableRow row = new TableRow(Score.this);
                        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT));
                        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));
                        TextView column1 = new TextView(Score.this);
                        TextView column2 = new TextView(Score.this);
                        column2.setText(name);
                        column1.setText(String.valueOf(score));
                        row.addView(column2);
                        row.addView(column1);
                        ranking.addView(row);
                        i++;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
