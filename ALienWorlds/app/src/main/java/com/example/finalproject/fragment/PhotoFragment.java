package com.example.finalproject.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.finalproject.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PhotoFragment extends Fragment {

    private int[] photos = {R.drawable.dok1, R.drawable.dok2, R.drawable.dok3, R.drawable.dok4,
            R.drawable.dok5, R.drawable.dok6, R.drawable.dok7, R.drawable.dok8,
            R.drawable.dok9};
    private int currentPosition = 0;
    private ImageView imageView;
    private ExecutorService executorService;
    private Handler mainHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        imageView = view.findViewById(R.id.imageView);
        mainHandler = new Handler(Looper.getMainLooper());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void onResume() {
        super.onResume();
        startPhotoRotation();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopPhotoRotation();
    }

    private void startPhotoRotation() {
        executorService.submit(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(3000);
                    mainHandler.post(() -> {
                        currentPosition = (currentPosition + 1) % photos.length;
                        imageView.setImageResource(photos[currentPosition]);
                    });
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    private void stopPhotoRotation() {
        executorService.shutdownNow();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
        }
    }
}
