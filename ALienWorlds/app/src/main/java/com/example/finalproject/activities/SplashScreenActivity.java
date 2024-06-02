package com.example.finalproject.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.finalproject.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SplashScreenActivity extends AppCompatActivity {
    TextView connection, textView;
    Button btnretry;
    LottieAnimationView lottieProgressBar;
    ImageView background;

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        connection = findViewById(R.id.text_connection_lost);
        btnretry = findViewById(R.id.btn_retry);
        lottieProgressBar = findViewById(R.id.lottieProgressBar);
        background = findViewById(R.id.background_image);
        textView = findViewById(R.id.textView);

        connection.setVisibility(View.GONE);
        btnretry.setVisibility(View.GONE);
        lottieProgressBar.setVisibility(View.GONE);
        background.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        if (!isNetworkAvailable()) {
            btnretry.setVisibility(View.VISIBLE);
            connection.setVisibility(View.VISIBLE);

            btnretry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    retryNetworkCheck(executor);
                }
            });
        } else {
            showMainContent();
        }
    }

    private void retryNetworkCheck(ExecutorService executor) {
        if (isNetworkAvailable()) {
            btnretry.setVisibility(View.GONE);
            connection.setVisibility(View.GONE);
            lottieProgressBar.setVisibility(View.VISIBLE);
            lottieProgressBar.playAnimation();

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lottieProgressBar.setVisibility(View.GONE);
                            showMainContent();
                        }
                    });
                }
            });
        } else {
            lottieProgressBar.setVisibility(View.VISIBLE);
            lottieProgressBar.playAnimation();
            btnretry.setVisibility(View.GONE);
            connection.setVisibility(View.GONE);

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lottieProgressBar.setVisibility(View.GONE);
                            btnretry.setVisibility(View.VISIBLE);
                            connection.setVisibility(View.VISIBLE);
                        }
                    });
                }
            });
        }
    }

    private void showMainContent() {
        background.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the main activity
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}
