package com.example.digitalclassroom;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    private ProgressBar progressBar;
    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                doprogress();
                startapp();
            }
        });
        thread.start();

    }

    private void startapp() {
        startActivity(new Intent(SplashScreen.this, LoginPage.class));
        finish();

    }

    private void doprogress() {

        for(progress = 33; progress<=99; progress = progress+33){
            try {
                Thread.sleep(1000);
                progressBar.setProgress(progress);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
