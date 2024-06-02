package com.example.finalproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.airbnb.lottie.LottieAnimationView;
import com.example.finalproject.R;
import com.example.finalproject.activities.LoginActivity;
import com.example.finalproject.sql.DBHelper;

public class LogoutFragment extends Fragment {

    TextView tv_welcome2;
    Button btn_logout;
    LottieAnimationView lottie_goodbye;
    DBHelper dbHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logout, container, false);

        tv_welcome2 = view.findViewById(R.id.tv_welcome2);
        btn_logout = view.findViewById(R.id.btn_logout);
        lottie_goodbye = view.findViewById(R.id.lottie_goodbye);
        dbHelper = new DBHelper(getActivity());

        String user = dbHelper.getLoggedInUser();

        if (user != null) {
            tv_welcome2.setText("Good Bye " + user);
        } else {
            // If no user is logged in, redirect to login
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Logout the user
                dbHelper.logout();
                // Redirect to Login
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .commit();
    }
}
