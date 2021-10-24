package com.example.puzzlecito;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static int elapsedTime = 0;
    public static int difficulty = 0;
//    public static boolean rollback = false;
    //public static boolean endgame = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Listener for play button
        Button play = (Button) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssetManager am = getAssets();
                try {
                    if (v.getId()==R.id.play) {
                        final String[] files = am.list("img");
                        for (int i = 0; i < files.length; i++) {
                            Intent intent = new Intent(v.getContext(), PuzzleActivity.class);
                            intent.putExtra("assetName", files[i]);
                            startActivity(intent);
//                            if (rollback) {
//                                break;
//                            }
                        }
//                        Intent intent_score = new Intent(v.getContext(), Score_register.class);
//                        startActivity(intent_score);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //Listener for score button
        Button score_button = (Button) findViewById(R.id.score);
        score_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ScoreRegister.class));
            }
        });

        //Listener for exit button
        Button exit_button = (Button) findViewById(R.id.exit);
        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


//    @Override
//    public void onResume() {
//        super.onResume();
//        Intent intent_score = new Intent(v.getContext(), Score_register.class);
//        startActivity(intent_score);
//    }

    @Override public boolean onCreateOptionsMenu(Menu mymenu){
        getMenuInflater().inflate(R.menu.menu, mymenu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override public boolean onOptionsItemSelected(MenuItem option_menu){
        //int id = option_menu.getItemId();
        switch (option_menu.getItemId()) {

            case R.id.Images:
                Intent myIntent = new Intent(MainActivity.this,SelectImage.class);
                startActivity(myIntent);
                return true;

            case R.id.Help:
                // Se abre la WebView con la ayuda
                Intent help = new Intent(this, HelpActivity.class);
                startActivity(help);
                return true;

            default:
                return super.onOptionsItemSelected(option_menu);
        }
    }


}

