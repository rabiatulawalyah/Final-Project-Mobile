package com.example.finalproject.activities;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.finalproject.R;
import com.example.finalproject.fragment.LogoutFragment;
import com.example.finalproject.fragment.PlanetFragment;
import com.example.finalproject.fragment.PhotoFragment;

public class MainActivity extends AppCompatActivity {
    private ImageView iv_home, iv_logout, iv_photo;
    private FragmentManager fragmentManager;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_home = findViewById(R.id.IV_Home);
        iv_logout = findViewById(R.id.IV_Logout);
        iv_photo = findViewById(R.id.IV_Photo);
        fragmentManager = getSupportFragmentManager();

        PlanetFragment planetFragment = new PlanetFragment();
        replaceFragment(planetFragment);

        iv_home.setOnClickListener(v -> {
            replaceFragment(planetFragment);
        });

        iv_logout.setOnClickListener(v -> {
            LogoutFragment logoutFragment = new LogoutFragment();
            replaceFragment(logoutFragment);
        });

        iv_photo.setOnClickListener(v -> {
            PhotoFragment photoFragment = new PhotoFragment();
            replaceFragment(photoFragment);
        });
        replaceFragment(planetFragment);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction()
                .addToBackStack(null);
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
        currentFragment = fragment;
        updateNavigationIconColor();
    }

    private void updateNavigationIconColor() {
        if (currentFragment instanceof PlanetFragment) {
            changeNavigationIconColor(iv_home, R.color.green);
            changeNavigationIconColor(iv_logout, R.color.white);
            changeNavigationIconColor(iv_photo, R.color.white);
        } else if (currentFragment instanceof LogoutFragment) {
            changeNavigationIconColor(iv_home, R.color.white);
            changeNavigationIconColor(iv_logout, R.color.green);
            changeNavigationIconColor(iv_photo, R.color.white);
        } else if (currentFragment instanceof PhotoFragment) {
            changeNavigationIconColor(iv_home, R.color.white);
            changeNavigationIconColor(iv_logout, R.color.white);
            changeNavigationIconColor(iv_photo, R.color.green);
        }
    }

    private void changeNavigationIconColor(ImageView imageView, int drawableResId) {
        ColorStateList colorStateList = ContextCompat.getColorStateList(this, drawableResId);
        Drawable drawable = imageView.getDrawable();
        if (drawable != null) {
            drawable.setColorFilter(colorStateList.getDefaultColor(), PorterDuff.Mode.SRC_IN);
            imageView.setImageDrawable(drawable);
        }
    }
}