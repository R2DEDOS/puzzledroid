package com.example.puzzlecito;

import static android.os.SystemClock.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Después de cargar la aplicación, iniciamos la actividad prinicpal y finalizamos la actual
        startActivity(new Intent(Splash_screen.this, MainActivity.class));
        sleep(1000);
        finish();
    }
}