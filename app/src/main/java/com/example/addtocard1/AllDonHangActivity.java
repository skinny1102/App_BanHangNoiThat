package com.example.addtocard1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

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

public class AllDonHangActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private String USER_ID;
    RecyclerView rcv,rcvchuaxacnhan,rcvdaxanhan;
    DondathangAdapter adapter;
    List<DonHang> donHangList;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRefdonhang = database.getReference("donhang");
    private Spinner spinner;
    private static final String[] paths = {"Tất cả đơn hàng", "Đơn chưa xác nhận ", "Đơn đã xác nhận"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_don_hang);
        getSupportActionBar().hide();
        Intent i = getIntent();
        USER_ID = i.getStringExtra("USER_ID");

        BackOnclick();

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
         android.R.layout.simple_spinner_item,paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
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
    private void getDonHangchuaxacnhan() {
        rcv = findViewById(R.id.rcv_donhangchuaxacnhan);
        adapter = new DondathangAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(linearLayoutManager);
        donHangList = new ArrayList<>();
        myRefdonhang.orderByChild("userId").equalTo(USER_ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1:snapshot.getChildren()) {
                    DonHang donHangObj = dataSnapshot1.getValue(DonHang.class);
                    if(donHangObj.isTrangThai()==false){
                    donHangList.add( new DonHang(dataSnapshot1.getKey(),donHangObj.getUserId(),donHangObj.getFullName(),donHangObj.getSdt(),donHangObj.getAddress(),
                            donHangObj.getDateDatHang(),donHangObj.getListProductDat(),donHangObj.isTrangThai(),donHangObj.getTongTien()
                    ));}
                    adapter.setData(donHangList);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        rcv.setAdapter(adapter);

    }
    private void getDonHangdaxacnhan() {
        rcv = findViewById(R.id.rcv_donhangdaxacnhan);
        adapter = new DondathangAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(linearLayoutManager);
        donHangList = new ArrayList<>();
        myRefdonhang.orderByChild("userId").equalTo(USER_ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1:snapshot.getChildren()) {
                    DonHang donHangObj = dataSnapshot1.getValue(DonHang.class);
                    if(donHangObj.isTrangThai()==true){
                        donHangList.add( new DonHang(dataSnapshot1.getKey(),donHangObj.getUserId(),donHangObj.getFullName(),donHangObj.getSdt(),donHangObj.getAddress(),
                                donHangObj.getDateDatHang(),donHangObj.getListProductDat(),donHangObj.isTrangThai(),donHangObj.getTongTien()
                        ));}
                    adapter.setData(donHangList);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        rcv.setAdapter(adapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                rcv = findViewById(R.id.rcv_donhang);
                rcvchuaxacnhan = findViewById(R.id.rcv_donhangdaxacnhan);
                rcvdaxanhan = findViewById(R.id.rcv_donhangdaxacnhan);
                Toast.makeText(AllDonHangActivity.this,"Tất cả đơn hàng ", Toast.LENGTH_LONG).show();
                rcv.setVisibility(View.VISIBLE);
                rcvchuaxacnhan.setVisibility(View.GONE);
                rcvdaxanhan.setVisibility(View.GONE);
                getDonHang();
                break;
            case 1:
                rcv = findViewById(R.id.rcv_donhang);
                rcvchuaxacnhan = findViewById(R.id.rcv_donhangdaxacnhan);
                rcvdaxanhan = findViewById(R.id.rcv_donhangdaxacnhan);
                Toast.makeText(AllDonHangActivity.this,"Đơn chưa xác nhận ", Toast.LENGTH_LONG).show();
                rcv.setVisibility(View.GONE);
                rcvdaxanhan.setVisibility(View.GONE);
                getDonHangchuaxacnhan();
                break;
            case 2:
                rcv = findViewById(R.id.rcv_donhang);
                rcvchuaxacnhan = findViewById(R.id.rcv_donhangdaxacnhan);
                // Whatever you want to happen when the thrid item gets selected
                Toast.makeText(AllDonHangActivity.this,"Đơn đã xác nhận", Toast.LENGTH_LONG).show();
                rcv.setVisibility(View.GONE);
                rcvchuaxacnhan.setVisibility(View.GONE);
                getDonHangdaxacnhan();
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}