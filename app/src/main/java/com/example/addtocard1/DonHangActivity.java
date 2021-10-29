package com.example.addtocard1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.addtocard1.Adapter.AddressAdapter;
import com.example.addtocard1.Adapter.ProductDonDatHangAdapter;
import com.example.addtocard1.Doituong.DonHang;
import com.example.addtocard1.Doituong.Product;
import com.example.addtocard1.Doituong.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DonHangActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private String USER_ID;
    ProductDonDatHangAdapter adapter;
    private List<Product> list;
    TextView tvTongtien,chosseAdress,tvDatHang,tvAddress, tvFullName , tvSdt;
    ImageView imgBack;
    int Tongtien;
    RadioGroup radioGroup;
    RadioButton radioButton,radioButton1,radioButton2;
    String hinhthucthanhtoan,address,fullName,Sdt,dateTime;
    ListView listViewAddress;
    AddressAdapter adapterAddress;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refcart = database.getReference("cart");
    DatabaseReference refProfile = database.getReference("profile");
    DatabaseReference refDonhang = database.getReference("donhang");

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
        getDataViewthongtin();
        getviewAdress();
        chosseAdress();
        onclickDathang();
        getDateTimenow();
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
                    finish();
//                Intent i = new Intent(DonHangActivity.this, MainActivity.class);
//                DonHangActivity.this.finish(); //Use your current activity
//                startActivity(i);
            }
        });

    }

    private void getDateTimenow(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        dateTime = currentDateandTime;
    }

    private  void chosseAdress(){
        chosseAdress = findViewById(R.id.tv_chosseAdress);
        chosseAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(DonHangActivity.this, AddressActivity.class);
//                intent.putExtra("USER_ID",USER_ID);
//                startActivity(intent);
                Intent i = new Intent(DonHangActivity.this, EditProfildeActivity.class);
                i.putExtra("USER_ID",USER_ID);
                startActivity(i);
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
        tvAddress=findViewById(R.id.tv_noneAdress);
        refProfile.child(USER_ID).child("address").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               if( snapshot.getValue()!=null){
                   tvAddress.setVisibility(View.GONE);
                   String address =  snapshot.getValue().toString();
                   listAdress.add(address);
                   adapterAddress.notifyDataSetChanged();
               }else {

                   tvAddress.setVisibility(View.VISIBLE);
               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
        return listAdress;
    }
    private void onclickDathang(){
        tvDatHang = findViewById(R.id.tv_dathang);
        tvDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getName() ==true & getSdt()==true &&getAddress()==true &&  getthanhtoan()==true){
                   Dathang();
                }else {
                    return;
                }



            }
        });
    }



    private boolean getthanhtoan(){
        radioGroup = findViewById(R.id.gr_radiobutton);
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        if(radioButton==null){
            Toast.makeText(DonHangActivity.this,"Hãy chọn hình thức thanh toán",Toast.LENGTH_LONG).show();
            return false;
        }
        else{
            hinhthucthanhtoan = radioButton.getText().toString().trim();
            return true;
        }
    }
    private boolean getAddress(){
        if(adapterAddress.getStrAddress()==null){
            Toast.makeText(DonHangActivity.this,"Hãy chọn địa chỉ",Toast.LENGTH_LONG).show();
            return false;
        }else {
            address = adapterAddress.getStrAddress().trim();
            return true;
        }

    }
    private boolean getName(){

        if(tvFullName.getText().toString().trim() =="(Hãy cập nhật tên)"){
            Toast.makeText(DonHangActivity.this,"Hãy cập nhật người nhận hàng",Toast.LENGTH_LONG).show();
            return false;
        }else {
            fullName = tvFullName.getText().toString().trim();
            return true;
        }

    }
    private boolean getSdt(){
        if(tvSdt.getText().toString().trim()=="(Hãy cập nhật sđt)"){
            Toast.makeText(DonHangActivity.this,"Hãy cập nhật số điện thoại nhận hàng",Toast.LENGTH_LONG).show();
            return false;
        }else {
            Sdt = tvSdt.getText().toString().trim();
            return true;
        }
    }
    private void getDataViewthongtin(){
        tvFullName = findViewById(R.id.tv_Nameuser);
        tvSdt = findViewById(R.id.tv_SĐT);
        refProfile.child(USER_ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userObj = snapshot.getValue(User.class);
                if(userObj!=null){
                    if (userObj.getFullName()!=null){
                        tvFullName.setText(userObj.getFullName());
                    }else {
                        tvFullName.setText("(Hãy cập nhật tên)");
                        tvFullName.setTextSize(13);
                        tvFullName.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                    }
                    if (userObj.getPhoneNumber()!=null){
                        tvSdt.setText(userObj.getPhoneNumber());
                    }else {
                        tvSdt.setText("(Hãy cập nhật sđt)");
                        tvSdt.setTextSize(13);
                        tvSdt.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                    }
                }else {
                    tvFullName.setText("(Hãy cập nhật tên)");
                    tvFullName.setTextSize(13);
                    tvFullName.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                    tvSdt.setText("(Hãy cập nhật sđt)");
                    tvSdt.setTextSize(13);
                    tvSdt.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                    return;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void Dathang() {

        DonHang donHang = new DonHang("none",USER_ID,fullName,Sdt,address,dateTime,list,false,Tongtien);
        refDonhang.push().setValue(donHang).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(DonHangActivity.this,"Đặt thành công",Toast.LENGTH_LONG).show();
                refcart.child(USER_ID).removeValue();
                finish();
            }
        });
    }
}