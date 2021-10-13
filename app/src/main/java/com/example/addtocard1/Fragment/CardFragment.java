package com.example.addtocard1.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.example.addtocard1.DetailProductActivity;
import com.example.addtocard1.MainActivity;
import com.example.addtocard1.R;

public class CardFragment extends Fragment {
    private AHBottomNavigation ahBottomNavigation;
    private AHBottomNavigationViewPager ahBottomNavigationViewPager;
    MainActivity mainActivity;
    View view;
    ImageView imgBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainActivity = (MainActivity) getActivity();
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_card, container, false);
        getActionBackClick();
        return view;
    }

    private void getActionBackClick() {
        imgBack=view.findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ahBottomNavigation=mainActivity.findViewById(R.id.AHBottomNavigation);
                ahBottomNavigationViewPager =mainActivity.findViewById(R.id.AHBottomNavigationViewPager);
                ahBottomNavigation.setCurrentItem(0);
                ahBottomNavigationViewPager.setCurrentItem(0);
            }
        });
    }
}