package com.example.puzzlecito;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements Window.Callback {

    public static int elapsedTime = 0;
    public static int difficulty = 0;
    public static int total_level = 0;
    public static String name = "";

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
                    if (v.getId() == R.id.play) {
                        showScore();
                        final String[] files = am.list("img");
                        MainActivity.total_level = files.length;
                        for (int i = 0; i < files.length; i++) {
                            Intent intent = new Intent(v.getContext(), PuzzleActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("assetName", files[i]);
                            startActivity(intent);
                        }

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
                Intent intent_sc = new Intent(v.getContext(), Score.class);
//                intent_sc.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent_sc.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent_sc);


            }
        });


        //Listener for exit button
        Button exit_button = (Button) findViewById(R.id.exit);
        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    protected void showScore() {
        if (MainActivity.difficulty >= MainActivity.total_level ) {
            Intent intent_score = new Intent(getApplicationContext(), ScoreRegister.class);
            intent_score.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent_score.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent_score);
        }
    }


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

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                        System.exit(0);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }


}

