package com.example.addtocard1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.addtocard1.Adapter.ProductDonDatHangAdapter;
import com.example.addtocard1.Doituong.DonHang;
import com.example.addtocard1.Doituong.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.List;

public class ChitietDonhangActivity extends AppCompatActivity {
    String madonhang;
    TextView tvmadonhang,tvusername , tvSdt,tvNgaydathang,tvdiachi,tvTongtien,tvtrangthai;
    RecyclerView recyclerView;
    ProductDonDatHangAdapter adapter;
    ImageView imgBack;
    List<Product> mlistproduct;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRefdonhang = database.getReference("donhang");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_donhang);
        getSupportActionBar().hide();
        Intent i = getIntent();
        madonhang = i.getStringExtra("maDonhang");
        getUi();
        getviewDonhang();
        BackOnclick();
    }

    private void getUi() {
        tvmadonhang= findViewById(R.id.tv_madonhang);
        tvusername = findViewById(R.id.tv_Nameuser);
        tvSdt = findViewById(R.id.tv_SĐT);
        tvNgaydathang=findViewById(R.id.tv_ngaydathang);
        tvdiachi = findViewById(R.id.tv_diachi);
        tvTongtien = findViewById(R.id.tv_tongtien);
        tvtrangthai = findViewById(R.id.tv_trangthai);

    }
    private void BackOnclick(){
        imgBack = findViewById(R.id.img_back);
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
    private void getviewDonhang() {
        tvmadonhang.setText(madonhang);
        myRefdonhang.child(madonhang).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DonHang donHangObj = new DonHang();
                donHangObj=    snapshot.getValue(DonHang.class);

                      tvusername.setText(donHangObj.getFullName());
                    tvSdt.setText(donHangObj.getSdt());
                    tvdiachi.setText(donHangObj.getAddress());
                    if(!donHangObj.isTrangThai()){
                        tvtrangthai.setText("Chưa xác nhận");
                    }else {
                        tvtrangthai.setText("Đã xác nhận");
                    }
                    tvNgaydathang.setText(donHangObj.getDateDatHang());
                    mlistproduct=donHangObj.getListProductDat();
                    recyclerView = findViewById(R.id.rcv_dondathang_product);
                    adapter = new ProductDonDatHangAdapter(ChitietDonhangActivity.this);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChitietDonhangActivity.this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    adapter.setData(mlistproduct);
                    recyclerView.setAdapter(adapter);
                    tvTongtien.setText(tiente(donHangObj.getTongTien())+" vnđ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private String  tiente(int price){
        String str1 = NumberFormat.getIntegerInstance().format(price);
        return str1;
    }
}