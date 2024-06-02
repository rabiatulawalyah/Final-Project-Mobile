package com.example.finalproject.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.finalproject.sql.DBHelper;
import com.example.finalproject.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity2 extends AppCompatActivity {

    TextView tv_welcome, textConnectionLost;
    Button btn_start, btnRetry;
    DBHelper dbHelper;
    LottieAnimationView lottieAlien, lottieProgressBar;
    View loginLayout;
    View background;

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
        setContentView(R.layout.activity_login2);

        tv_welcome = findViewById(R.id.tv_welcome);
        btn_start = findViewById(R.id.btn_start);
        dbHelper = new DBHelper(this);
        lottieAlien = findViewById(R.id.lottie_alien);
        textConnectionLost = findViewById(R.id.connectionlostLogin);
        btnRetry = findViewById(R.id.retryLogin);
        lottieProgressBar = findViewById(R.id.ProgressBarLogin);
        loginLayout = findViewById(R.id.loginLayout);
        background = findViewById(R.id.background_image_login);

        lottieProgressBar.setVisibility(View.GONE);
        textConnectionLost.setVisibility(View.GONE);
        btnRetry.setVisibility(View.GONE);

        String user = dbHelper.getLoggedInUser();

        if (user != null) {
            tv_welcome.setText("Hello " + user);
        } else {
            Intent intent = new Intent(LoginActivity2.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        btnRetry.setOnClickListener(v -> retryNetworkCheck(executor));

        btn_start.setOnClickListener(v -> showProgressAndCheckNetwork(executor, () -> {
            Intent intent = new Intent(LoginActivity2.this, MainActivity.class);
            startActivity(intent);
        }));
        checkNetworkOnStart();
    }
    private void checkNetworkOnStart() {
        if (!isNetworkAvailable()) {
            showConnectionLost();
        }
    }
    private void showProgressAndCheckNetwork(ExecutorService executor, Runnable onSuccess) {
        lottieProgressBar.setVisibility(View.VISIBLE);
        lottieProgressBar.playAnimation();
        loginLayout.setVisibility(View.GONE);
        background.setVisibility(View.GONE);
        lottieAlien.setVisibility(View.GONE);
        btn_start.setVisibility(View.GONE);

        executor.execute(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> {
                lottieProgressBar.setVisibility(View.GONE);
                if (isNetworkAvailable()) {
                    onSuccess.run();
                } else {
                    showConnectionLost();
                }
            });
        });
    }

    private void showConnectionLost() {
        lottieProgressBar.setVisibility(View.GONE);
        btnRetry.setVisibility(View.VISIBLE);
        textConnectionLost.setVisibility(View.VISIBLE);
        loginLayout.setVisibility(View.GONE);
        background.setVisibility(View.GONE);
    }

    private void retryNetworkCheck(ExecutorService executor) {
        lottieProgressBar.setVisibility(View.VISIBLE);
        lottieProgressBar.playAnimation();
        btnRetry.setVisibility(View.GONE);
        textConnectionLost.setVisibility(View.GONE);

        executor.execute(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> {
                lottieProgressBar.setVisibility(View.GONE);
                if (isNetworkAvailable()) {
                    loginLayout.setVisibility(View.VISIBLE);
                    background.setVisibility(View.VISIBLE);
                    lottieAlien.setVisibility(View.VISIBLE);
                    btn_start.setVisibility(View.VISIBLE);
                    textConnectionLost.setVisibility(View.GONE);
                    btnRetry.setVisibility(View.GONE);
                } else {
                    showConnectionLost();
                }
            });
        });
    }

    @Override
    public void onBackPressed() {
        // Handle back press logic if needed
        if (textConnectionLost.getVisibility() == View.VISIBLE) {
            // Don't call finish(), just do nothing to prevent going back to SplashScreen
            moveTaskToBack(true);
        } else {
            super.onBackPressed();
        }
    }
}
