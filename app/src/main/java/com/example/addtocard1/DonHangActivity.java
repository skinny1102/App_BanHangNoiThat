package com.example.addtocard1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.addtocard1.Adapter.AddressAdapter;
import com.example.addtocard1.Adapter.ProductCartAdapter;
import com.example.addtocard1.Adapter.ProductDonDatHangAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class DonHangActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private String USER_ID;
    ProductDonDatHangAdapter adapter;
    private List<Product> list;
    TextView tvTongtien,chosseAdress,tvDatHang;
    ImageView imgBack;
    int Tongtien;
    RadioGroup radioGroup;
    RadioButton radioButton,radioButton1,radioButton2;
    String hinhthucthanhtoan,address;
    ListView listViewAddress;
    AddressAdapter adapterAddress;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refAddress = database.getReference("profile");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang);
        getSupportActionBar().hide();
        Intent i = getIntent();
        list = (List<Product>) i.getSerializableExtra("list_product");
        Tongtien = i.getIntExtra("tongtien",0);
        USER_ID = i.getStringExtra("USER_ID");
        getviewProductDatHang();
        BackOnclick();
        getviewAdress();
        chosseAdress();
        Dathang();
    }

    private void getviewProductDatHang() {
        recyclerView = findViewById(R.id.rcv_dondathang_product);
        adapter = new ProductDonDatHangAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.setData(list);
        recyclerView.setAdapter(adapter);
        tvTongtien = findViewById(R.id.tv_tongtien);
        tvTongtien.setText(tiente(Tongtien)+" vnđ");

    }
    private String  tiente(int price){
        String str1 = NumberFormat.getIntegerInstance().format(price);
        return str1;
    }
    private void BackOnclick(){
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DonHangActivity.this, MainActivity.class);
                DonHangActivity.this.finish(); //Use your current activity
                startActivity(i);
            }
        });

    }
    private  void chosseAdress(){
        chosseAdress = findViewById(R.id.tv_chosseAdress);
        chosseAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DonHangActivity.this, AddressActivity.class);
                intent.putExtra("USER_ID",USER_ID);
                startActivity(intent);
            }
        });
    }
    private void getviewAdress(){
        listViewAddress = findViewById(R.id.lv_adress);
        adapterAddress = new AddressAdapter(DonHangActivity.this, R.layout.item_address,getListAddress());
        listViewAddress.setAdapter(adapterAddress);

    }

    private List<String> getListAddress() {
        List<String> listAdress = new ArrayList<>();
       refAddress.child(USER_ID).child("address").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {

               for (DataSnapshot postSnapshot : snapshot.getChildren()){
                   if(postSnapshot.getValue()==null){
                       Toast.makeText(DonHangActivity.this,"Chưa có địa chỉ",Toast.LENGTH_LONG).show();
                   }else {
                       String address =  postSnapshot.getValue().toString();
                       listAdress.add(address);
                       adapterAddress.notifyDataSetChanged();
                   }
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
        return listAdress;
    }
    private void Dathang(){
        tvDatHang = findViewById(R.id.tv_dathang);
        tvDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAddress();
                getthanhtoan();
                Toast.makeText(DonHangActivity.this,"Đã lấy được địa chỉ và hình thức thanh toán test nút xẽm có click được hay không",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void getthanhtoan(){
        radioGroup = findViewById(R.id.gr_radiobutton);
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        if(radioButton==null){
            Toast.makeText(DonHangActivity.this,"Hãy chọn hình thức thanh toán",Toast.LENGTH_LONG).show();
            return;
        }
        else{
            hinhthucthanhtoan = radioButton.getText().toString().trim();
        }
    }
    private void getAddress(){
        if(adapterAddress.getStrAddress()==null){
            Toast.makeText(DonHangActivity.this,"Hãy chọn địa chỉ",Toast.LENGTH_LONG).show();
            return;
        }else {
            address = adapterAddress.getStrAddress().toString().trim();
        }

    }
}