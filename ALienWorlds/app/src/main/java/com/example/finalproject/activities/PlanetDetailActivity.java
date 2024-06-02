package com.example.finalproject.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.model.Planet;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlanetDetailActivity extends AppCompatActivity {

    private ImageView planetImageView;
    private TextView planetNameTextView;
    private TextView planetDescriptionTextView;
    private TextView planetVolumeTextView;
    private TextView planetMassTextView;
    private LottieAnimationView lottieProgressBar;
    private TextView connectionLostText;
    private Button retryButton;

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet_detail);

        planetImageView = findViewById(R.id.planet_detail_image);
        lottieProgressBar = findViewById(R.id.ProgressBar2);
        planetNameTextView = findViewById(R.id.planet_detail_name);
        planetDescriptionTextView = findViewById(R.id.planet_detail_description);
        planetVolumeTextView = findViewById(R.id.planet_detail_volume);
        planetMassTextView = findViewById(R.id.planet_detail_mass);
        connectionLostText = findViewById(R.id.connectionlostDetail);
        retryButton = findViewById(R.id.retryDetail);

        Planet planet = (Planet) getIntent().getSerializableExtra("planet");

        // Initial state
        hideContent();
        lottieProgressBar.setVisibility(View.VISIBLE);
        connectionLostText.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);

        checkNetworkAndLoadData(planet);

        ImageButton backButton = findViewById(R.id.button_back_to_main);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanetDetailActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        retryButton.setOnClickListener(v -> {
            connectionLostText.setVisibility(View.GONE);
            retryButton.setVisibility(View.GONE);
            lottieProgressBar.setVisibility(View.VISIBLE);
            lottieProgressBar.playAnimation();
            new Handler().postDelayed(() -> checkNetworkAndLoadData(planet), 2000);
        });
    }

    private void checkNetworkAndLoadData(Planet planet) {
        if (isNetworkAvailable()) {
            loadData(planet);
        } else {
            showConnectionLost();
        }
    }

    private void loadData(Planet planet) {
        lottieProgressBar.setVisibility(View.VISIBLE);
        lottieProgressBar.playAnimation();
        hideContent();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                Thread.sleep(3000); // Simulasi loading data
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> {
                lottieProgressBar.setVisibility(View.GONE);
                if (planet != null) {
                    Glide.with(PlanetDetailActivity.this)
                            .load(planet.getImgSrc().getImg())
                            .into(planetImageView);
                    planetNameTextView.setText(planet.getName());
                    planetDescriptionTextView.setText(planet.getDescription());
                    planetVolumeTextView.setText("Volume: " + planet.getBasicDetails().getVolume());
                    planetMassTextView.setText("Mass: " + planet.getBasicDetails().getMass());
                }
                showContent();
            });
        });
    }

    private void showConnectionLost() {
        lottieProgressBar.setVisibility(View.GONE);
        hideContent();
        connectionLostText.setVisibility(View.VISIBLE);
        retryButton.setVisibility(View.VISIBLE);
    }

    private void hideContent() {
        planetImageView.setVisibility(View.GONE);
        planetNameTextView.setVisibility(View.GONE);
        planetDescriptionTextView.setVisibility(View.GONE);
        planetVolumeTextView.setVisibility(View.GONE);
        planetMassTextView.setVisibility(View.GONE);
    }

    private void showContent() {
        planetImageView.setVisibility(View.VISIBLE);
        planetNameTextView.setVisibility(View.VISIBLE);
        planetDescriptionTextView.setVisibility(View.VISIBLE);
        planetVolumeTextView.setVisibility(View.VISIBLE);
        planetMassTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (connectionLostText.getVisibility() == View.VISIBLE) {
            moveTaskToBack(true);
        } else {
            super.onBackPressed();
        }
    }
}
