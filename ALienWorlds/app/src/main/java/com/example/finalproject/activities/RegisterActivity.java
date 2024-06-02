package com.example.finalproject.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.finalproject.sql.DBHelper;
import com.example.finalproject.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterActivity extends AppCompatActivity {

    EditText et_user, et_password;
    Button btn_register, btnRetry;
    DBHelper dbHelper;
    TextView textConnectionLost;
    LottieAnimationView lottieProgressBar;
    LinearLayout registerLayout;
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
        setContentView(R.layout.activity_register);

        dbHelper = new DBHelper(this);

        et_user = findViewById(R.id.et_userRegister);
        et_password = findViewById(R.id.et_passwordRegister);
        btn_register = findViewById(R.id.btn_register2);
        textConnectionLost = findViewById(R.id.connectionlostRegister);
        btnRetry = findViewById(R.id.retryRegister);
        lottieProgressBar = findViewById(R.id.ProgressBarRegister);
        registerLayout = findViewById(R.id.allregister);
        background = findViewById(R.id.background_image_register);

        lottieProgressBar.setVisibility(View.GONE);
        textConnectionLost.setVisibility(View.GONE);
        btnRetry.setVisibility(View.GONE);

        ExecutorService executor = Executors.newSingleThreadExecutor();

        btnRetry.setOnClickListener(v -> retryNetworkCheck(executor));

        btn_register.setOnClickListener(view -> {
            String user = et_user.getText().toString().trim();
            String password = et_password.getText().toString().trim();

            if (user.isEmpty() || password.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Silakan isi semua bidang", Toast.LENGTH_SHORT).show();
            } else if (dbHelper.isUsernameExists(user)) {
                showProgressAndCheckNetwork(executor,() -> {
                    Toast.makeText(RegisterActivity.this, "Akun sudah terdaftar", Toast.LENGTH_SHORT).show();
                    showRegisterForm();
                });
            } else {
                showProgressAndCheckNetwork(executor, () -> {
                    registerUser(user, password);
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);

                });
            }
        });
    }


    private void showProgressAndCheckNetwork(ExecutorService executor, Runnable onSuccess) {
        lottieProgressBar.setVisibility(View.VISIBLE);
        lottieProgressBar.playAnimation();
        registerLayout.setVisibility(View.GONE);
        background.setVisibility(View.GONE);

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
        registerLayout.setVisibility(View.GONE);
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
                    registerLayout.setVisibility(View.VISIBLE);
                    background.setVisibility(View.VISIBLE);
                } else {
                    showConnectionLost();
                }
            });
        });
    }

    private void showRegisterForm() {
        registerLayout.setVisibility(View.VISIBLE);
        background.setVisibility(View.VISIBLE);
        btnRetry.setVisibility(View.GONE);
        textConnectionLost.setVisibility(View.GONE);
    }

    private void registerUser(String user, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.UserEntry.COLUMN_NAME_USERNAME, user);
        values.put(DBHelper.UserEntry.COLUMN_NAME_PASSWORD, password);

        long newRowId = db.insert(DBHelper.UserEntry.TABLE_NAME, null, values);

        if (newRowId != -1) {
            Toast.makeText(RegisterActivity.this, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(RegisterActivity.this, "Gagal mendaftar", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (isNetworkAvailable()) {
            showConnectionLost();
        } else {
            super.onBackPressed();
        }
    }
}
