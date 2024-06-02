package com.example.finalproject.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.finalproject.R;
import com.example.finalproject.adapter.PlanetAdapter;
import com.example.finalproject.api.ApiClient;
import com.example.finalproject.api.ApiService;
import com.example.finalproject.model.Planet;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Response;

public class PlanetFragment extends Fragment {
    private RecyclerView recyclerView;
    private PlanetAdapter adapter;
    private LottieAnimationView lottieProgressBar;
    private TextView connectionLostText;
    private Button retryButton;

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_planet, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        lottieProgressBar = view.findViewById(R.id.ProgressBarfragment);
        connectionLostText = view.findViewById(R.id.connectionlostfragment);
        retryButton = view.findViewById(R.id.retryfragment);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PlanetAdapter(getContext());
        recyclerView.setAdapter(adapter);

        lottieProgressBar.setVisibility(View.VISIBLE);
        lottieProgressBar.playAnimation();

        retryButton.setOnClickListener(v -> {
            connectionLostText.setVisibility(View.GONE);
            retryButton.setVisibility(View.GONE);

            if (isNetworkAvailable()) {
                loadData();
            } else {
                lottieProgressBar.setVisibility(View.VISIBLE);
                lottieProgressBar.playAnimation();

                new Handler().postDelayed(() -> {
                    lottieProgressBar.setVisibility(View.GONE);
                    connectionLostText.setVisibility(View.VISIBLE);
                    retryButton.setVisibility(View.VISIBLE);
                }, 2000);
            }
        });



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadData();
    }

    private void loadData() {
        if (!isNetworkAvailable()) {
            showConnectionLost();
            return;
        }

        lottieProgressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        connectionLostText.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            ApiService service = ApiClient.getClient().create(ApiService.class);
            Call<List<Planet>> call = service.getPlanets();

            try {
                Response<List<Planet>> response = call.execute();
                if (response.isSuccessful()) {
                    List<Planet> planets = response.body();
                    handler.post(() -> {
                        if (planets != null) {
                            adapter.setData(planets);
                            lottieProgressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            connectionLostText.setVisibility(View.GONE);
                            retryButton.setVisibility(View.GONE);
                        }
                    });
                } else {
                    handler.post(() -> showConnectionLost());
                }
            } catch (Exception e) {
                handler.post(() -> {
                    Toast.makeText(getContext(), "Request failed", Toast.LENGTH_SHORT).show();
                    showConnectionLost();
                });
            }
        });
    }
    private void showConnectionLost() {
        lottieProgressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        connectionLostText.setVisibility(View.VISIBLE);
        retryButton.setVisibility(View.VISIBLE);
    }
}
