package com.example.puzzlecito;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements Window.Callback {

    public static int elapsedTime = 0;
    public static int difficulty = 0;
    public static int total_level = 0;
    public static String name = "";

    public static int imagesview = 0;
    long count = 3;
    public static long level=0;
    long contador;
    final Long[] time = new Long[1];
    final Long[] level_ = new Long[1];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        //retrieve time and level
        time[0] = (long) 0;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                level_[0] = snapshot.child("Level").child(GoogleSignInActivity.uid).getValue(Long.class);
                if (level_[0] == null) level = 0;
                else level = Math.toIntExact(level_[0]);
                difficulty = (int) level;
                time[0] = snapshot.child("Time").child(GoogleSignInActivity.uid).getValue(Long.class);
                if (time[0] == null) time[0] = 0L;
                else elapsedTime += time[0];

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        Button play = (Button) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.play) {


                    contador = count - level;
                    while(contador > 0){
                        //System.out.println("Count del main: "+count + "\ni: "+ i);
                        Intent intent = new Intent(v.getContext(), PuzzleActivity.class);
                        intent.putExtra("contador", String.valueOf(contador));
                        intent.putExtra("totalImages", String.valueOf(count-level));
                        startActivity(intent);
                        contador--;
                    }
                }

            }
        });


        //Listener for score button
        Button score_button = (Button) findViewById(R.id.score);
        score_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_sc = new Intent(v.getContext(), Score.class);
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

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override public boolean onCreateOptionsMenu(Menu mymenu){
        getMenuInflater().inflate(R.menu.menu, mymenu);
        return true;
    }

    Uri uri = null;
    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                //the selected audio.
                uri = data.getData();
                Music.player = MediaPlayer.create(this, uri);
                Music.player.start();

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("NonConstantResourceId")
    @Override public boolean onOptionsItemSelected(MenuItem option_menu){
        //int id = option_menu.getItemId();
        switch (option_menu.getItemId()) {

            case R.id.Help:
                //Open webview with help
                Intent help = new Intent(this, HelpActivity.class);
                startActivity(help);
                return true;


            case R.id.Music:
                //On, Of music
                if (Music.player == null) {
                    if (uri == null) {
                        Music.player = MediaPlayer.create(this, R.raw.musica_fondo);
                    } else {
                        Music.player = MediaPlayer.create(this, uri);
                    }
                }
                if (!Music.player.isPlaying()) Music.player.start();
                else {
                    Music.player.stop();
                    Music.player = null;
                }

                return true;


            case R.id.MusicFile:
                Intent intent_upload = new Intent();
                intent_upload.setType("audio/*");
                intent_upload.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent_upload,1);
                return true;

            default:
                return super.onOptionsItemSelected(option_menu);
        }
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.onBackPressed)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                        System.exit(0);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }


}

