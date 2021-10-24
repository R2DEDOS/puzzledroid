package com.example.puzzlecito;

import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {
        private WebView vistaAyuda;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_help);

            vistaAyuda = findViewById(R.id.activityhelp);
            vistaAyuda.setWebViewClient(new WebViewClient());
            vistaAyuda.loadUrl(getString(R.string.urlhelp));

        }

        @Override
        public void onBackPressed() {
            if (vistaAyuda.canGoBack()){
                vistaAyuda.goBack();
            }else {
                super.onBackPressed();
            }
        }

    }