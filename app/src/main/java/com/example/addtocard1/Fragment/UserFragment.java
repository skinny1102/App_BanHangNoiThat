package com.example.addtocard1.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.addtocard1.MainActivity;
import com.example.addtocard1.R;
import com.example.addtocard1.SigninActivity;
import com.google.firebase.auth.FirebaseAuth;

public class UserFragment extends Fragment {
        View view ;
        Button btnLogout;
        MainActivity mainActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user, container, false);
        btnLogout = view.findViewById(R.id.btn_logout);
        mainActivity = (MainActivity) getActivity();
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(mainActivity, SigninActivity.class);
                startActivity(intent);
            }
        });
        return  view;
    }
}