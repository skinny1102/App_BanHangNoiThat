package com.example.addtocard1.Fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.example.addtocard1.EditProfildeActivity;
import com.example.addtocard1.MainActivity;
import com.example.addtocard1.R;
import com.example.addtocard1.SigninActivity;
import com.example.addtocard1.Doituong.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment {
    private String USER_ID;
        View view ;
        Button btnLogout;
        MainActivity mainActivity;
        TextView tvUserName,tvSdt,tvEmail;
         ImageView imgBack;
         ListView lv;
         ArrayAdapter<String> adapter;

    private AHBottomNavigation ahBottomNavigation;
    private AHBottomNavigationViewPager ahBottomNavigationViewPager;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRefProfile = database.getReference("profile");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user, container, false);
        btnLogout = view.findViewById(R.id.btn_logout);
        mainActivity = (MainActivity) getActivity();
        USER_ID=mainActivity.getG_uid();
        initUI();
        getActionBackClick();
        getDataUserUI();
        clickEditProfile();
        initListview();
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

    private void initUI(){
        tvUserName = view.findViewById(R.id.tv_username);
        tvSdt = view.findViewById(R.id.tv_phoneNumber);
        tvEmail = view.findViewById(R.id.tv_email);
    }
    private void initListview(){
        lv = view.findViewById(R.id.lvdonhang);
        adapter = new ArrayAdapter<>(mainActivity, android.R.layout.simple_list_item_1,getlist());
        lv.setAdapter(adapter);
    }

    private List<String> getlist() {
        List<String> list = new ArrayList<>();
        list.add("Đơn hàng của bạn");
        return list;
    }

    private void getActionBackClick() {

        imgBack=view.findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ahBottomNavigation=mainActivity.findViewById(R.id.AHBottomNavigation);
                ahBottomNavigationViewPager =mainActivity.findViewById(R.id.AHBottomNavigationViewPager);
                ahBottomNavigation.setCurrentItem(1);
                ahBottomNavigationViewPager.setCurrentItem(1);

            }
        });
    }
    private void getDataUserUI(){
        myRefProfile.child(USER_ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                        User userObj = snapshot.getValue(User.class);
                        if(userObj!=null){
                            /// IF ELSE ĐẾN CHẾT :)))
                            if(userObj.getFullName()!=null){
                                tvUserName.setText(userObj.getFullName());
                                UpdateUIName(1,tvUserName);
                            }else {
                                tvUserName.setText("(Hãy cập nhật tên đầy đủ)");
                                UpdateUIName(2,tvUserName);
                            }
                            if(userObj.getPhoneNumber()!=null){
                                tvSdt.setText(userObj.getPhoneNumber());
                                UpdateUIName(1,tvSdt);
                            }else {
                                tvSdt.setText("(Hãy cập nhật số điện thoại)");
                                UpdateUIName(2,tvSdt);
                            }
                            if(userObj.getEmail()!=null){
                                tvEmail.setText(userObj.getEmail());
                                UpdateUIName(1,tvEmail);
                            }else {
                                tvEmail.setText("(Hãy cập nhật email)");
                                UpdateUIName(2,tvEmail);
                            }
                        }else {
                            tvUserName.setText("(Hãy cập nhật tên đầy đủ)");
                            UpdateUIName(2,tvUserName);
                            tvSdt.setText("(Hãy cập nhật số điện thoại)");
                            UpdateUIName(2,tvSdt);
                            tvEmail.setText("(Hãy cập nhật số email)");
                            UpdateUIName(2,tvEmail);
                        }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void UpdateUIName(int i,TextView tv){
        switch (i){
            case 1:
                tv.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tv.setTextSize(16);
                break;
            case 2:
                tv.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                tv.setTextSize(13);
                break;
            default:
                break;
        }
    }
    public void clickEditProfile(){
        TextView tvEdit = view.findViewById(R.id.edit_profile);
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mainActivity, EditProfildeActivity.class);
                    i.putExtra("USER_ID",USER_ID);
                    startActivity(i);
            }
        });
    }

}