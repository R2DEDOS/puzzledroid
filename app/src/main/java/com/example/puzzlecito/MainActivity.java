package com.example.puzzlecito;

import static java.lang.Math.abs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Listener for demo
        Button play = (Button) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssetManager am = getAssets();
                try {
                    final String[] files = am.list("img");
                    for(int i=0; i<files.length; i++) {
                        Intent intent = new Intent(v.getContext(), PuzzleActivity.class);
                        intent.putExtra("assetName", files[i]);
                        startActivity(intent);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    @Override public boolean onCreateOptionsMenu(Menu mymenu){
        getMenuInflater().inflate(R.menu.menu, mymenu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem option_menu){
        int id = option_menu.getItemId();

        if(id==R.id.Help){
            return true;
        }

        if(id==R.id.Images){
            Intent myIntent = new Intent(MainActivity.this,SelectImage.class);
            startActivityForResult(myIntent, 0);
            return true;
        }
        return super.onOptionsItemSelected(option_menu);

    }
}

