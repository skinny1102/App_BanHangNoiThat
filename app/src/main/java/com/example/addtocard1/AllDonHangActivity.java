package com.example.addtocard1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.addtocard1.Adapter.DondathangAdapter;
import com.example.addtocard1.Doituong.DonHang;
import com.example.addtocard1.Doituong.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllDonHangActivity extends AppCompatActivity {
    private String USER_ID;
    RecyclerView rcv;
    DondathangAdapter adapter;
    List<DonHang> donHangList;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRefdonhang = database.getReference("donhang");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_don_hang);
        getSupportActionBar().hide();
        Intent i = getIntent();
        USER_ID = i.getStringExtra("USER_ID");
        getDonHang();
        BackOnclick();
    }
    private void BackOnclick(){
      ImageView imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                Intent i = new Intent(DonHangActivity.this, MainActivity.class);
//                DonHangActivity.this.finish(); //Use your current activity
//                startActivity(i);
            }
        });

    }
    private void getDonHang() {
        rcv = findViewById(R.id.rcv_donhang);
        adapter = new DondathangAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(linearLayoutManager);
        donHangList = new ArrayList<>();
        myRefdonhang.orderByChild("userId").equalTo(USER_ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot dataSnapshot1:snapshot.getChildren()) {
                    DonHang donHangObj = dataSnapshot1.getValue(DonHang.class);

                    donHangList.add( new DonHang(dataSnapshot1.getKey(),donHangObj.getUserId(),donHangObj.getFullName(),donHangObj.getSdt(),donHangObj.getAddress(),
                            donHangObj.getDateDatHang(),donHangObj.getListProductDat(),donHangObj.isTrangThai(),donHangObj.getTongTien()
                            ));
                    adapter.setData(donHangList);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        rcv.setAdapter(adapter);

    }
}